package controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vo.AccountInfoDTO;

@WebServlet("/start")
public class A_BankClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public A_BankClient() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String personalIdNumber = request.getParameter("personalIdNumber");

        String url = "http://3.39.24.31/openapi/accountInfo?personalIdNumber=" + personalIdNumber;

        Request okHttpRequest = new Request.Builder().url(url).get().build();

        List<AccountInfoDTO> accountInfos = new ArrayList<>();

        try {
            Response okHttpResponse = client.newCall(okHttpRequest).execute();
            if (okHttpResponse.isSuccessful() && okHttpResponse.body() != null) {
                Type listType = new TypeToken<ArrayList<AccountInfoDTO>>() {}.getType();
                accountInfos = gson.fromJson(okHttpResponse.body().string(), listType);

                // Account Information 출력
                System.out.println("Account Information: " + accountInfos);

            } else {
                throw new IOException("Unexpected code " + okHttpResponse);
            }
        } catch (IOException e) {
            // 에러 메시지 출력
            System.out.println("Failed to get account information: " + e.getMessage());
            throw new ServletException("Failed to get account information", e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(accountInfos));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
