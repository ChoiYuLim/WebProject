package repository;

public interface MemberRepository {
    String findMemberIdByPersonalIdNumber(String personalIdNumber);
}
