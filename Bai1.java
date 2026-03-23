//Phần 1
//Gây nguy hiểm (đặc biệt với hệ thống bệnh viện 24/7) vì:
//-Cạn kiệt tài nguyên Database
//+MySQL chỉ cho phép số lượng connection giới hạn (ví dụ: 100–300)
//+Không đóng thì connection tăng dần đến full
//+Khi full thì lỗi Communications link failure

//Phần 2
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class PatientDAO {

    public void testConnection() {
        Connection conn = null;

        try {
            conn = DBContext.getConnection();

            System.out.println("Kết nối thành công");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBContext.closeConnection(conn);
        }
    }
}

class DBContext {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "mat_khau_moi";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Bai1 {
    public static void main(String[] args) {
        PatientDAO dao = new PatientDAO();
        dao.testConnection();
    }
}