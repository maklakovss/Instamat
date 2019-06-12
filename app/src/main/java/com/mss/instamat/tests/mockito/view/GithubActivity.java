package com.mss.instamat.tests.mockito.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.R;
import com.mss.instamat.tests.mockito.di.App;
import com.mss.instamat.tests.mockito.presenter.GithubFieldPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GithubActivity extends MvpAppCompatActivity implements GithubView {

    @BindView(R.id.tvName)
    TextView tvName;

//    @Inject
//    @InjectPresenter
//    GithubConstructorPresenter presenter;

    @InjectPresenter
    GithubFieldPresenter presenter;

    @NonNull
    @ProvidePresenter
    public GithubFieldPresenter providePresenter() {
        presenter = new GithubFieldPresenter();
        App.getAppComponent().inject(presenter);
        return presenter;
    }

//    @NonNull
//    @ProvidePresenter
//    public GithubConstructorPresenter providePresenter() {
//        return presenter;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        presenter.onClick();
    }

    @Override
    public void showName(@NonNull String name) {
        tvName.setText(name);
    }
}
