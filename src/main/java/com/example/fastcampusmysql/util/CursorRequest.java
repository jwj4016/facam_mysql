package com.example.fastcampusmysql.util;

import javax.validation.constraints.Size;

//커서키 설정 시 중복키가 없어야한다. 유니크함이 보장되어야. 그래서 식별자로 커서키를 할 거야!
//클라이언트가 처음 요청 시에는 키가 없을거다. 그래서 default 키를 설정해줄거야.
//클라이언트에서 마지막 데이터인지는 알아야 한다. 없다라는 것을 나타낼 키가 필요함.
public record CursorRequest(Long key, int size) {
    public static final Long NONE_KEY = -1L;

    public Boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
