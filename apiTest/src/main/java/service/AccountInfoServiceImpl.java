package service;

import java.util.List;
import repository.AccountInfoRepository;
import repository.AccountInfoRepositoryImpl;
import vo.AccountInfoDTO;

public class AccountInfoServiceImpl implements AccountInfoService {

    @Override
    public List<AccountInfoDTO> findAccountsByMemberId(String memberId) {
        AccountInfoRepository repository = AccountInfoRepositoryImpl.getInstance();

        return repository.findAccountsByMemberId(memberId);
    }

}
