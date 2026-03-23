package Bai4;

//Phần 1
//Luồng thực thi của câu lệnh SQL sau khi bị nối chuỗi:
//-Bước 1: Phân tích điều kiện
//+full_name = '': Sai (thường không có ai tên rỗng)
//-Bước 2: Hacker thêm điều kiện
//+OR '1'='1'. Đây là: '1' = '1'  nên luôn TRUE
//-Kết quả cuối cùng
//WHERE (false) OR (true) trả về TRUE nghĩa là: tất cả bản ghi đều thỏa điều kiện
//Mệnh đề WHERE lại luôn đúng (true) trong trường hợp này vì '1' luôn bằng '1'

//Phần 2
//Giải pháp:
//Bước 1: Viết hàm lọc ký tự nguy hiểm
//public String sanitizeInput(String input) {
//    if (input == null) return null;
//
//    input = input.replace("'", "");
//    input = input.replace("--", "");
//    input = input.replace(";", "");
//
//    return input;
//}
//Bước 2: Áp dụng vào truy vấn
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class PatientDAO {
//    public void searchPatientByName(String patientName) {
//        patientName = sanitizeInput(patientName);
//
//        String sql = "SELECT * FROM Patients WHERE full_name = '" + patientName + "'";
//
//        try (Connection conn = DBContext.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                System.out.println("Tên: " + rs.getString("full_name"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String sanitizeInput(String input) {
//        if (input == null) return null;
//
//        input = input.replace("'", "");
//        input = input.replace("--", "");
//        input = input.replace(";", "");
//
//        return input;
//    }
//}