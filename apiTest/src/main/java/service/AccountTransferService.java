package service;

import java.util.List;
import vo.AccountTransferInfoDTO;

public interface AccountTransferService {
    boolean withdraw(String accountNumber1, int amount);

    boolean deposit(String accountNumber2, int amount);

    void insertTransferInfo(String accountNumber1, String bankCode1, String accountNumber2,
            String bankCode2, int amount, String content, String string);

    List<AccountTransferInfoDTO> findTransferInfoByAccountNumber(String accountNumber);
}
