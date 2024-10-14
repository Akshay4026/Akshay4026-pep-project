package Service;



import DAO.AccountDAO;
import Model.Account;

public class AccountService{
    public AccountDAO accountDAO;

    public AccountService(Account account){
        accountDAO = new AccountDAO();
    }
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account addUser(Account account) {
        if(account.getUsername()==null || account.getUsername().strip().isEmpty()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        else if(account.getPassword().length()<4){
            throw new IllegalArgumentException("Name cannot be less then 4 chars");
        }
        else if(accountDAO.UserExistorNot(account.getUsername())){
            throw new IllegalArgumentException("User already exists!");
        }
        Account newUser = accountDAO.UserRegistration(account);
        return newUser;
    }
    public Account UserLogin(Account account){
       
        Account existingUser = accountDAO.UserLogin(account);

        if(existingUser!=null){
            return existingUser;
        }
        else{
            throw new IllegalArgumentException("User not found");
        }
        
    }

}