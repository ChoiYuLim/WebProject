package service;

import java.util.ArrayList;
import vo.AccountInfoDTO;

public interface AccountInfoService {

    // 모든 계좌정보 가져오기
    ArrayList<AccountInfoDTO> getMyAllAccountInfo(String jumin_num);
    
//    // 관중 계좌정보 가져오기
//    ArrayList<AccountInfoDTO> getOtherAllAccountInfo(String jumin_num);
    
}
