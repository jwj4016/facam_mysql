package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsecase {
    final private MemberReadService memberReadService;

    final private FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        /*
            1. fromMemberId = memberId -> Follow list 조회
            2. 1번을 순회하면서 회원정보를 찾는다.
         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
