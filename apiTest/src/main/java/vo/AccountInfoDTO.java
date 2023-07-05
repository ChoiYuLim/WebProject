package vo;

import java.util.Date;

public class AccountInfoDTO {
    private String memberId;
    private String accountNumber;
    private String accountPassword;
    private int balance = 0;
    private String nickname;
    private int accountType;
    private int accountStatus = 1;
    private String bankCode;
    private Date regDate;

    public AccountInfoDTO(String memberId, String accountNumber, String accountPassword,
            int balance, String nickname, int accountType, int accountStatus, String bankCode,
            Date regDate) {
        super();
        this.memberId = memberId;
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
        this.balance = balance;
        this.nickname = nickname;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.bankCode = bankCode;
        this.regDate = regDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }


}
