package com.nhom7.foodg.services.impls;

import com.nhom7.foodg.exceptions.DuplicateRecordException;
import com.nhom7.foodg.exceptions.ModifyException;
import com.nhom7.foodg.exceptions.NotFoundException;
import com.nhom7.foodg.models.dto.TblLineDto;
import com.nhom7.foodg.models.entities.TblLineEntity;
import com.nhom7.foodg.repositories.LineRepository;
import com.nhom7.foodg.services.LineService;
import com.nhom7.foodg.shareds.Constants;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;

@Component
public  class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final String TABLE_NAME = "tbl_line";

    public LineServiceImpl(LineRepository lineRepository) {
        this.lineRepository = lineRepository;

    }

    // Get all category
    @Override
    public List<TblLineEntity> getAll() { return lineRepository.findAll();}

    @Override
    public void insert(TblLineDto newLine) {
                int lineId = newLine.getId();
        try {
            if (lineRepository.existsById(lineId)) {
                throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR, TABLE_NAME, lineId));
            }

            Date currentDate = Constants.getCurrentDay();
            TblLineEntity tblLineEntity = TblLineEntity.create(
                    0,
                    newLine.getIdInvoice(),
                    newLine.getIdProduct(),
                    newLine.getDescription(),
                    newLine.getQuantity(),
                    newLine.getPrice(),
                    newLine.getUnitPrice(),

                    newLine.getTotal()

            );

            lineRepository.save(tblLineEntity);

        } catch (DataIntegrityViolationException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }


    @Transactional
    @Override
    public void update(TblLineEntity tblLineEntity) {
        if (!lineRepository.existsById(tblLineEntity.getId())) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, tblLineEntity.getId()));
        }



        try {
            TblLineEntity line = lineRepository.findById(tblLineEntity.getId()).orElse(null);

            if (line != null) {
                line.setIdInvoice(tblLineEntity.getIdInvoice());
                line.setIdProduct(tblLineEntity.getIdProduct());
                line.setDescription(tblLineEntity.getDescription());
                line.setQuantity(tblLineEntity.getQuantity());
                line.setPrice(tblLineEntity.getPrice());
                line.setUnitPrice(tblLineEntity.getUnitPrice());

                line.setTotal(tblLineEntity.getTotal());
                 lineRepository.save(line);
            }
        } catch (NullPointerException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME) + ex.getMessage());
        }
    }



    @Transactional
    @Override
    public void softDelete(int id) {
        if (!lineRepository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_FAIL_CATCH, TABLE_NAME, id));
        }

        try {
            lineRepository.deleteById(id);
            TblLineEntity line = lineRepository.findById(id).orElse(null);
            if (line != null) {

                lineRepository.save(line);
            }
        } catch (DataAccessException ex) {
            throw new ModifyException(MessageFormat.format(Constants.MODIFY_DATA_FAIL_CATCH, TABLE_NAME, id) + ex.getMessage());
        }
    }


}
