package com.example.yunhoborad.Member.repository;


import com.example.yunhoborad.Member.domain.Member;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Optional<Member> findById(Long id);
}
