package Signature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbSignature {
    Connection connection;
    PreparedStatement statement;
    ResultSet resultSet;

    public void saveSignatureInUser(ClassSignature classSignature){
        String query = "INSERT INTO signatureorder (userId, publicKey, privateKey, signature) VALUES (?,?,?,?)";
    }
}
