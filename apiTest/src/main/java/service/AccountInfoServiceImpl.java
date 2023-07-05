package service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import repository.AccountInfoRepository;
import repository.AccountInfoRepositoryImpl;
import vo.AccountInfoDTO;

public class AccountInfoServiceImpl implements AccountInfoService {

    @Override
    public ArrayList<AccountInfoDTO> getMyAllAccountInfo(String jumin_num) {

        AccountInfoRepository respositoryImpl = AccountInfoRepositoryImpl.getInstance();

        return respositoryImpl.getAllMyList(jumin_num);

    }

    @Override
    public void getOtherPut() {
        try {
            // 요청을 보낼 URL 생성
            URL url = new URL("https://gwanjungbank.loca.lt/RestfulApi/account");

            // HttpURLConnection 객체 생성 및 설정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);

            // 요청 데이터 생성
            String requestData =
                    "{\"accountNumber1\":\"A123\", \"accountNumber2\":\"B456\", \"tranAmt\":1000}";

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
