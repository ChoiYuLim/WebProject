package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import vo.AccountInfoDTO;

public class AccountInfoRepositoryImpl implements AccountInfoRepository {

    Connection conn = null;
    ResultSet rs = null;
    private static AccountInfoRepositoryImpl instance = null;
    private DataSource dataSource;
    private PreparedStatement pstmt; // PreparedStatement 필드로 선언

    String id = null, memberId = null, accountNumber = null, accountPassword = null,
            nickname = null, bankCode = null;
    int balance = 0, accountType = 1, accountStatus;
    Date regDate = null;

    private AccountInfoRepositoryImpl() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/oracle");
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

    public ArrayList<AccountInfoDTO> getAllMyList(String jumin_num) {
        ArrayList<AccountInfoDTO> dtos = new ArrayList<AccountInfoDTO>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM account_info");


            // pstmt.setString(1, jumin_num);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                memberId = rs.getString("MEMBER_ID");
                accountNumber = rs.getString("ACCOUNT_NUMBER");
                accountPassword = rs.getString("ACCOUNT_PASSWORD");
                balance = rs.getInt("BALANCE");
                nickname = rs.getString("NICKNAME");
                accountType = rs.getInt("ACCOUNT_TYPE");
                accountStatus = rs.getInt("ACCOUNT_STATUS");
                bankCode = rs.getString("BANK_CODE");
                regDate = rs.getDate("REG_DATE");

                AccountInfoDTO dto = new AccountInfoDTO(memberId, accountNumber, accountPassword,
                        balance, nickname, accountType, accountStatus, bankCode, regDate);
                dtos.add(dto);
                System.out.println(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dtos;
    }




}
