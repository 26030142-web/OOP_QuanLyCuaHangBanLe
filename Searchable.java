import java.util.List;

// Giao dien chuan hoa chuc nang tim kiem cho danh sach du lieu.
public interface Searchable<T> {
    // Lop trien khai tu dinh nghia cach tim theo tu khoa.
    List<T> timKiem(String tuKhoa);

    // Ham mac dinh cho biet tu khoa co tao ra ket qua hay khong.
    default boolean coKetQua(String tuKhoa) {
        return !timKiem(tuKhoa).isEmpty();
    }
}
