package com.zxf.joke.ui.view;

import com.zxf.joke.data.entity.JokeBase;
import java.util.List;

/**
 * Created by zhangman on 16/3/29 14:55.
 * Email: zhangman523@126.com
 */
public interface IJokeView<T extends JokeBase> extends ISwipeRefreshView {
  /**
   * 加载成功
   */
  void fillData(List<T> data);

  void appendMoreDataToView(List<T> data);

  void hasNoMoreData();
}
