package Signature;

import DBConnect.DBContext;

import java.sql.*;

public class DbSecurity {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    // Lưu public key vào cơ sở dữ liệu
    public void savePublicKeyToDatabase(String userId, String publicKey, Timestamp createTime, Timestamp endTime) {
        try {
            String query = "INSERT INTO users (userId, publicKey, createTime, endTime) VALUES (?, ?, ?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, publicKey);
            ps.setTimestamp(3, createTime);
            ps.setTimestamp(4, endTime); // endTime có thể là null nếu là khóa mới
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Kiểm tra nếu người dùng đã có public key
    public boolean isPublicKeyExist(String userId) {
        String query = "SELECT COUNT(*) FROM users WHERE userId = ? AND endTime IS NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // Lấy public key từ cơ sở dữ liệu
    public String getPublicKeyFromDatabase(String userId) {
        String query = "SELECT publicKey FROM users WHERE userId = ? AND endTime IS NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("publicKey");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Cập nhật endTime cho public key khi báo mất key
    public void updateEndTime(String userId, Timestamp endTime) {
        try {
            String query = "UPDATE users SET endTime = ? WHERE userId = ? AND endTime IS NULL";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, endTime); // Thời gian khi báo mất key
            ps.setString(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy public key hiện tại từ cơ sở dữ liệu (dùng cho việc báo mất key)
    public String getCurrentPublicKey(String userId) {
        String query = "SELECT publicKey FROM users WHERE userId = ? AND endTime IS NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("publicKey");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void reportLostKey(String userId) {
        String query = "UPDATE users SET endTime = ? WHERE userId = ? AND endTime IS NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            // Thời điểm hiện tại
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(1, currentTime); // Gán endTime = thời điểm hiện tại
            ps.setString(2, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteKey(String userId) {
        String query = "DELETE FROM users WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DbSecurity db = new DbSecurity();

        // Kiểm tra nếu public key tồn tại
        boolean exists = db.isPublicKeyExist("1");
        System.out.println("Public key exists: " + exists);

        // Cập nhật endTime khi báo mất key
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        db.updateEndTime("1", endTime);

        // Lấy public key hiện tại
        String publicKey = db.getPublicKeyFromDatabase("1");
        System.out.println("Current Public Key: " + publicKey);
    }
}
