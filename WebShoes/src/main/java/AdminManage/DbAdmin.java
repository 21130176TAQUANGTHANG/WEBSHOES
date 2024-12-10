package AdminManage;

import DBConnect.DBContext;
import LoginUser.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbAdmin {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public User addUser(User user) {
        String sql = "insert into login(username, password, email, phone,address, role) values(?,?,?,?,?,?)";
        try {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setInt(4, user.getPhone());
            pst.setString(5, user.getAddress());
            pst.setInt(6, user.getRole());
            pst.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static void main(String[] args) {
        DbAdmin dbAdmin = new DbAdmin();
//        User user = new User("thang","thang", "thang@gmai.com", 123456, "asdasda",1);
//        System.out.println( "da add thanh cong"+ dbAdmin.addUser(user));



    }


}
