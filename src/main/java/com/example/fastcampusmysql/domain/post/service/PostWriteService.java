package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostWriteService {
    final private PostRepository postRepository;

    public Long create(PostCommand command) {
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();

        return postRepository.save(post).getId();
    }

    //동시성 문제 발생!
    //락을 걸어준다.
    @Transactional
    public void likePost(long postId) {
        var post = postRepository.findById(postId, true).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }

    //트랜잭션 안걸림. 락도 안걸림. 성능도 더 좋아!
    //정합성이 중요한 경우 비관적 락과 낙관적 락을 동시에 사용한다.
    //정합성이 중요한 경우 옵티미스틱락킹 어노테이션을 엔티티에 추가해서 옵티미스틱 라킹 구현 가능.
    //분산 환경에서느 비관적 락을 위의 likePost처럼 간단하게 쓸 수 없다.
    public void likePostByOptimisticLock(long postId) {
        var post = postRepository.findById(postId, false).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }
}
