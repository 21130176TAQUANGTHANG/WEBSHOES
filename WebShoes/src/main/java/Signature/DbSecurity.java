    package Signature;

    import DBConnect.DBContext;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class DbSecurity {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        public void savePublicKeyToDatabase(String userId, String publicKey) {
            try {
                String query = "INSERT INTO users (userId, publicKey) VALUES (?, ?)";
                conn = new DBContext().getConnection();
                ps = conn.prepareStatement(query);
                ps.setString(1, userId);
                ps.setString(2, publicKey);
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public boolean isPublicKeyExist(String userId) {
            // Thay bằng truy vấn thực tế của bạn
            String query = "SELECT COUNT(*) FROM users WHERE userId = ?";
            try {
                conn = new DBContext().getConnection();
                ps = conn.prepareStatement(query);
                ps.setString(1, userId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) > 0) {
                        return true;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return false;
        }

        public String getPublicKeyFromDatabase(String userId) {
            String query = "SELECT publicKey FROM users WHERE userId = ?";
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
        public void updateKeyEndTime(String userId) {
            String query = "UPDATE users SET endTime = CURRENT_TIMESTAMP WHERE userId = ?";
            try {
                conn = new DBContext().getConnection();
                ps = conn.prepareStatement(query);
                ps.setString(1, userId);
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                closeResources();
            }
        }

        private void closeResources() {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void main(String[] args) {
            DbSecurity db = new DbSecurity();
            db.isPublicKeyExist("1");

        }
    }
