package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FollowWriteService {
    final private FollowRepository followRepository;

    // 멤버 식별자를 파라미터로 받을경우 (param : Long fromMemberId, Long toMemberId)
    // 식별자 검증을 위해 팔로우 서비스에서 도메인 repository나 service를 주입 받아야한다. 결합이 심해진다.
    // MemberDto는 어디서 올까?
    // 서로 다른 도메인의 데이터 주고 받을 때, 혹은 서로 다른 도메인 간 흐름 제어를 어디서 해야하나?
    // 헥사고날 아키텍처, ddd, 레이어드 아키텍처. 경계간 통신을 위한 여러가지 이론이 있다.
    public void create(MemberDto fromMember, MemberDto toMember) {
        /*
            from, to 회원 정보를 받아서
            저장...
            from <-> to validate
         */
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");

        var follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();

        followRepository.save(follow);
    }
}
