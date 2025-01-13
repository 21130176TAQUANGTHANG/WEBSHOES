package ForgotPassword;

import DBConnect.DBContext;
import LoginUser.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBforgot {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Boolean checkEmailExists(String email) {
        boolean exists = false;
        try {
            String query = "SELECT * FROM login WHERE email = ?";
            con = new DBContext().getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            // Kiểm tra kết quả truy vấn
            if (rs.next()) {
                exists = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra email: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    public boolean updateNewPassword(String email, String password) {
        boolean exists = false;
        try {
            String query = "UPDATE login SET password = ? WHERE email = ?";
            con = new DBContext().getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
            exists = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return exists;
    }

    public static void main(String[] args) {
        DBforgot dbforgot = new DBforgot();
        boolean exists = dbforgot.checkEmailExists("admian@gmail.com");
        System.out.println(exists);
    }
}
