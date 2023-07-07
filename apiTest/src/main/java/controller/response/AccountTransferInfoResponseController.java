package controller.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountTransferService;
import service.AccountTransferServiceImpl;
import java.io.IOException;

/**
 * Servlet implementation class AccountTransferInfoResponseController
 */
@WebServlet({"/withdraw", "/deposit"})
public class AccountTransferInfoResponseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // AccountTransferService 객체 추가 (예시, 실제로는 적절히 구현이 필요합니다.)
    private AccountTransferService accountTransferService = new AccountTransferServiceImpl();

    public AccountTransferInfoResponseController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
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
