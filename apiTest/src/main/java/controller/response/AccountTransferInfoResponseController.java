package controller.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountTransferService;
import service.AccountTransferServiceImpl;
import vo.AccountInfoDTO;
import vo.AccountTransferInfoDTO;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

/**
 * Servlet implementation class AccountTransferInfoResponseController
 */
@WebServlet({"/withdraw", "/deposit", "/accounts-transfer-response"})
public class AccountTransferInfoResponseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    // AccountTransferService 객체 추가 (예시, 실제로는 적절히 구현이 필요합니다.)
    private AccountTransferService accountTransferService = new AccountTransferServiceImpl();

    public AccountTransferInfoResponseController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String comPath = req.getContextPath();
        String command = uri.substring(comPath.length());
        if (command.equals("/accounts-transfer-response")) {
            String accountNumber = req.getParameter("accountNumber");

            List<AccountTransferInfoDTO> accountInfos =
                    accountTransferService.findTransferInfoByAccountNumber(accountNumber);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(accountInfos));

        }

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String comPath = request.getContextPath();
        String command = uri.substring(comPath.length());
        String accountNumber1 = request.getParameter("accountNumber1");
        String bankCode1 = request.getParameter("bankCode1");
        String accountNumber2 = request.getParameter("accountNumber2");
        String bankCode2 = request.getParameter("bankCode2");
        int amount = Integer.parseInt(request.getParameter("amount"));
        String content = request.getParameter("content");
        System.out.println("start");
        if (command.equals("/withdraw")) {
            // 출금 로직
            boolean result = accountTransferService.withdraw(accountNumber1, amount);

            // DB에 거래 정보 추가
            if (result) {
                accountTransferService.insertTransferInfo(accountNumber1, bankCode1, accountNumber2,
                        bankCode2, amount, content, "OUT");
            }
        } else if (command.equals("/deposit")) {
            // 입금 로직
            boolean result = accountTransferService.deposit(accountNumber2, amount);

            // DB에 거래 정보 추가
            if (result) {
                accountTransferService.insertTransferInfo(accountNumber1, bankCode1, accountNumber2,
                        bankCode2, amount, content, "IN");
            }
        }
    }
}
