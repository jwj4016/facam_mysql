package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    final private PostWriteService postWriteService;

    final private FollowReadService followReadService;

    final private TimelineWriteService timelineWriteService;

    //여기에 트랜잭션을 걸지는 고려해봐야 할 문제다.
    //트랜잭션 설정 시 트랜잭션 범위를 짧게 가져가는게 좋다.
    //팔로워 수가 많은 회원의 경우 글을 하나 쓰면 수백만건의 insert가 발생할 수도 있다.
    //이렇게 되면 트랜잭션을 오램 물고 있게 되고, db 커넥션 풀 고갈로도 이어질 수도...
    //@Transactional
    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);
        var followerMemberIds = followReadService.getFollowers(postCommand.memberId())
                .stream()
                .map(Follow::getFromMemberId)
                .toList();

        timelineWriteService.deliveryToTimeline(postId, followerMemberIds);

        return postId;
    }
}
