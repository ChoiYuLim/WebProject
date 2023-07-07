package controller.response;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountInfoService;
import service.AccountInfoServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;
import vo.AccountInfoDTO;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("/accounts-response")
public class AccountInfoResponseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    private final MemberService memberService = new MemberServiceImpl();
    private final AccountInfoService accountInfoService = new AccountInfoServiceImpl();

    public AccountInfoResponseController() {
        super();
    }

    /*
     * 상대방에게서 주민등록번호 기준으로 계좌 조회한 부분 반환 (url로 들어온 부분 로직 수행하여, list로 받아서 json 형태로 반환하는 부분.) -->
     * http://13.125.243.120/gwanjung/account?personalIdNumber=980412-1890123
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String personalIdNumber = request.getParameter("personalIdNumber");

        String memberId = memberService.findMemberIdByPersonalIdNumber(personalIdNumber);
        System.out.println(memberId);
        List<AccountInfoDTO> accountInfos = accountInfoService.findAccountsByMemberId(memberId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(accountInfos));
    }

}
