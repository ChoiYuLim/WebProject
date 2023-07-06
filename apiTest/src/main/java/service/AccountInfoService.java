package service;

import java.util.List;
import vo.AccountInfoDTO;

public interface AccountInfoService {

    // 멤버 아이디로 계좌 가져오기
    public List<AccountInfoDTO> findAccountsByMemberId(String memberId);

}
