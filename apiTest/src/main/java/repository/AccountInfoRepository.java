package repository;

import java.util.ArrayList;
import vo.AccountInfoDTO;

public interface AccountInfoRepository {
    ArrayList<AccountInfoDTO> getAllMyList(String jumin_num);
}
