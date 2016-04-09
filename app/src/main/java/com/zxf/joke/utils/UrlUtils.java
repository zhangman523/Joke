package com.zxf.joke.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangman on 16/4/9 11:11.
 * Email: zhangman523@126.com
 */
public class UrlUtils {

  /**
   * 获取链接的后缀名
   */
  public static String parseSuffix(String url) {

    Pattern pattern = Pattern.compile("\\S*[?]\\S*");

    Matcher matcher = pattern.matcher(url);

    String[] spUrl = url.toString().split("/");
    int len = spUrl.length;
    String endUrl = spUrl[len - 1];

    if (matcher.find()) {
      String[] spEndUrl = endUrl.split("\\?");
      return spEndUrl[0].split("\\.")[1];
    }
    return endUrl.split("\\.")[1];
  }
}
