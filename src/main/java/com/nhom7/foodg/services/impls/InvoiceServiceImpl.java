
package com.nhom7.foodg.services.impls;

        import com.nhom7.foodg.exceptions.DuplicateRecordException;
        import com.nhom7.foodg.exceptions.ModifyException;
        import com.nhom7.foodg.exceptions.NotFoundException;
        import com.nhom7.foodg.models.dto.TblInvoiceDto;
        import com.nhom7.foodg.models.entities.TblInvoiceEntity;
        import com.nhom7.foodg.repositories.InvoiceRepository;
        import com.nhom7.foodg.repositories.LineRepository;
        import com.nhom7.foodg.services.InvoiceService;
        import com.nhom7.foodg.shareds.Constants;
        import jakarta.transaction.Transactional;
        import org.springframework.dao.DataAccessException;
        import org.springframework.dao.DataIntegrityViolationException;
        import org.springframework.stereotype.Component;

        import java.util.Date;
        import java.text.MessageFormat;
        import java.util.List;

@Component
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final LineRepository lineRepository;
    private final String TABLE_NAME = "tbl_Invoice";

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, LineRepository lineRepository) {
        this.invoiceRepository = invoiceRepository;
        this.lineRepository = lineRepository;
    }

    // Get all invoices
    @Override
    public List<TblInvoiceEntity> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public void insert(TblInvoiceDto newInvoice) {
        //Validate input
        Constants.validateRequiredFields(newInvoice, "customerId", "invoiceNumber", "invoiceDate", "totalAmount", "tax", "idDiscount", "grandTotal", "status", "paid" );
        Constants.validateIntegerFields(newInvoice, "customerId", "invoiceNumber", "idDiscount","status");
        Constants.validateDecimalFields(newInvoice, 5,2, "totalAmount", "grandTotal");
        Constants.validateDecimalFields(newInvoice, 2, 1, "tax");
//        Constants.validateDateFields(newInvoice, "dueDate", "paidDate", "invoiceDate");
        Constants.validateBooleanFields(newInvoice, "paid");


        int id = newInvoice.getId();
        try {
            if (invoiceRepository.existsById(id)) {
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, id));
            }

            Date currentDate = Constants.getCurrentDay();
            TblInvoiceEntity tblInvoiceEntity = TblInvoiceEntity.
                    create(0,
                            newInvoice.getCustomerId(),
                            newInvoice.getInvoiceNumber(),
                            Constants.getCurrentDay(),
                            newInvoice.getTotalAmount(),
                            newInvoice.getTax(),
                            newInvoice.getIdDiscount(),
                            newInvoice.getGrandTotal(),
                            newInvoice.getStatus(),
                            newInvoice.getIdOnePayResponse(),
                            currentDate,
                            currentDate,
                            newInvoice.getDueDate(),
                            newInvoice.getPaid(),
                            currentDate
                    );
            invoiceRepository.save(tblInvoiceEntity);

        } catch (DataIntegrityViolationException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }
    @Transactional
    @Override
    public void update(TblInvoiceEntity tblInvoiceEntity) {
        if (!invoiceRepository.existsById(tblInvoiceEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblInvoiceEntity.getId()));
        }



        try {
            TblInvoiceEntity invoice = invoiceRepository.findById(tblInvoiceEntity.getId()).orElse(null);

            if (invoice != null) {

                invoice.setInvoiceNumber(tblInvoiceEntity.getInvoiceNumber());

                invoice.setUpdatedAt(tblInvoiceEntity.getUpdatedAt());

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
