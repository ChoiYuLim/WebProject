package controller;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.AccountInfoRepository;
import repository.AccountInfoRepositoryImpl;
import repository.MemberRepositoryImpl;
import vo.AccountInfoDTO;
/**
 * Servlet implementation class AccountController
 */
@WebServlet("/account")
public class AccountController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    private final MemberRepositoryImpl memberRepository = new MemberRepositoryImpl();
    private final AccountInfoRepository accountInfoDAO = new AccountInfoRepositoryImpl();

    public AccountController() {
        super();
    }
    
    /* 상대방엑서 주민등록번호 기준으로 계좌 조회한 부분 반환 (url로 들어온 부분 로직 수행하여, list로 받아서 json 형태로 반환하는 부분.)
     * --> http://13.125.243.120/gwanjung/account?personalIdNumber=980412-1890123
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String personalIdNumber = request.getParameter("personalIdNumber");

        String memberId = memberRepository.findMemberIdByPersonalIdNumber(personalIdNumber);
        System.out.println(memberId);
        List<AccountInfoDTO> accountInfos = accountInfoDAO.findAccountInfosByMemberId(memberId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(accountInfos));
    }
    
}