package repository;

public interface AccountTransferRepository {

    boolean withdraw(String accountNumber1, int amount);

    boolean deposit(String accountNumber2, int amount);

    void insertTransgerInfo(String accountNumber1, String bankCode1, String accountNumber2,
            String bankCode2, int amount, String content, String string);
    
    

}