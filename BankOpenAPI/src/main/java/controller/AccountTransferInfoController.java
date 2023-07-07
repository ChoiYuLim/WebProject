package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/transfer")
public class AccountTransferInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AccountTransferInfoController() {
        super();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accountNumber1 = req.getParameter("accountNumber1");
        String bankCode1 = req.getParameter("bankCode1");
        String accountNumber2 = req.getParameter("accountNumber2");
        String bankCode2 = req.getParameter("bankCode2");
        String amount = req.getParameter("amount");
        String content = req.getParameter("content");

        // 개별 은행 서버 URL 리스트
        Map<String, String> bankApiUrlMap = new HashMap<>();
        bankApiUrlMap.put("하나", "http://43.202.60.52/yurim");
        bankApiUrlMap.put("우리", "http://13.125.243.120/gwanjung");

        // A쪽에서 출금하는 put request
        withdrawPutRequest(bankApiUrlMap.get(bankCode1), bankCode1, accountNumber1, bankCode2,
                accountNumber2, amount, content);

        // B쪽에서 입금되는 put request
        depositPutRequest(bankApiUrlMap.get(bankCode2), bankCode1, accountNumber1, bankCode2,
                accountNumber2, amount, content);

    }

    // A쪽에서 출금하는 put request
    private void withdrawPutRequest(String url, String bankCode1, String accountNumber1,
            String bankCode2, String accountNumber2, String amount, String content)
            throws IOException {
        try {
            // 요청을 보낼 URL 생성
            URL bankUrl = new URL(url + "/withdraw");

            // HttpURLConnection 객체 생성 및 설정
            HttpURLConnection connection = (HttpURLConnection) bankUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);

            String requestData = "{\"bankCode1\":\"" + bankCode1 + "\"," + "\"accountNumber1\":\""
                    + accountNumber1 + "\"," + "\"accountNumber2\":\"" + accountNumber2
                    + "\"bankCode2\":\"" + bankCode2 + "\"," + "\"amount\":" + amount + ","
                    + "\"content\":\"" + content + "\"}";

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

    // B쪽에서 입금되는 put request
    private void depositPutRequest(String url, String bankCode1, String accountNumber1,
            String bankCode2, String accountNumber2, String amount, String content)
            throws IOException {
        try {
            // 요청을 보낼 URL 생성
            URL bankUrl = new URL(url + "/deposit");

            // HttpURLConnection 객체 생성 및 설정
            HttpURLConnection connection = (HttpURLConnection) bankUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);

            String requestData = "{\"bankCode1\":\"" + bankCode1 + "\"," + "\"accountNumber1\":\""
                    + accountNumber1 + "\"," + "\"accountNumber2\":\"" + accountNumber2
                    + "\"bankCode2\":\"" + bankCode2 + "\"," + "\"amount\":" + amount + ","
                    + "\"content\":\"" + content + "\"}";

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

}
