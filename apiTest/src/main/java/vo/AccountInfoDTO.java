package vo;

import java.util.Date;

public class AccountInfoDTO {
    private String accountNumber;
    private String memberId;
    private String bankCode;
    private String branchCode;
    private String accountPassword;
    private int balance;
    private String nickname;
    private int accountType;
    private int accountStatus;
    private Date regDate;


    public AccountInfoDTO(String accountNumber, String memberId, String bankCode, String branchCode,
            String accountPassword, int balance, String nickname, int accountType,
            int accountStatus, Date regDate) {
        this.accountNumber = accountNumber;
        this.memberId = memberId;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.accountPassword = accountPassword;
        this.balance = balance;
        this.nickname = nickname;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.regDate = regDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
