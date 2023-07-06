package repository;

import java.util.ArrayList;
import java.util.List;
import vo.AccountInfoDTO;

public interface AccountInfoRepository {
    ArrayList<AccountInfoDTO> getAllMyList(String jumin_num);

    ArrayList<AccountInfoDTO> getAllList();

    List<AccountInfoDTO> findAccountInfosByMemberId(String memberId);
}
