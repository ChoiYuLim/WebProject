package controller.request;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
import vo.AccountTransferInfoDTO;

/* transfer는 이체 진행, transfer-info는 계좌내역 불러오기 */
@WebServlet({"/transfer", "/transfer-info"})
public class AccountTransferInfoRequestController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public AccountTransferInfoRequestController() {
        super();
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String comPath = request.getContextPath();
        String command = uri.substring(comPath.length());

        if (command.equals("/transfer")) {

            String accountNumber1 = request.getParameter("accountNumber1");
            String bankCode1 = request.getParameter("bankCode1");
            String accountNumber2 = request.getParameter("accountNumber2");
            String bankCode2 = request.getParameter("bankCode2");
            String amount = request.getParameter("amount");
            String content = request.getParameter("content");

            URL url = new URL("http://3.39.24.31/openapi/transfer");

            try {
                // HttpURLConnection 객체 생성 및 설정
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);

                String requestData = "{\"bankCode1\":\"" + bankCode1 + "\","
                        + "\"accountNumber1\":\"" + accountNumber1 + "\"," + "\"accountNumber2\":\""
                        + accountNumber2 + "\"bankCode2\":\"" + bankCode2 + "\"," + "\"amount\":"
                        + amount + "," + "\"content\":\"" + content + "\"}";

                // 요청 데이터를 바이트 배열로 변환
                byte[] postData = requestData.getBytes(StandardCharsets.UTF_8);

                // 요청 본문에 데이터 쓰기
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData);
                outputStream.flush();
                outputStream.close();

                // 응답 코드 확인
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("PUT request succeeded.");
                } else {
                    System.out.println("PUT request failed. Response Code: " + responseCode);
                }

                // 연결 종료
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // String url = "http://3.39.24.31/openapi/transfer?accountNumber1=" + accountNumber1
        // + "&bankCode1=" + bankCode1
        // + "&accountNumber2=" + accountNumber2
        // + "&bankCode2=" + bankCode2
        // + "&amount=" + amount
        // + "&content=" + URLEncoder.encode(content, "UTF-8");
        //
        // Request okHttpRequest = new Request.Builder().url(url).get().build();
        //
        // AccountTransferInfoDTO transferResponse = null;
        //
        // try {
        // Response okHttpResponse = client.newCall(okHttpRequest).execute();
        // if (okHttpResponse.isSuccessful() && okHttpResponse.body() != null) {
        // transferResponse = gson.fromJson(okHttpResponse.body().string(),
        // AccountTransferInfoDTO.class);
        //
        // // Transfer response 출력
        // System.out.println("Transfer response: " + transferResponse);
        //
        // } else {
        // throw new IOException("Unexpected code " + okHttpResponse);
        // }
        // } catch (IOException e) {
        // // 에러 메시지 출력
        // System.out.println("Failed to transfer money: " + e.getMessage());
        // throw new ServletException("Failed to transfer money", e);
        // }
        //
        // response.setContentType("application/json");
        // response.setCharacterEncoding("UTF-8");
        // response.getWriter().write(gson.toJson(transferResponse));
    }

    // 특정 은행 계좌의 거래 내역 모두 조회
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String comPath = req.getContextPath();
        String command = uri.substring(comPath.length());

        if (command.equals("/transfer-info")) {
            String accountNumber = req.getParameter("accountNumber");
            String bankCode = req.getParameter("bankCode");

            String url = "http://3.39.24.31/openapi/accountTransferInfo?accountNumber="
                    + accountNumber + "&bankCode=" + bankCode;

            Request okHttpRequest = new Request.Builder().url(url).get().build();

            List<AccountTransferInfoDTO> accountTransferInfos = new ArrayList<>();

            try {
                Response okHttpResponse = client.newCall(okHttpRequest).execute();
                if (okHttpResponse.isSuccessful() && okHttpResponse.body() != null) {
                    Type listType = new TypeToken<ArrayList<AccountTransferInfoDTO>>() {}.getType();
                    accountTransferInfos = gson.fromJson(okHttpResponse.body().string(), listType);

                    // Account Information 출력
                    System.out.println("Account Information: " + accountTransferInfos);

                } else {
                    throw new IOException("Unexpected code " + okHttpResponse);
                }
            } catch (IOException e) {
                // 에러 메시지 출력
                System.out.println("Failed to get account information: " + e.getMessage());
                throw new ServletException("Failed to get account information", e);
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(accountTransferInfos));
        }

    }

}
