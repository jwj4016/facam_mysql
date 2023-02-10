package com.example.fastcampusmysql.domain.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 팔로우 할 때 항상 닉네임이 보여짐.
 * 닉네임 변경은 자주 있는 일이기 때문에, 해당 테이블에서 닉네임 컬럼을 가지지 않는다. 정규화를 수행한다.
 * 닉네임 가져오기 방법1.조인, 방법2.select 한번 더, 방법3.별도 저장소 사용.
 * 팔로우해서 멤버 조인 시 팔로우 서비스에 멤버 도메인이 침투가 된다. 결합이 강해진다.
 * 프로젝트 초기부터 결합이 강해지면 유연성(확장성)이 떨어진다.
 * 그러므로 join이 아닌 쿼리를 한번 더 수행할 예정.
 * 조인은 될 수 있으면 안쓰는 방향으로!!
 */
@Getter
public class Follow {
    final private Long id;

    //팔로우 한 사람
    final private Long fromMemberId;

    //팔로우 당한 사람
    final private Long toMemberId;

    final private LocalDateTime createdAt;

    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        //중복되는 코드가 발생하는데 이를 추상화 시킬 좋은 방법 찾아보기!!
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
