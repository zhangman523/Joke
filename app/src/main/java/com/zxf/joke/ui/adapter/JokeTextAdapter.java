package com.zxf.joke.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.zxf.joke.R;
import com.zxf.joke.data.entity.JokeText;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangman on 16/3/30 10:25.
 * Email: zhangman523@126.com
 */
public class JokeTextAdapter extends RecyclerView.Adapter<JokeTextAdapter.JokeTextViewHolder>
    implements View.OnClickListener {
  private List<JokeText> mDatas;
  private OnJokeTextItemClick mOnJokeTextItemClick;

  public JokeTextAdapter() {

  }

  public void update(List<JokeText> data) {
    this.mDatas = data;
    notifyDataSetChanged();
  }

  public void loadMoreData(List<JokeText> data) {
    if (mDatas == null) mDatas = new ArrayList<>();
    mDatas.addAll(data);
    notifyDataSetChanged();
  }

  public void setOnJokeTextItemClick(OnJokeTextItemClick mClick) {
    this.mOnJokeTextItemClick = mClick;
  }

  @Override public JokeTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke_text, null);
    return new JokeTextViewHolder(view);
  }

  @Override public void onBindViewHolder(JokeTextViewHolder holder, int position) {
    holder.bindItem(mDatas.get(position));
    holder.shareIv.setOnClickListener(this);
    holder.parentRl.setOnClickListener(this);
    holder.shareIv.setTag(position);
    holder.parentRl.setTag(position);
  }

  @Override public int getItemCount() {
    return mDatas == null ? 0 : mDatas.size();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.share_iv:
        if (mOnJokeTextItemClick != null) {
          mOnJokeTextItemClick.OnShareClick((int) v.getTag());
        }
        break;
      case R.id.parent_rl:
        if (mOnJokeTextItemClick != null) {
          mOnJokeTextItemClick.OnItemClick((int) v.getTag());
        }
        break;
    }
  }

  public JokeText getItem(int position) {
    return mDatas.get(position);
  }

  static class JokeTextViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.content_label) TextView contentLabel;
    @Bind(R.id.share_iv) ImageView shareIv;
    @Bind(R.id.time_label) TextView timeLabel;
    @Bind(R.id.parent_rl) RelativeLayout parentRl;

    JokeTextViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    void bindItem(JokeText mData) {
      contentLabel.setText(mData.content);
      timeLabel.setText(mData.updatetime);
    }
  }

  public interface OnJokeTextItemClick {
    void OnItemClick(int position);

    void OnShareClick(int position);
  }
}
