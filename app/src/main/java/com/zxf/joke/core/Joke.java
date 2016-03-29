package com.zxf.joke.core;

import com.zxf.joke.data.JokeImageData;
import com.zxf.joke.data.JokeTextData;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by zhangman on 16/3/29 10:43.
 * Email: zhangman523@126.com
 */
public interface Joke {
  @GET("/content/text.from?key={Key}&page={Page}&pagesize={PageSize}")
  Observable<JokeTextData> getJokeTextData(@Path("Key") String key, @Path("Page") int page,
      @Path("PageSize") int pageSize);

  @GET("/content/text.from?key={Key}&page={Page}&pagesize={PageSize}")
  Observable<JokeImageData> getJokeImageData(@Path("Key") String key, @Path("Page") int page,
      @Path("PageSize") int pageSize);
}
