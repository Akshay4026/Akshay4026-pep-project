package Service;
import java.sql.SQLException;

import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {
    public MessageDAO messageDAO;
    public Message message;

    public MessageService(){
        this.messageDAO= new MessageDAO();

    }
    public MessageService(Message message){
        MessageDAO messageDAO = new MessageDAO();
   }

    public Message CreateMessage(Message message) throws SQLException{
        if(message.getMessage_text()==null || message.getMessage_text().length()>255||message.getMessage_text().strip().isEmpty()){
            throw new IllegalArgumentException("Message cant be neither null nor more than 255 charecters");
        }
        else if(!messageDAO.UserExistorNot(message.posted_by)){
            throw new IllegalArgumentException("User Not Found!");
        }
        Message userMessage = messageDAO.CreateMessage(message);
        return userMessage;
    }

    public List<Message> RetrieveMessages(){
        return messageDAO.getAllMessages();
    }

    public Message RetrieveMessagesByid(int id){
         
        Message rMessage= messageDAO.RetrieveMessagesByid(id);
        if(rMessage!=null){
            return rMessage;
        }
        return null;
    }
    public Message DeleteMessageById(int id){
        Message rMessage = messageDAO.DeleteMessageById(id);
        if(rMessage!=null){
            return rMessage;
        }
        return null;
    }

    public Message UpdateMessageText(int id ,Message message){
        if(message.getMessage_text()==null || message.getMessage_text().strip().isEmpty()){
            throw new IllegalArgumentException("Message cant be empty!");
        }
        if(message.getMessage_text().length()>255){
            throw new IllegalArgumentException("Test can't be more than 255 length");
        }

        Message rMessage = messageDAO.UpdateMessageText(id,message.getMessage_text());
        if(rMessage!=null){
            return rMessage;
        }
        else{
            return null;
        }
    }

    public List<Message> RetrieveMessagesForUser(int id ){
        List<Message> rMessage = messageDAO.RetrieveMessagesForUser(id);
        return rMessage;
    }
}
