import java.util.List;

public interface TimKiem<T> {
    List<T> timKiem(String keyword);

    default boolean coKetQua(String keyword) {
        return !timKiem(keyword).isEmpty();
    }
}
