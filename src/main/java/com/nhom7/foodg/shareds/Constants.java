package com.nhom7.foodg.shareds;

import com.nhom7.foodg.exceptions.DataIntegrityViolationException;
import com.nhom7.foodg.exceptions.InvalidDataException;
import com.nhom7.foodg.exceptions.MissingFieldException;
import org.apache.commons.validator.routines.EmailValidator;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.MessageFormat;

public class Constants {
    // error message
    public static final String GET_DATA_SUCCESS = "Ohhh, lấy dữ liệu của `{0}` thành công rồi nè, chúc mừng nhé ^^";
    public static final String GET_DATA_EMPTY = "`{0}` rỗng này, kiểm tra xem trong db có dữ liệu chưa! ";
    public static final String SEARCH_SUCCESS = "Tìm kiếm `{1}` của `{0}` thành công rồi nhé. ";
    public static final String SEARCH_FAIL_CATCH = "Không tìm thấy `{1}` nào của `{0}` nào đâu cu. Check lại trính tả, ID xem :v! ";
    public static final String RESTORE_SUCCESS = "Khôi phục `{0}` thành công rồi nhé. ";
    public static final String RESTORE_FAIL_CATCH = "Khôi phục `{0}` thất bại. ";
    public static final String MODIFY_DATA_SUCCESS = "Thay đổi `{0}` thành công rồi nhé. Mở db check lại xem đúng chưa. ";
    public static final String MODIFY_DATA_FAIL_CATCH = "`{0}` không có thay đổi. Kiểm tra xem đúng type đầu vào chưa. ";
    public static final String DELETE_SUCCESS = "Xoá `{1}` thành công của bảng `{0}` rồi nhé! ";
    public static final String DELETE_FAIL_CATCH = "Thất bại khi xoá `{1}` của bảng `{0}`. ";
    public static final String DUPLICATE_ERROR = "Giá trị `{1}` đã tồn tại trong bảng `{0}` rồi. Nếu `{1}` xuất hiện trong thùng rác, hãy xoá hoặc khôi phục nó! ";
    public static final String  MISSING_FIELD_EXCEPTION = "Bạn nhập dữ liệu trường `{0}` còn thiếu, bạn hãy điền vào nhé! ";
    public static final String OUT_OF_RANGE_EXCEPTION = "Dữ liệu của trường `{0}` nằm ngoài phạm vi cho phép, bạn hãy nhập lại nhé!";
    public static final String INVALID_DATA_EXCEPTION = "Dữ liệu của trường `{0}` không phải kiểu `{1}`, bạn hãy nhập lại nhé!";
    public static final String  DATA_INTEGRITY_VIOLATION_EXCEPTION = "Dữ liệu của trường `{0}` không đúng với kiểu dữ liệu `{1}` trong DATABASE cho phép, bạn hãy kiểm tra lại nhé!";
    // actions
    public static final String ACTION_CREATE = "CREATE";
    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";
    public static final String ACTION_RESTORE = "RESTORE";

    // regex
    public static final String REGEX_URL_IMAGE = "(https?:\\/\\/.*\\.(?:png|jpg))";

    // method
    public static Date getCurrentDay() {
        java.util.Date currentDate = new java.util.Date();
        return new Date(currentDate.getTime());
    }


    public static void validateRequiredFields(Object obj, String... fields) throws MissingFieldException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                Object value = f.get(obj);
                if (value == null || value.toString().trim().isEmpty()) {
                    throw new MissingFieldException(MessageFormat.format(Constants.MISSING_FIELD_EXCEPTION, field));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateIntegerFields(Object obj, String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                Object value = f.get(obj);
                if (!(value instanceof Integer)) {
                    throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "int"));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateEmailFields(Object obj, String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                String value = (String) f.get(obj);
                if (!EmailValidator.getInstance().isValid(value)) {
                    throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "email"));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateDateFields(Object obj, String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                String value = (String) f.get(obj);
                try {
                    DateTime dt = new DateTime(value);
                } catch (Exception e) {
                    throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "DATE"));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateStringFields(Object obj, String dataType,  int limit, String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                String value = (String) f.get(obj);
                if (value != null) {
                    if (value.length() > limit)
                        throw new DataIntegrityViolationException(MessageFormat.format(Constants.DATA_INTEGRITY_VIOLATION_EXCEPTION, field, dataType));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateDecimalFields(Object obj, int integer, int fractional , String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                String dataType = "Decimal(" + integer + ", " +fractional+ ")";

                String input = (String) f.get(obj);
                if (input != null && input.trim().length() > 0) {
                    BigDecimal value;
                    try {
                        value = new BigDecimal(input);
                        int scale = value.scale();
                        int precision = value.precision();
                        if ( scale > fractional || precision > (integer + scale)) {
                            throw new DataIntegrityViolationException(MessageFormat.format(Constants.DATA_INTEGRITY_VIOLATION_EXCEPTION, field, dataType));
                        }
                    } catch (Exception ex){
                        throw new DataIntegrityViolationException(MessageFormat.format(Constants.DATA_INTEGRITY_VIOLATION_EXCEPTION, field, input));
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }
}
