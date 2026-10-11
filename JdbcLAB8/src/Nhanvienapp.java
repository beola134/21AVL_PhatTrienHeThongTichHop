import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Nhanvienapp {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Chuẩn bị môi trường và thiết lập kết nối
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:nhanvien.db";
            conn = DriverManager.getConnection(url);

            // --- XỬ LÝ GIAO DỊCH (TRANSACTIONS) ---
            // Tắt chế độ auto-commit để quản lý giao dịch thủ công
            conn.setAutoCommit(false);

            // Tạo đối tượng Statement từ Connection
            stmt = conn.createStatement();

            // (Tùy chọn) Xóa bảng cũ nếu tồn tại để reset dữ liệu sạch sẽ mỗi khi chạy lại
            String sql_drop_table = "DROP TABLE IF EXISTS NhanVien;";
            stmt.executeUpdate(sql_drop_table);

            // Tạo bảng NhanVien
            String sql_create_table = "CREATE TABLE NhanVien ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ten TEXT NOT NULL, "
                    + "chuc_vu TEXT"
                    + ");";
            stmt.executeUpdate(sql_create_table);

            // Thêm dữ liệu nhân viên
            String sql_insert1 = "INSERT INTO NhanVien(ten, chuc_vu) VALUES ('Nguyen Van An', 'Java Developer')";
            String sql_insert2 = "INSERT INTO NhanVien(ten, chuc_vu) VALUES ('Tran Thi Bich', 'Tester')";
            String sql_insert3 = "INSERT INTO NhanVien(ten, chuc_vu) VALUES ('Le Van Cuong', 'Project Manager')";

            stmt.executeUpdate(sql_insert1);
            stmt.executeUpdate(sql_insert2);
            stmt.executeUpdate(sql_insert3);

            // Gọi conn.commit() khi tất cả các thao tác thêm dữ liệu thành công
            conn.commit();
            System.out.println("Đã thêm dữ liệu vào cơ sở dữ liệu!");

            // Truy vấn và hiển thị dữ liệu
            String sql_select = "SELECT id, ten, chuc_vu FROM NhanVien";
            rs = stmt.executeQuery(sql_select);

            System.out.println("\n--- DANH SÁCH NHÂN VIÊN ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String chuc_vu = rs.getString("chuc_vu");
                System.out.println("id: " + id + ", ten: " + ten + ", chuc_vu: " + chuc_vu);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi SQL xảy ra: " + e.getMessage());
            // Gọi conn.rollback() trong khối catch để hủy bỏ các thay đổi nếu có lỗi
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã thực hiện rollback giao dịch thành công.");
                } catch (SQLException ex) {
                    System.out.println("Lỗi khi rollback: " + ex.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver SQLite: " + e.getMessage());
        } finally {
            // Đóng kết nối và giải phóng tài nguyên theo thứ tự ngược lại (ResultSet ->
            // Statement -> Connection)
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                System.out.println("\nĐã đóng tất cả tài nguyên an toàn.");
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}