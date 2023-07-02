package mailTest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class mailOk
 */

@WebServlet("/mailOk")
public class mailOk extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public mailOk() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        // SendMail 클래스의 naverMailSend 메서드 호출
        String verifyCode = SendMail.naverMailSend(email);
        // response.sendRedirect("/mailTest/main.jsp");

        // JSP 파일로 전달할 데이터 설정
        request.setAttribute("verifyCode", verifyCode);

        // 포워딩
        RequestDispatcher reqDpt = request.getRequestDispatcher("main.jsp");
        reqDpt.forward(request, response);
    }

}
