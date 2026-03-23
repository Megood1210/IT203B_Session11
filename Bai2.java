package Bai2;

//Phần 1
//Lệnh if không đủ để xử lý yêu cầu "in danh sách" vì:
//-Trong code:
//ResultSet rs = stmt.executeQuery("SELECT medicine_name, stock FROM Pharmacy");
//if (rs.next()) {
//    System.out.println("Thuốc: rs.getString("medicine_name")");
//}
//+if (rs.next()) chỉ chạy 1 lần tức là
//+Chỉ lấy dòng đầu tiên
//+Sau đó kết thúc, không chạy dòng print
//Sau mỗi lần gọi next(), con trỏ của ResultSet:
//| Lần gọi        | Vị trí con trỏ |
//| -------------- | -------------- |
//| Chưa gọi       | Trước dòng 1   |
//| `next()` lần 1 | đến dòng 1     |
//| `next()` lần 2 | đến dòng 2     |
//| ...            | ...            |
//| hết dữ liệu    |  false         |

//Phần 2
import java.sql.*;

class DBContext {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "mat_khau_moi";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

class PharmacyDAO {
    public void printAllMedicines() {
        String sql = "SELECT medicine_name, stock FROM Pharmacy";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Không có thuốc nào trong kho!");
                return;
            }

            System.out.println("=== DANH SÁCH THUỐC ===");

            do {
                String name = rs.getString("medicine_name");
                int stock = rs.getInt("stock");

                System.out.println("Thuốc: " + name + " | Tồn kho: " + stock);
            } while (rs.next());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Bai2 {
    public static void main(String[] args) {
        PharmacyDAO dao = new PharmacyDAO();
        dao.printAllMedicines();
    }
}