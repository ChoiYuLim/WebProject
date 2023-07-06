package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import vo.AccountInfoDTO;

public class AccountInfoRepositoryImpl implements AccountInfoRepository {

    // Connection conn = null;
    // ResultSet rs = null;
    private static AccountInfoRepositoryImpl instance = null;
    // private DataSource dataSource;
    // private PreparedStatement pstmt; // PreparedStatement 필드로 선언


    private final String DB_URL =
            "jdbc:oracle:thin:@dinkdb_medium?TNS_ADMIN=/opt/wallet/Wallet_DinkDB"; // your database
                                                                                   // url
    private final String USER = "DA2321"; // your database username
    private final String PASS = "Data2321"; // your database password


    // String id = null, memberId = null, accountNumber = null, accountPassword = null,
    // nickname = null, bankCode = null, branchCode = null;
    // int balance = 0, accountType = 1, accountStatus;
    // Date regDate = null;

    public AccountInfoRepositoryImpl() {
        try {
            // InitialContext initContext = new InitialContext();
            // dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/oracle");
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

    // public ArrayList<AccountInfoDTO> getAllMyList(String jumin_num) {
    // ArrayList<AccountInfoDTO> dtos = new ArrayList<AccountInfoDTO>();
    // try {
    // conn = dataSource.getConnection();
    // pstmt = conn.prepareStatement("SELECT * FROM account_info");
    //
    //
    // // pstmt.setString(1, jumin_num);
    // rs = pstmt.executeQuery();
    //
    // while (rs.next()) {
    // memberId = rs.getString("MEMBER_ID");
    // accountNumber = rs.getString("ACCOUNT_NUMBER");
    // accountPassword = rs.getString("ACCOUNT_PASSWORD");
    // balance = rs.getInt("BALANCE");
    // nickname = rs.getString("NICKNAME");
    // branchCode = rs.getString("branchCode");
    // accountType = rs.getInt("ACCOUNT_TYPE");
    // accountStatus = rs.getInt("ACCOUNT_STATUS");
    // bankCode = rs.getString("BANK_CODE");
    // regDate = rs.getDate("REG_DATE");
    //
    // AccountInfoDTO dto = new AccountInfoDTO(accountNumber, memberId, bankCode,
    // branchCode, accountPassword, balance, nickname, accountType, accountStatus,
    // regDate);
    // dtos.add(dto);
    // System.out.println(dto);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // try {
    // if (rs != null)
    // rs.close();
    // if (pstmt != null)
    // pstmt.close();
    // if (conn != null)
    // conn.close();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // return dtos;
    // }

    // 계좌 리스트 전부 불러오는 함수
    @Override
    public ArrayList<AccountInfoDTO> getAllList() {
        ArrayList<AccountInfoDTO> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM account_info";
            PreparedStatement pstmt;
            ResultSet rs;

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
                accountInfoDTO.setAccountNumber(rs.getString("account_number"));
                accountInfoDTO.setMemberId(rs.getString("member_id"));
                accountInfoDTO.setAccountPassword(rs.getString("account_password"));
                accountInfoDTO.setBalance(rs.getInt("balance"));
                accountInfoDTO.setNickname(rs.getString("nickname"));
                accountInfoDTO.setAccountType(rs.getInt("account_type"));
                accountInfoDTO.setAccountStatus(rs.getInt("account_status"));
                accountInfoDTO.setBankCode(rs.getString("bank_code"));
                accountInfoDTO.setRegDate(rs.getDate("reg_date"));
                list.add(accountInfoDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<AccountInfoDTO> findAccountInfosByMemberId(String memberId) {
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

    @Override
    public ArrayList<AccountInfoDTO> getAllMyList(String jumin_num) {
        // TODO Auto-generated method stub
        return null;
    }



}
