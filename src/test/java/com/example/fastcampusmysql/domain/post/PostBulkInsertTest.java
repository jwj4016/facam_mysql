package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

//트랜잭셔널 어노테이션 있으면 테스트 진행 후 롤백 된다.
//@Transactional
@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                2L
                , LocalDate.of(1970, 1, 1)
                , LocalDate.of(2022, 2, 1)
        );


        var stopWatch = new StopWatch();
        stopWatch.start();

        var posts = IntStream.range(0, 10000 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .forEach(x -> System.out.println("memberId = " + x.getMemberId() + " / 날짜 : " + x.getCreatedDate())
//                .forEach(postObj -> postRepository.save(postObj)
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간" + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();

        queryStopWatch.start();
        postRepository.bulkInsert(posts);
        queryStopWatch.stop();



        System.out.println("쿼리 수행  시간" + queryStopWatch.getTotalTimeSeconds());
    }
}
