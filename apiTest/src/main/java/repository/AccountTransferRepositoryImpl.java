package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vo.AccountInfoDTO;
import vo.AccountTransferInfoDTO;

public class AccountTransferRepositoryImpl implements AccountTransferRepository {

    private static AccountTransferRepositoryImpl instance = null;
    private final String DB_URL =
            "jdbc:oracle:thin:@dinkdb_medium?TNS_ADMIN=/opt/wallet/Wallet_DinkDB"; // 데이터베이스 url
    private final String USER = "DA2321";
    private final String PASS = "Data2321";

    public AccountTransferRepositoryImpl() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AccountTransferRepositoryImpl getInstance() {
        if (instance == null) {
            synchronized (MemberRepositoryImpl.class) {
                if (instance == null) {
                    instance = new AccountTransferRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean withdraw(String accountNumber1, int amount) {
        // TODO Auto-generated method stub
        boolean result = false;
        System.out.println("hello");
        String query =
                "UPDATE account_info_hana SET balance = balance - ? WHERE account_number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(query)) {
            System.out.println("hello");
            ps.setInt(1, amount);
            ps.setString(2, accountNumber1);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deposit(String accountNumber2, int amount) {
        // TODO Auto-generated method stub
        boolean result = false;
        String query =
                "UPDATE account_info_hana SET balance = balance + ? WHERE account_number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, amount);
            ps.setString(2, accountNumber2);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void insertTransgerInfo(String accountNumber1, String bankCode1, String accountNumber2,
            String bankCode2, int amount, String content, String string) {
        String queryInsertTransfer =
                "INSERT INTO AccountTransferInfo_hana (transfer_id, account_Number1, account_Number2, tran_Amt, content, inout_Type, tran_Date, tran_Time) "
                        + "VALUES (TRANSFER_ID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), TO_CHAR(SYSDATE,'HH24:MI:SS'))";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement psInsertTransfer = conn.prepareStatement(queryInsertTransfer)) {

            // Insert into AccountTransferInfo_woori
            psInsertTransfer.setString(1, accountNumber1);
            psInsertTransfer.setString(2, accountNumber2);
            psInsertTransfer.setInt(3, amount);
            psInsertTransfer.setString(4, content);
            psInsertTransfer.setString(5, string);

            int rowsInserted = psInsertTransfer.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new transfer was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AccountTransferInfoDTO> findTransferInfoByAccountNumber(String accountNumber) {
        List<AccountTransferInfoDTO> accountTransferInfos = new ArrayList<>();
        String query = "SELECT * FROM accounttransferinfo_hana WHERE account_Number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AccountTransferInfoDTO accountTransferInfo = new AccountTransferInfoDTO(
                            rs.getString("transfer_id"), rs.getString("account_Number1"),
                            rs.getString("account_Number2"), rs.getInt("tran_Amt"),
                            rs.getString("content"), rs.getString("inout_Type"),
                            rs.getString("tran_Date"), rs.getString("tran_Time"));
                    accountTransferInfos.add(accountTransferInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountTransferInfos;
    }


}
