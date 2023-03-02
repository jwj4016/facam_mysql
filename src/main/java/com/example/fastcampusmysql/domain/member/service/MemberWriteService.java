package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    @Transactional
    public Member register(RegisterMemberCommand command) {

        /*
         * 목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다. 닉네임은 10자를 넘길 수 없다.
         * 파라미터 - memberRegisterCommand
         * val member = Member.of(memberRegisterCommand)
         * memberRepository.save(member)
         */
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        var savedMember = memberRepository.save(member);
        //트랜잭션 확인을 위해 에러 발생시키는 코드 추가
        //var zero = 0 / 0;

        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }
    /*
    아래와 같은 방식으로 코드 작성 후 register 메소드에서 아래 메소드 호출 시 트랜잭션 안먹는다.
    프록시 방식으로 호출하기 때문.
    @Transactional
    Member getMember(RegisterMemberCommand command) {
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        var savedMember = memberRepository.save(member);
        //트랜잭션 확인을 위해 에러 발생시키는 코드 추가
        //var zero = 0 / 0;

        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    */

    public void changeNickname(Long memberId, String nickname) {
        /*
            1.회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }

}
