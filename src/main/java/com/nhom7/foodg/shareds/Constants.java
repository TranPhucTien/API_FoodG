package com.nhom7.foodg.shareds;

import com.nhom7.foodg.exceptions.DataIntegrityViolationException;
import com.nhom7.foodg.exceptions.InvalidDataException;
import com.nhom7.foodg.exceptions.MissingFieldException;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.joda.time.DateTime;
import java.security.MessageDigest;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public static final String OUT_OF_RANGE_EXCEPTION = "Dữ liệu của trường `{0}` nằm ngoài phạm vi cho phép của `{1}`, bạn hãy nhập lại nhé!";
    public static final String INVALID_DATA_EXCEPTION = "Dữ liệu của trường `{0}` không phải kiểu `{1}`, bạn hãy nhập lại nhé!";

    public static final String  DATA_INTEGRITY_VIOLATION_EXCEPTION = "Dữ liệu của trường `{0}` không đúng với kiểu dữ liệu `{1}` trong DATABASE cho phép, bạn hãy kiểm tra lại nhé!";
    public static final String REQUIRE_TYPE = "`{1}` phải là dạng `{0}`";
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


    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void validateRequiredFields(Object obj, String... fields) throws MissingFieldException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                Object value = f.get(obj);
                if (value == null ||
                        value.toString().trim().isEmpty()
                        /*|| (value instanceof Number && ((Number) value).doubleValue() == 0)*/) {
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
                if (!(value instanceof Integer ) && value != null) {
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
                Object input = f.get(obj);
                if (input != null) {
                    String value = (String) f.get(obj);
                    if (!GenericValidator.isEmail(value)) {
                        throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "email"));
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

//    public static void validateDateFields(Object obj, String... fields) throws InvalidDataException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (String field : fields) {
//            try {
//                Field f = obj.getClass().getDeclaredField(field);
//                f.setAccessible(true);
//                String value = f.get(obj).toString();
//                if(value != null && !value.isEmpty()) {
//                    try {
//                        if(value.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                            Date date = new Date(sdf.parse(value).getTime());
//                        } else {
//                            throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "DATE"));
//                        }
//                    } catch (ParseException e) {
//                        throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "DATE"));
//                    }
//                }
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                throw new RuntimeException("Invalid field: " + field, e);
//            }
//        }
//    }

    public static void validateDateFields(Object obj, String... fields) throws InvalidDataException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                Object input = f.get(obj);
                if (input != null) {
                    String value = f.get(obj).toString();
                    if (value != null && !value.isEmpty()) {
                        if (!GenericValidator.isDate(value, "yyyy-MM-dd", true)) {
                            throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "DATE"));
                        }
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateStringFields(Object obj, String dataType,  int min, int max, String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                String value = (String) f.get(obj);
                if (value != null) {
                    if (value.length() < min || value.length() > max)
                        throw new DataIntegrityViolationException(MessageFormat.format(Constants.OUT_OF_RANGE_EXCEPTION, field, dataType));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }

    public static void validateBooleanFields(Object obj, String... fields) throws InvalidDataException {
        for (String field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field);
                f.setAccessible(true);
                Object input = f.get(obj);
                if (input != null) {
                    Boolean value = (Boolean) f.get(obj);
                    if (value != false && value != true) {
                        throw new InvalidDataException(MessageFormat.format(Constants.INVALID_DATA_EXCEPTION, field, "Boolean"));
                    }
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

                Object input =  f.get(obj);
                String stringValue = "";
                if(input instanceof Float){
                    float floatValue = (Float) input;
                    stringValue = Float.toString(floatValue);
                } else if(input instanceof Double) {
                    double doubleValue = (Double) input;
                    stringValue = Double.toString(doubleValue);
                }

                if (stringValue != null  && stringValue.trim().length() > 0) {
                    BigDecimal value = new BigDecimal(stringValue);
                    int scale = 0;
                    int precision = 0;
                    try {
                        scale = value.scale();
                        precision = value.precision();
                        if (scale > fractional || precision > (integer - fractional + scale)) {
                            throw new DataIntegrityViolationException(MessageFormat.format(Constants.DATA_INTEGRITY_VIOLATION_EXCEPTION, field, dataType));
                        }
                    } catch (Exception ex) {
                        throw new DataIntegrityViolationException(MessageFormat.format(Constants.DATA_INTEGRITY_VIOLATION_EXCEPTION, field, dataType));
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + field, e);
            }
        }
    }
}