# Quản lý cửa hàng bán lẻ

Đồ án Java hướng đối tượng chạy trên giao diện Console. Chương trình quản lý sản phẩm, khách hàng, đơn hàng và các khoản thu chi. Dữ liệu được lưu thành các file CSV để mở chương trình lại vẫn còn dữ liệu.

## Yêu cầu

- JDK 8 trở lên.
- VS Code hoặc một IDE Java khác.
- Các file `.java` nằm trong thư mục `src` và cùng package mặc định.

## Chạy chương trình

Mở Terminal tại thư mục dự án, là thư mục đang chứa `src`.


Chọn `0` trong menu để lưu dữ liệu và thoát. Khi nhập giá tiền, dùng số bình thường, ví dụ `25000`.

## Chức năng

### 1. Sản phẩm

Thêm, sửa thông tin và tồn kho, xóa, tìm theo mã/tên, xem danh sách, thống kê số lượng từng loại và tổng tồn. Ba loại sản phẩm là `Food`, `Electronics`, `Clothing`; cả ba dùng chung chức năng quản lý.

### 2. Khách hàng

Thêm, sửa tên/số điện thoại, xóa, tìm theo mã/tên, xem danh sách và thống kê số khách. Không xóa khách đã có đơn để giữ liên kết với lịch sử đơn hàng.

### 3. Đơn hàng

Tạo đơn cho khách, thêm nhiều dòng sản phẩm, sửa số lượng, xóa đơn, tìm theo mã đơn/tên khách, xem danh sách và thống kê tổng tiền bán. Giá trong từng dòng đơn được giữ tại lúc thêm sản phẩm vào đơn.

### 4. Thu chi

Ghi khoản thu hoặc chi, sửa, xóa, tìm theo mã/nội dung, xem danh sách và thống kê tổng thu, tổng chi, phần còn lại. Doanh thu đơn hàng được thống kê riêng, không tự cộng vào sổ thu chi để tránh đếm hai lần.

## Lưu dữ liệu

Chương trình tự tạo thư mục `data` tại thư mục đang chạy và lưu:

- `products.csv`: sản phẩm.
- `customers.csv`: khách hàng.
- `orders.csv`: mã đơn và mã khách.
- `order_items.csv`: các dòng hàng trong đơn.
- `thuchi.csv`: các khoản thu chi.

Không xóa thư mục `data` nếu muốn giữ dữ liệu. File CSV dùng dấu chấm phẩy để tách cột; tránh nhập ký tự `;` trong mã, tên hoặc nội dung.

## Các lớp chính

| Lớp | Vai trò |
|---|---|
| `Product` | Lớp trừu tượng chứa thông tin chung của sản phẩm |
| `Food`, `Electronics`, `Clothing` | Ba lớp con của `Product`, mỗi lớp có thông tin riêng |
| `Customer` | Lưu thông tin khách hàng |
| `Order` | Lưu khách hàng và danh sách dòng hàng của đơn |
| `OrderItem` | Lưu sản phẩm, đơn giá, số lượng và thành tiền |
| `ThuChi` | Lưu một khoản thu hoặc chi |
| `QuanLyCuaHang` | Quản lý danh sách, chức năng nghiệp vụ và lưu/đọc dữ liệu |
| `ProductFactory` | Tạo sản phẩm theo loại được chọn |
| `TimKiem`, `LuuDuLieu` | Hai interface tìm kiếm và đọc/ghi dữ liệu |
| `Main` | Menu Console và nhập dữ liệu từ bàn phím |

## Nội dung OOP được dùng

- Đóng gói: các thuộc tính dữ liệu khai báo `private`, có getter/setter và kiểm tra dữ liệu.
- Kế thừa, trừu tượng, ghi đè: `Product` là lớp cha trừu tượng; ba loại sản phẩm ghi đè phương thức riêng.
- Đa hình: danh sách `ArrayList<Product>` có thể chứa cả ba loại sản phẩm.
- Quan hệ đối tượng: `Order` có `Customer` và chứa các `OrderItem`; mỗi dòng hàng tham chiếu tới `Product`.
- Nạp chồng: `Order.addItem(...)` và `QuanLyCuaHang.timKiem(...)`.
- Interface: `TimKiem` có `default method`; `LuuDuLieu` khai báo thao tác file.
- Design pattern: Singleton dùng cho `QuanLyCuaHang`; Factory dùng cho `ProductFactory`.
- Exception riêng: `ProductNotFoundException` và `DataException` được khai báo trong `QuanLyCuaHang`.

