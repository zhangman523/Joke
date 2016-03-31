package com.zxf.joke.core;

import com.zxf.joke.data.JokeImageData;
import com.zxf.joke.data.JokeTextData;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by zhangman on 16/3/29 10:43.
 * Email: zhangman523@126.com
 */
public interface Joke {
  @GET("/content/text.from")
  Observable<JokeTextData> getJokeTextData(@Query("key") String key, @Query("page") int page,
      @Query("pagesize") int pageSize);

  @GET("/img/text.from")
  Observable<JokeImageData> getJokeImageData(@Query("key") String key, @Query("page") int page,
      @Query("pagesize") int pageSize);
}
