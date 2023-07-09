package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@WebServlet({"/transfer", "/accountTransferInfo"})
public class AccountTransferInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // OkHttpClient와 Gson 객체 생성
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson =
            new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                @Override
                public Date deserialize(final JsonElement json, final Type typeOfT,
                        final JsonDeserializationContext context) throws JsonParseException {
                    try {
                        return df.parse(json.getAsString());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
                }
            }).create();

    public AccountTransferInfoController() {
        super();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String comPath = req.getContextPath();
        String command = uri.substring(comPath.length());

        if (command.equals("/transfer")) {
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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String comPath = req.getContextPath();
        String command = uri.substring(comPath.length());

        if (command.equals("/accountTransferInfo")) {
            String accountNumber = req.getParameter("accountNumber");
            String bankCode = req.getParameter("bankCode");

            // 개별 은행 서버 URL 리스트
            Map<String, String> bankApiUrlMap = new HashMap<>();
            bankApiUrlMap.put("하나", "http://43.202.60.52/yurim");
            bankApiUrlMap.put("우리", "http://13.125.243.120/gwanjung");

            // JSON 형태로 변환 후 응답
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(
                    transferInfoGetRequest(bankApiUrlMap.get(bankCode), bankCode, accountNumber)));
        }
    }


    private String transferInfoGetRequest(String url, String bankCode, String accountNumber)
            throws IOException {
        // OkHttp를 이용해 Get 요청
        Request request = new Request.Builder()
                .url(url + "/accounts-transfer-response?accountNumber=" + accountNumber).get()
                .build();

        // 요청 보내고 응답 받기
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
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
