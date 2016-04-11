package com.zxf.joke.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zxf.joke.R;
import com.zxf.joke.data.entity.JokeImage;
import com.zxf.joke.utils.FrescoUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangman on 16/3/31 10:58.
 * Email: zhangman523@126.com
 */
public class JokeImageAdapter extends RecyclerView.Adapter<JokeImageAdapter.ViewHolder>
    implements View.OnClickListener {
  private List<JokeImage> mDatas;
  private Fragment mFragment;
  private IOnItemClickListener mIOnItemClickListener;

  public JokeImageAdapter(Fragment fragment) {
    this.mFragment = fragment;
  }

  public void update(List<JokeImage> data) {
    this.mDatas = data;

    notifyDataSetChanged();
  }

  public void loadMoreData(List<JokeImage> data) {
    if (mDatas == null) mDatas = new ArrayList<>();
    mDatas.addAll(data);
    notifyDataSetChanged();
  }

  public JokeImage getItem(int position) {
    return mDatas.get(position);
  }

  public void setIOnItemClickListener(IOnItemClickListener onItemClickListener) {
    this.mIOnItemClickListener = onItemClickListener;
  }

  @Override public JokeImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke_image, null);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(JokeImageAdapter.ViewHolder holder, int position) {
    holder.bindItem(mDatas.get(position));
    holder.shareIv.setOnClickListener(this);
    holder.shareIv.setTag(position);
  }

  @Override public int getItemCount() {
    return mDatas == null ? 0 : mDatas.size();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.share_iv:
        if (mIOnItemClickListener != null) {
          mIOnItemClickListener.OnShareClick((Integer) v.getTag());
        }
        break;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.content_label) TextView contentLabel;
    @Bind(R.id.joke_image) SimpleDraweeView jokeImage;
    @Bind(R.id.share_iv) ImageView shareIv;
    @Bind(R.id.time_label) TextView timeLabel;
    @Bind(R.id.parent_rl) RelativeLayout parentRl;

    ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    void bindItem(JokeImage data) {
      contentLabel.setText(data.content);
      timeLabel.setText(data.updatetime);
      if (data.url.endsWith(".gif")) {
        FrescoUtils.loadGif(jokeImage, data.url);
      } else {
        FrescoUtils.loadImage(jokeImage, data.url);
      }
    }
  }

  public interface IOnItemClickListener {
    void OnItemClick(int position);

    void OnShareClick(int position);
  }
}
