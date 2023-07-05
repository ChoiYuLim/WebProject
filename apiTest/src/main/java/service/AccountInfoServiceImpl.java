package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

//    @Override
//    public ArrayList<AccountInfoDTO> getOtherAllAccountInfo(String jumin_num) {
//        try {
//            // 요청 URL
//            String url = "https://gwanjungbank.loca.lt/Board/write_view.do";
//
//            // URL 객체 생성
//            URL requestUrl = new URL(url);
//
//            // HttpURLConnection 생성 및 설정
//            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
//            connection.setRequestMethod("GET");
//
//            // 응답 코드 확인
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // 응답 본문 읽기
//                BufferedReader reader =
//                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // 응답 결과 출력
//                System.out.println(response.toString());
//            } else {
//                System.out.println("Error: " + responseCode);
//            }
//
//            // 연결 종료
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }

}
