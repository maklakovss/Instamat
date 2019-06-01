package com.mss.instamat.network;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.mss.instamat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RetrofitActivity extends MvpAppCompatActivity implements RetrofitView {

    @InjectPresenter
    RetrofitPresenter presenter;

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGetAvatar)
    public void onGetAvatarClick(View view) {
        presenter.onGetAvatarClick();
    }

    @ProvidePresenter
    public RetrofitPresenter providePresenter() {
        return new RetrofitPresenter();
    }

    @Override
    public void showAvatar(String avatarUrl) {
        Glide
                .with(this)
                .load(avatarUrl)
                .into(ivAvatar);
    }
}
