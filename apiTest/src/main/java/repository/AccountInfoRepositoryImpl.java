package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vo.AccountInfoDTO;

public class AccountInfoRepositoryImpl implements AccountInfoRepository {

    private static AccountInfoRepositoryImpl instance = null;

    private final String DB_URL =
            "jdbc:oracle:thin:@dinkdb_medium?TNS_ADMIN=/opt/wallet/Wallet_DinkDB"; // 데이터베이스 url
    private final String USER = "DA2321";
    private final String PASS = "Data2321";

    public AccountInfoRepositoryImpl() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AccountInfoRepositoryImpl getInstance() {
        if (instance == null) {
            synchronized (AccountInfoRepositoryImpl.class) {
                if (instance == null) {
                    instance = new AccountInfoRepositoryImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public List<AccountInfoDTO> findAccountsByMemberId(String memberId) {
        List<AccountInfoDTO> accountInfos = new ArrayList<>();
        String query = "SELECT * FROM account_info_hana WHERE member_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AccountInfoDTO accountInfo = new AccountInfoDTO(rs.getString("account_number"),
                            rs.getString("member_id"), rs.getString("bank_code"),
                            rs.getString("branch_code"), rs.getString("account_password"),
                            rs.getInt("balance"), rs.getString("nickname"),
                            rs.getInt("account_type"), rs.getInt("account_status"),
                            rs.getDate("reg_date"));
                    accountInfos.add(accountInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountInfos;
    }

}
