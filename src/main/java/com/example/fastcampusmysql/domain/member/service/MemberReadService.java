package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    final private MemberRepository memberRepository;

    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        var member = memberRepository.findById(id).orElseThrow();
        return toDto(member);

    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository
                .findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    //객체와 객체를 연결해주는 mapping layer를 두기도 한다. 하지만 프로젝트 사이즈가 작기 때문에 서비스에 public으로 메소드를 제공한다.
    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory memberNicknameHistory) {
        return new MemberNicknameHistoryDto(
                memberNicknameHistory.getId()
                , memberNicknameHistory.getMemberId()
                , memberNicknameHistory.getNickname()
                , memberNicknameHistory.getCreatedAt()
        );
    }

}
