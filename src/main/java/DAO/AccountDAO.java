package DAO;

import Model.Account;
import Util.ConnectionUtil;


import java.sql.*;



public class AccountDAO {
    

    public Boolean UserExistorNot(String username){
        boolean exists = false;
        Connection connection = ConnectionUtil.getConnection();
        String sql ="Select * from Account where username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                exists =true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
        }

        return exists;
    }

    public Account UserRegistration(Account account) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            
                String sql = "INSERT INTO Account(username,password) VALUES(?,?);";
                PreparedStatement pStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, account.getUsername());
                pStatement.setString(2, account.getPassword()); 
                pStatement.executeUpdate();
                ResultSet rs = pStatement.getGeneratedKeys();
                if(rs.next()){
                    int generated_account_id = rs.getInt(1);
                    return new Account(generated_account_id,account.getUsername(),account.getPassword());
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            return null;
    }
    public Account UserLogin(Account account){

        Connection connection = ConnectionUtil.getConnection();
        String sql ="Select * from Account where username = ? and password =?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                int generated_account_id = rs.getInt("account_id"); 
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new Account(generated_account_id,username,password);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
        }
        return null;
    }
}
