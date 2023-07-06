package service;

import repository.MemberRepository;
import repository.MemberRepositoryImpl;

public class MemberServiceImpl implements MemberService {

    @Override
    public String findMemberIdByPersonalIdNumber(String personalIdNumber) {
        MemberRepository repository = MemberRepositoryImpl.getInstance();

        return repository.findMemberIdByPersonalIdNumber(personalIdNumber);
    }

}
