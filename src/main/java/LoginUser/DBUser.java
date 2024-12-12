package LoginUser;

import DBConnect.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUser {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

//    public List<User> getInforUser() {
//        String sql = "select * from user";
//        List<User> users = new ArrayList<User>();
//        try {
//            conn = new DBContext().getConnection();
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                User user = new User(rs.getString());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
