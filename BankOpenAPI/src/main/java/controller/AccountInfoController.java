package controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
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

@WebServlet("/accountInfo")
public class AccountInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // OkHttpClient와 Gson 객체 생성
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson=new GsonBuilder().registerTypeAdapter(Date.class,new JsonDeserializer<Date>(){DateFormat df=new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);

    @Override public Date deserialize(final JsonElement json,final Type typeOfT,final JsonDeserializationContext context)throws JsonParseException{try{return df.parse(json.getAsString());}catch(ParseException e){throw new JsonParseException(e);}}}).create();

    public AccountInfoController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 요청받은 주민등록번호 파라미터
        String personalIdNumber = request.getParameter("personalIdNumber");

        // 주민등록번호로 조회한 계좌정보들을 담을 List
        List<AccountInfoDTO> accountInfos = new ArrayList<>();

        // 개별 은행 서버 URL 리스트 13.125.243.120/gwanjung/account
        List<String> bankApiUrls = List.of("http://13.125.243.120/gwanjung");
        

        // 각 은행의 API로 요청을 보내고 응답 받아와 List에 추가
        for (String bankApiUrl : bankApiUrls) {
            String bankResponse = sendGetRequest(bankApiUrl, personalIdNumber);
            Type listType = new TypeToken<ArrayList<AccountInfoDTO>>() {}.getType();
            List<AccountInfoDTO> bankAccountInfos = gson.fromJson(bankResponse, listType);
            accountInfos.addAll(bankAccountInfos);
        }

        // JSON 형태로 변환 후 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(accountInfos));
    }

    // 은행 API에 Get 요청을 보내고 응답 받는 메소드
    private String sendGetRequest(String url, String personalIdNumber) throws IOException {
        // OkHttp를 이용해 Get 요청
        Request request = new Request.Builder()
                .url(url + "/account?personalIdNumber=" + personalIdNumber).get().build();

        // 요청 보내고 응답 받기
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
