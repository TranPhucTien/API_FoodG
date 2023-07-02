
package com.nhom7.foodg.services.impls;
import java.util.Objects;
import com.nhom7.foodg.exceptions.InactiveDiscountException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotApplyDiscountExeption;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.*;
import com.nhom7.foodg.models.entities.*;
import com.nhom7.foodg.repositories.*;
import com.nhom7.foodg.services.InvoiceService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

@Component
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    private final LineRepository lineRepository;
    private final String TABLE_NAME = "tbl_Invoice";

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, LineRepository lineRepository,CustomerRepository customerRepository
            ,ProductRepository productRepository,DiscountRepository discountRepository) {
        this.invoiceRepository = invoiceRepository;
        this.lineRepository = lineRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

//    public InvoiceServiceImpl() {
//
//    }

    // Get all invoices
    @Override
    public List<TblInvoiceEntity> getAll() {
        return invoiceRepository.findAll();
    }



    @Override
    public void insert(TblInvoiceLineDto tblInvoiceLineDto,String codeDiscount)  {
        try {

//            Constants.validateRequiredFields(tblInvoiceLineDto, "customerId", "invoiceNumber", "invoiceDate", "totalAmount", "tax", "idDiscount", "grandTotal", "status", "paid" );
//            Constants.validateIntegerFields(tblInvoiceLineDto, "customerId", "invoiceNumber", "idDiscount","status");
//            Constants.validateDecimalFields(tblInvoiceLineDto, 5,2, "totalAmount", "grandTotal");
//            Constants.validateDecimalFields(tblInvoiceLineDto, 2, 1, "tax");
//            Constants.validateDateFields(tblInvoiceLineDto, "dueDate", "paidDate", "invoiceDate");
            TblDiscountEntity tblDiscountEntity  = discountRepository.getByCode(codeDiscount); // code = null => tblDiscountEntity = null chỗ này nó trả veeg null r
            Random rand = new Random();
            int upperbound = 123456789;
            int id_random = rand.nextInt(upperbound);
            Date currentDate = Constants.getCurrentDay();
            Integer discountId = null;

            if(tblDiscountEntity != null){
                discountId = tblDiscountEntity.getId();
            }

                TblInvoiceEntity tblInvoiceEntity = TblInvoiceEntity.create(
                        id_random,
                        tblInvoiceLineDto.getNewInvoice().getCustomerId(),
                        tblInvoiceLineDto.getNewInvoice().getInvoiceNumber(),
                        currentDate,
                        null,
                        BigDecimal.valueOf(0),
                        discountId,

                        //chỗ này t cho phép null trong database r mà no cứ báo k đc để null: cái idDsicountấy
                        null,
                        Status.ACTIVE.getValue(),
                        tblInvoiceLineDto.getNewInvoice().getIdOnePayResponse(),
                        currentDate,
                        currentDate,
                        tblInvoiceLineDto.getNewInvoice().getDueDate(),
                        tblInvoiceLineDto.getNewInvoice().getPaid(),
                        currentDate

                );

                invoiceRepository.save(tblInvoiceEntity);

            int a =id_random;
            BigDecimal totalAmount = new BigDecimal("0");
            for (TblLineOutDto line : tblInvoiceLineDto.getTblLineOutDtos()) {
                TblProductEntity tblProductEntity = productRepository.findById(line.getIdProduct()).orElse(null);

                BigDecimal price = BigDecimal.valueOf(tblProductEntity.getPrice());//giá trị từng sp
                BigDecimal unitPrice = BigDecimal.valueOf(line.getQuantity()*tblProductEntity.getPrice());// gtri sp * sluong

                totalAmount = totalAmount.add(unitPrice);//tổng tiền các dòng hóa đơn
                BigDecimal total = new BigDecimal("0");


                TblLineEntity tblLineEntity = TblLineEntity.create(
                        0,
                        tblInvoiceEntity.getId(),
                        line.getIdProduct(),
                        line.getDescription(),
                        line.getQuantity(),
                        price,
                        unitPrice ,//giá* so lượng sản phẩm
                        unitPrice// tổng tiềng dòng
                );

                lineRepository.save(tblLineEntity);
            }

            if(tblDiscountEntity != null) {

                BigDecimal totalAmountMulTax = totalAmount.multiply(tblInvoiceEntity.getTax());
                BigDecimal grandtotal = tblInvoiceLineDto.getNewInvoice().caculatorGrandTotal(totalAmount,tblDiscountEntity).add(totalAmountMulTax);

                tblInvoiceEntity.setTotalAmount(totalAmount);
                tblInvoiceEntity.setGrandTotal(grandtotal);
            }

            else if(tblDiscountEntity == null){
                BigDecimal totalAmountMulTax = totalAmount.multiply(tblInvoiceEntity.getTax());
                BigDecimal grandtotal = totalAmount.add(totalAmountMulTax);
                tblInvoiceEntity.setTotalAmount(totalAmount);
                tblInvoiceEntity.setGrandTotal(grandtotal);
                invoiceRepository.save(tblInvoiceEntity);
            }


        } catch (DataIntegrityViolationException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());

        }
//        catch (NotApplyDiscountExeption e) {
//            throw new RuntimeException(e);
//        }
    }

    @Transactional
    @Override
    public void update(TblInvoiceEntity tblInvoiceEntity) {
        if (!(invoiceRepository.existsById(tblInvoiceEntity.getId()) || invoiceRepository.existsByInvoiceNumber(tblInvoiceEntity.getInvoiceNumber()))) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblInvoiceEntity.getId()));
        }


        try {
            TblInvoiceEntity invoice = invoiceRepository.findFirstByInvoiceNumber(tblInvoiceEntity.getInvoiceNumber());

            if (invoice != null) {
                invoice.setInvoiceDate(tblInvoiceEntity.getInvoiceDate());
                invoice.setTotalAmount(tblInvoiceEntity.getTotalAmount());
                invoice.setTax(tblInvoiceEntity.getTax());
                invoice.setIdDiscount(tblInvoiceEntity.getIdDiscount());
                invoice.setGrandTotal(tblInvoiceEntity.getGrandTotal());
                invoice.setStatus(tblInvoiceEntity.getStatus());
                invoice.setIdOnePayResponse(tblInvoiceEntity.getIdOnePayResponse());

                invoice.setUpdatedAt(tblInvoiceEntity.getUpdatedAt());
                invoice.setDueDate(tblInvoiceEntity.getDueDate());
                invoice.setPaid(tblInvoiceEntity.getPaid());
                invoice.setPaidDate(tblInvoiceEntity.getPaidDate());

                invoiceRepository.save(invoice);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    @Transactional
    @Override
    public void softDelete(int id) {
        if (!invoiceRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            invoiceRepository.deleteById(id);
            TblInvoiceEntity invoice = invoiceRepository.findById(id).orElse(null);
            if (invoice != null) {

                invoiceRepository.save(invoice);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }

    //     lay thong tin hoa don bang invoice_id
    @Override
    public TblInvoiceOutDto getById(int invoiceId) {

        try {

            if (!invoiceRepository.existsById(invoiceId)) {
                throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, invoiceId));
            }

            TblInvoiceEntity tblInvoiceEntity = invoiceRepository.findFirstById(invoiceId);
            TblCustomerEntity customer = customerRepository.findById(tblInvoiceEntity.getCustomerId()).orElse(null);
            TblCustomerOutDto tblCustomerOutDto = TblCustomerOutDto.create(
                    customer.getUsername(),
                    customer.getFullName(),
                    customer.getEmail()

            );
            TblInvoiceOutDto tblInvoiceOutDto = TblInvoiceOutDto.create(
                    invoiceId,
                    tblCustomerOutDto,
                    lineRepository.findByIdInvoice(invoiceId),
                    tblInvoiceEntity.getInvoiceNumber(),
                    tblInvoiceEntity.getInvoiceDate(),
                    tblInvoiceEntity.getTotalAmount(),
                    tblInvoiceEntity.getTax(),
                    tblInvoiceEntity.getIdDiscount(),
                    tblInvoiceEntity.getGrandTotal(),
                    tblInvoiceEntity.getStatus(),
                    tblInvoiceEntity.getIdOnePayResponse(),
                    tblInvoiceEntity.getCreatedAt(),
                    tblInvoiceEntity.getUpdatedAt(),
                    tblInvoiceEntity.getDueDate(),
                    tblInvoiceEntity.getPaid(),
                    tblInvoiceEntity.getPaidDate()
            );
            return tblInvoiceOutDto;
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    // Get all lines of invoice by invoice id
//    @Override
//    public List<TblLineEntity> getLinesByInvoice(int invNum) {
//        if (!invoiceRepository.existsByInvoiceNumber(invNum)) {
//            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, invNum));
//        }
//
//        TblInvoiceEntity inv = invoiceRepository.findFirstByInvoiceNumber(invNum);
//        return lineRepository.findByIdInvoice(inv.getId());
//    }
//
//    // Get invoice by invoice id
//    @Override
//    public TblInvoiceEntity getByID(int id) {
//        if (!invoiceRepository.existsById(id)) {
//            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
//        }
//
//        return invoiceRepository.findById(id).orElse(null);
//    }
//
//    // Search invoice by invoice num
//    @Override
//    public List<TblInvoiceEntity> search(int invNum) {
//        List<TblInvoiceEntity> rs = new ArrayList<>();
//        List<TblInvoiceEntity> invoices = invoiceRepository.findAll();
//
//        for (TblInvoiceEntity invoice : invoices) {
//            if (invoice.getInvoiceNumber() == invNum) {
//                rs.add(invoice);
//            }
//        }
//
//        return rs;
//    }


}