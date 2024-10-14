package Controller;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Service.AccountService;

import Model.Message;
import Service.MessageService;

import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    ObjectMapper objectMapper;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new  AccountService(); 
        objectMapper = new ObjectMapper();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register",this::UserRegistration);
        app.post("/login",this::UserLogin);
        app.post("/messages",this::CreateMessage);
        app.get("/messages",this::RetrieveMessages);
        app.get("/messages/{message_id}", this ::RetrieveMessagesByid);
        app.delete("/messages/{message_id}",this ::DeleteMessageById );
        app.patch("/messages/{message_id}",this :: UpdateMessageText);
        app.get("/accounts/{account_id}/messages", this ::RetrieveMessagesForUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
//---------------------------------------------------------------------------------------------------------------
    //1
    private void UserRegistration(Context ctx) throws  JsonProcessingException {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            Account userRegistered = accountService.addUser(account);
            if (userRegistered != null) {
                ctx.status(200).json(userRegistered); 
            } 
        } catch (IllegalArgumentException e) {
            ctx.status(400); 
        } 
    }

//---------------------------------------------------------------------------------------------------- 
    //2
    private void UserLogin(Context ctx){
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            Account userRegistered = accountService.UserLogin(account);
            if(userRegistered!=null){
                ctx.status(200).json(userRegistered);
            }
        } catch (Exception e) {
            ctx.status(401);
        }
    }

//==========================================================================================================================================
    //3
    private void CreateMessage(Context ctx) throws JsonMappingException, JsonProcessingException{
        try{
        Message message = objectMapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.CreateMessage(message);
        if(createdMessage!=null){
            ctx.status(200).json(createdMessage);
        }
        }
        catch(IllegalArgumentException e){
            ctx.status(400);
        }
        catch(SQLException e){
            ctx.status(401);
        }
    }

//---------------------------------------------------------------------------------------------------------
    //4
    private void RetrieveMessages(Context ctx){

        List<Message> messages = messageService.RetrieveMessages();
        ctx.status(200).json(messages);
    }

//------------------------------------------------------------------------------------------------------------    
    //5
    private void RetrieveMessagesByid(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messagesById = messageService.RetrieveMessagesByid(id);
        if (messagesById != null) {
            ctx.status(200).json(messagesById);  
        } else {
            ctx.status(200).json(""); 
        }
    }

//-------------------------------------------------------------------------------------------------------------
//6
    private void DeleteMessageById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.DeleteMessageById(id);
        if(msg!=null){
            ctx.status(200).json(msg);
        }
        else{
            ctx.status(200).json("");
        }
    }

//------------------------------------------------------------------------------------------------------------
    //7
    private void UpdateMessageText(Context ctx) throws JsonMappingException, JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        try{
        Message message = objectMapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.UpdateMessageText(id,message);
        if(createdMessage!=null){
            ctx.status(200).json(createdMessage);
        }
        else{
            ctx.status(400);
        }
        }
        catch(IllegalArgumentException e){
            ctx.status(400);
        }
    }

//------------------------------------------------------------------------------------------------------------
    //8
    private void RetrieveMessagesForUser(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List <Message> rMessage = messageService.RetrieveMessagesForUser(id);
        if(rMessage!=null){
            ctx.status(200).json(rMessage);
        }
        else{
            ctx.status(200).json(rMessage);
        }
    }

//==================================================================================================================


}