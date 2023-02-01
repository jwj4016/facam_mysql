package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;

public class MemberWriteService {
    public void create(RegisterMemberCommand command) {

        /**
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
    }
}
