package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vo.AccountInfoDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import dao.AccountInfoDAO;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("/accountInfo")
public class AccountController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AccountInfoDAO accountInfoDAO; // AccountInfoDAO 객체를 멤버 변수로 선언

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        accountInfoDAO = new AccountInfoDAO(); // AccountInfoDAO 객체를 초기화
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<AccountInfoDTO> accountList = accountInfoDAO.getAllList(); // AccountInfoDAO를 사용하여
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

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        AccountInfoDTO account = gson.fromJson(reader, AccountInfoDTO.class);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(account));
        out.flush();
    }

}
