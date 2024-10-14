package DAO;
import Service.MessageService;
import Util.ConnectionUtil;
import Model.Message;


import java.sql.*;
import java.util.*;

public class MessageDAO {
    
    
    public boolean UserExistorNot(int user_id){
        boolean exits = false;
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "Select * from Message where posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                exits= true;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return exits;
    }

//------------------------------------------------------------------------------------------------------------

    public Message CreateMessage(Message message) {
        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "INSERT INTO Message(posted_by,message_text,time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generated_message_id = rs.getInt(1);
               
                return new Message(generated_message_id,message.posted_by,message.message_text,message.time_posted_epoch);
            }
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }

        return null;
    }

//------------------------------------------------------------------------------------------------------------

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();

        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT* FROM Message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

//----------------------------------------------------------------------------------------------------------------------------

    public Message RetrieveMessagesByid(int message_id){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql ="Select * from Message where message_id =?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,message_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Message msgbyid = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return msgbyid;
            }  
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

//---------------------------------------------------------------------------------------------------------------

    public Message DeleteMessageById(int id){
        Message retrMessage = RetrieveMessagesByid(id);
        try {
            Connection connection =  ConnectionUtil.getConnection();
            String sql = "DELETE FROM Message where message_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            int rowsEffected = ps.executeUpdate();
            if(rowsEffected!=0){
                return retrMessage;
            }
            // if(rs.next()){
            //     return new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            // }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

//---------------------------------------------------------------------------------------------------------------------------

    public Message UpdateMessageText(int id , String text){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE Message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(2,id);
            ps.setString(1, text);
            int rowsEffected = ps.executeUpdate();
            if(rowsEffected!=0){
                return RetrieveMessagesByid(id);
            }
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return null;
    }

//-------------------------------------------------------------------------------------------------------------------------------------

    public List<Message> RetrieveMessagesForUser(int id){
        List<Message> messageList = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message WHERE posted_by =?;";
            PreparedStatement  ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message rMessage = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messageList.add(rMessage);
            }
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return messageList;
    }

}
