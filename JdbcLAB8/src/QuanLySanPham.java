import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuanLySanPham {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Chuẩn bị môi trường, nạp driver và thiết lập kết nối
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:sanpham.db";
            conn = DriverManager.getConnection(url);

            // --- BẬT GIAO DỊCH (TRANSACTION) ---
            // Tắt chế độ auto-commit để quản lý thủ công (commit/rollback)
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            // Xóa bảng cũ nếu có để làm sạch dữ liệu khi chạy lại
            stmt.executeUpdate("DROP TABLE IF EXISTS SanPham;");
            stmt.executeUpdate("DROP TABLE IF EXISTS NhanVien;");

            // Tạo bảng SanPham (maSP, tenSP, gia, soluong)
            String sql_create_sp = "CREATE TABLE SanPham ("
                    + "maSP INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "tenSP TEXT NOT NULL, "
                    + "gia REAL NOT NULL, "
                    + "soluong INTEGER NOT NULL"
                    + ");";
            stmt.executeUpdate(sql_create_sp);
            stmt.close();

            // CREATE (Thêm mới 3 sản phẩm dùng PreparedStatement)
            String sql_insert = "INSERT INTO SanPham (tenSP, gia, soluong) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql_insert);

            // Sản phẩm 1
            pstmt.setString(1, "Laptop Dell Inspiron");
            pstmt.setDouble(2, 16500000);
            pstmt.setInt(3, 10);
            pstmt.executeUpdate();

            // Sản phẩm 2
            pstmt.setString(1, "Chuột không dây Logitech");
            pstmt.setDouble(2, 450000);
            pstmt.setInt(3, 50);
            pstmt.executeUpdate();

            // Sản phẩm 3
            pstmt.setString(1, "Bàn phím cơ Gaming");
            pstmt.setDouble(2, 1200000);
            pstmt.setInt(3, 25);
            pstmt.executeUpdate();
            pstmt.close();

            // UPDATE (Cập nhật giá hoặc số lượng theo maSP)
            String sql_update = "UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?";
            pstmt = conn.prepareStatement(sql_update);
            pstmt.setDouble(1, 15900000); // Giảm giá sản phẩm maSP = 1
            pstmt.setInt(2, 8); // Cập nhật số lượng còn lại
            pstmt.setInt(3, 1); // Điều kiện maSP = 1
            pstmt.executeUpdate();
            pstmt.close();

            // DELETE (Xóa sản phẩm theo maSP)
            String sql_delete = "DELETE FROM SanPham WHERE maSP = ?";
            pstmt = conn.prepareStatement(sql_delete);
            pstmt.setInt(1, 2); // Xóa sản phẩm có maSP = 2 (Chuột Logitech)
            pstmt.executeUpdate();
            pstmt.close();

            // Xác nhận hoàn tất chuỗi thao tác C-U-D (Commit Transaction)
            conn.commit();
            System.out.println("--> Thao tác CSDL sản phẩm thành công (Create, Update, Delete)!");

            // READ (Truy vấn & Tìm kiếm sản phẩm)
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM SanPham");

            System.out.println("\n=== DANH SÁCH TẤT CẢ SẢN PHẨM ===");
            while (rs.next()) {
                int maSP = rs.getInt("maSP");
                String tenSP = rs.getString("tenSP");
                double gia = rs.getDouble("gia");
                int soluong = rs.getInt("soluong");
                System.out
                        .println("maSP: " + maSP + " | tenSP: " + tenSP + " | gia: " + gia + " | soluong: " + soluong);
            }
            rs.close();
            stmt.close();

            // Tìm kiếm sản phẩm theo tên sử dụng PreparedStatement
            String sql_search = "SELECT * FROM SanPham WHERE tenSP LIKE ?";
            pstmt = conn.prepareStatement(sql_search);
            pstmt.setString(1, "%Laptop%"); // Tìm kiếm từ khóa
            rs = pstmt.executeQuery();

            System.out.println("\n=== KẾT QUẢ TÌM KIẾM (Từ khóa: 'Laptop') ===");
            while (rs.next()) {
                System.out.println("Tìm thấy -> Tên: " + rs.getString("tenSP") + " - Giá: " + rs.getDouble("gia"));
            }
            rs.close();
            pstmt.close();

            // TRUY VẤN NÂNG CAO & HÀM TỔNG HỢP (Aggregation Functions)
            stmt = conn.createStatement();
            // Tạo bảng NhanVien có cột luong để thực hành hàm tổng hợp
            stmt.executeUpdate(
                    "CREATE TABLE NhanVien (id INTEGER PRIMARY KEY AUTOINCREMENT, ten TEXT, chuc_vu TEXT, luong REAL);");
            stmt.executeUpdate(
                    "INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES ('Nguyen Van An', 'Developer', 15000000);");
            stmt.executeUpdate(
                    "INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES ('Tran Thi Bich', 'Tester', 12000000);");
            stmt.executeUpdate(
                    "INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES ('Le Van Cuong', 'Developer', 22000000);");

            // Tính tổng lương, lương trung bình, max, min
            String sql_agg = "SELECT SUM(luong) AS TongLuong, AVG(luong) AS LuongTB, MAX(luong) AS MaxLuong, MIN(luong) AS MinLuong FROM NhanVien";
            ResultSet rsAgg = stmt.executeQuery(sql_agg);
            if (rsAgg.next()) {
                System.out.println("\n--- THỐNG KÊ LƯƠNG NHÂN VIÊN ---");
                System.out.println("Tổng quỹ lương: " + rsAgg.getDouble("TongLuong"));
                System.out.println("Lương trung bình: " + rsAgg.getDouble("LuongTB"));
                System.out.println("Lương cao nhất: " + rsAgg.getDouble("MaxLuong"));
                System.out.println("Lương thấp nhất: " + rsAgg.getDouble("MinLuong"));
            }
            rsAgg.close();

            // Đếm số lượng nhân viên theo chức vụ (GROUP BY)
            String sql_group = "SELECT chuc_vu, COUNT(*) AS SoLuongNV FROM NhanVien GROUP BY chuc_vu";
            ResultSet rsGroup = stmt.executeQuery(sql_group);
            System.out.println("\n--- THỐNG KÊ NHÂN VIÊN THEO CHỨC VỤ ---");
            while (rsGroup.next()) {
                System.out.println(
                        "Chức vụ: " + rsGroup.getString("chuc_vu") + " - Số lượng: " + rsGroup.getInt("SoLuongNV"));
            }
            rsGroup.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Lỗi SQL xảy ra: " + e.getMessage());
            // Rollback toàn bộ thay đổi nếu xảy ra sự cố bất ngờ
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("--> Đã thực hiện ROLLBACK giao dịch thành công.");
                } catch (SQLException ex) {
                    System.out.println("Lỗi khi rollback: " + ex.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver SQLite: " + e.getMessage());
        } finally {
            // Đảm bảo đóng tất cả tài nguyên triệt để trong khối finally (chống rò rỉ bộ
            // nhớ/kết nối)
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
                System.out.println("\nĐã đóng toàn bộ tài nguyên an toàn.");
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
}