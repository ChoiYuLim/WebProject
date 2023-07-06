package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepositoryImpl implements MemberRepository {

    private static MemberRepositoryImpl instance = null;

    private final String DB_URL =
            "jdbc:oracle:thin:@dinkdb_medium?TNS_ADMIN=/opt/wallet/Wallet_DinkDB"; // database url
    private final String USER = "DA2321";
    private final String PASS = "Data2321";

    public MemberRepositoryImpl() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MemberRepositoryImpl getInstance() {
        if (instance == null) {
            synchronized (MemberRepositoryImpl.class) {
                if (instance == null) {
                    instance = new MemberRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public String findMemberIdByPersonalIdNumber(String personalIdNumber) {
        String result = "";
        String query = "SELECT member_id FROM member_hana WHERE personal_id_number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, personalIdNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("member_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving member_id");
            e.printStackTrace();
        }
        return result;
    }
}
