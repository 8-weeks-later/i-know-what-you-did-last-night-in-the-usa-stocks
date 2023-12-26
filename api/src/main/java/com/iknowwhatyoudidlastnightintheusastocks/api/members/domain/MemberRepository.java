package com.iknowwhatyoudidlastnightintheusastocks.api.members.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

  Optional<Member> findByEmail(String email);
}
