import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Doctor {
    private String id;
    private String name;
    private String specialty;

    public Doctor(String id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSpecialty() {
        return specialty;
    }
}

class DoctorDAO {
    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("doctor_id"),
                        rs.getString("full_name"),
                        rs.getString("specialty")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách");
        }
        return list;
    }

    public boolean addDoctor(Doctor d) {
        String sql = "INSERT INTO Doctors VALUES (?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getId());
            ps.setString(2, d.getName());
            ps.setString(3, d.getSpecialty());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Mã bác sĩ đã tồn tại");
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm bác sĩ");
        }
        return false;
    }

    public void statisticSpecialty() {
        String sql = "SELECT specialty, COUNT(*) AS total FROM Doctors GROUP BY specialty";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("=== THỐNG KÊ CHUYÊN KHOA ===");

            while (rs.next()) {
                System.out.println(
                        rs.getString("specialty") + " : " + rs.getInt("total")
                );
            }

        } catch (SQLException e) {
            System.out.println("Lỗi thống kê");
        }
    }
}

class DoctorService {
    private DoctorDAO dao = new DoctorDAO();

    public void showDoctors() {
        List<Doctor> list = dao.getAllDoctors();

        if (list.isEmpty()) {
            System.out.println("Không có bác sĩ");
            return;
        }

        System.out.println("=== DANH SÁCH BÁC SĨ ===");
        for (Doctor d : list) {
            System.out.println(d.getId() + " | " + d.getName() + " | " + d.getSpecialty());
        }
    }

    public void addDoctor(Doctor d) {
        if (d.getId().isEmpty() || d.getName().isEmpty() || d.getSpecialty().isEmpty()) {
            System.out.println("Không được để trống");
            return;
        }

        dao.addDoctor(d);
    }

    public void statistic() {
        dao.statisticSpecialty();
    }
}

public class Bai5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoctorService service = new DoctorService();

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát");

            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    service.showDoctors();
                    break;
                case 2:
                    System.out.print("Mã: ");
                    String id = sc.nextLine();

                    System.out.print("Tên: ");
                    String name = sc.nextLine();

                    System.out.print("Chuyên khoa: ");
                    String sp = sc.nextLine();

                    service.addDoctor(new Doctor(id, name, sp));
                    break;
                case 3:
                    service.statistic();
                    break;

                case 4:
                    System.out.println("Thoát chương trình");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }
}