package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OpenAPIController2
 */

@WebServlet("/bankInfo")
public class OpenAPIController2 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String API_GATEWAY_URL = "http://3.39.24.31:8080/";
    private static final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 은행별 API 서브도메인 매핑 정보
        Map<String, String> bankApiUrls = new HashMap<>();
        bankApiUrls.put("A", "https://ylbank.loca.lt/apiTest");
        bankApiUrls.put("B", "https://gwanjungbank.loca.lt/RestfulApi");

        // A은행의 selectAll 메서드 호출하여 결과값 받아오기
        String aBankSelectAllJsonData =
                callBankGETService(API_GATEWAY_URL, bankApiUrls, "A", "/accountInfo");
        JsonElement aBankSelectAllJsonElement =
                gson.fromJson(aBankSelectAllJsonData, JsonElement.class);
        out.println("A은행 selectAll 결과: " + aBankSelectAllJsonElement);

        // PUT 요청에 필요한 데이터
        String requestData =
                "{\"accountNumber1\":\"A123\", \"accountNumber2\":\"B456\", \"tranAmt\":1000}";

        // B은행의 PUT 요청 호출하여 결과값 받아오기
        String bBankPutData =
                callBankPUTService(API_GATEWAY_URL, bankApiUrls, "B", "/account", requestData);
        out.println("B은행 put 결과: " + bBankPutData);
    }

    private static String callBankGETService(String apiGatewayUrl, Map<String, String> bankApiUrls,
            String bankName, String endpoint) {
        OkHttpClient client = new OkHttpClient();

        // 은행별 API URL 조회
        String bankApiUrl = bankApiUrls.get(bankName);
        if (bankApiUrl == null) {
            System.out.println("해당 은행의 API URL을 찾을 수 없습니다.");
            return null;
        }

        // API 호출을 위한 Request 객체 생성
        Request request = new Request.Builder().url(bankApiUrl + endpoint).get().build();
        try {
            // API 호출 및 응답 처리
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String callBankPUTService(String apiGatewayUrl, Map<String, String> bankApiUrls,
            String bankName, String endpoint, String requestData) {
        OkHttpClient client = new OkHttpClient();

        // 은행별 API URL 조회
        String bankApiUrl = bankApiUrls.get(bankName);
        if (bankApiUrl == null) {
            System.out.println("해당 은행의 API URL을 찾을 수 없습니다.");
            return null;
        }

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json"), requestData);

        Request request = new Request.Builder().url(bankApiUrl + endpoint).put(requestBody).build();

        try {
            // API 호출 및 응답 처리
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
