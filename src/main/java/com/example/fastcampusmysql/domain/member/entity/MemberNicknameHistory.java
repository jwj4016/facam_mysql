package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/*
 * 히스토리성 데이터는 정규화의 대상이 아님.
 * Member 엔티티에도
 */
@Getter
public class MemberNicknameHistory {
    final private Long id;

    final private Long memberId;

    final private String nickname;

    final private LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = nickname;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
