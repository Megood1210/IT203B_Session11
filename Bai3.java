package Bai3;

//Phần 1
//Giá trị trả về của executeUpdate() có ý nghĩa:
//-Có dòng bị cập nhật (thành công)
//-Không có dòng nào bị ảnh hưởng
//Cách để dùng giá trị này nhằm phản hồi chính xác cho y tá rằng "Mã giường này không tồn tại":
//-Dựa vào rowsAffected: if (rowsAffected == 0)
//-nghĩa là không tìm thấy giường thì báo lỗi cho y tá

//Phần 2
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DBContext {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "mat_khau_moi";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

class BedDAO {
    public void updateBedStatus(String bedId) {
        String sql = "UPDATE Beds SET bed_status = 'Occupied' WHERE bed_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bedId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Đã cập nhật giường thành 'Đang sử dụng'");
            } else {
                System.out.println("Mã giường không tồn tại");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Bai3 {
    public static void main(String[] args) {
        BedDAO dao = new BedDAO();

        dao.updateBedStatus("Bed_001");
        dao.updateBedStatus("Bed_999");
    }
}
