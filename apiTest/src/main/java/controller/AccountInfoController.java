package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountInfoServiceImpl;
import vo.AccountInfoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Servlet implementation class AccountInfoController
 */
@WebServlet("/accountInfo")
public class AccountInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AccountInfoServiceImpl accountInfoServiceImpl; // AccountInfoDAO 객체를 멤버 변수로 선언

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountInfoController() {
        super();
        accountInfoServiceImpl = new AccountInfoServiceImpl(); // AccountInfoDAO 객체를 초기화
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<AccountInfoDTO> accountList = accountInfoServiceImpl.getMyAllAccountInfo(""); // AccountInfoDAO를
        // 사용하여
        // 계정
        // 정보 조회
        Gson gson = new Gson();
        String jsonAccountList = gson.toJson(accountList);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonAccountList);
        out.flush();
    }


}
