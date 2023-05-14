package com.nhom7.foodg.shareds;

import java.sql.Date;

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
}
