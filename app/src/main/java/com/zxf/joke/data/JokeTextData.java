package com.zxf.joke.data;

import com.zxf.joke.data.entity.JokeText;
import java.util.List;

/**
 * Created by zhangman on 16/3/29 11:48.
 * Email: zhangman523@126.com
 */
public class JokeTextData extends BaseData {
  public Result result;

  class Result {
    public List<JokeText> data;
  }
}
