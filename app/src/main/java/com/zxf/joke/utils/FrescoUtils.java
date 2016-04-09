package com.zxf.joke.utils;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by zhangman on 16/4/8 14:25.
 * Email: zhangman523@126.com
 */
public class FrescoUtils {
  public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
    if (simpleDraweeView == null || TextUtils.isEmpty(url)) {
      throw new IllegalArgumentException("IllegalArgumentException freco");
    }
    Uri uri = Uri.parse(url);
    simpleDraweeView.setImageURI(uri);
  }

  public static void loadGif(final SimpleDraweeView simpleDraweeView, String url) {
    if (simpleDraweeView == null || TextUtils.isEmpty(url)) {
      throw new IllegalArgumentException("IllegalArgumentException freco");
    }
    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
      @Override public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo,
          @Nullable Animatable anim) {
        if (anim != null) {
          // app-specific logic to enable animation starting
          anim.start();
        }
        if (imageInfo != null) {
          simpleDraweeView.setAspectRatio(imageInfo.getWidth() / imageInfo.getHeight() <= 0 ? 1
              : imageInfo.getWidth() / imageInfo.getHeight());
        }
      }
    };
    Uri uri = Uri.parse(url);
    DraweeController controller =
        Fresco.newDraweeControllerBuilder().setUri(uri).setControllerListener(controllerListener)
            // other setters
            .build();
    GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
    hierarchy.setProgressBarImage(new ProgressBarDrawable());

    simpleDraweeView.setHierarchy(hierarchy);
    simpleDraweeView.setController(controller);
  }
}
