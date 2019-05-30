package com.mss.instamat.view.detail;

import android.os.Bundle;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.R;
import com.mss.instamat.presenter.DetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    public static String POSITION_TAG = "POSITION_TAG";
    @InjectPresenter
    DetailPresenter presenter;
    @BindView(R.id.imageView)
    ImageView imageView;
    private int position = 0;

    @ProvidePresenter
    DetailPresenter providePresenter() {
        return new DetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt(POSITION_TAG, 0);
        }
        presenter.onCreate(position);
    }

    @Override
    public void showImage(int idResource) {
        imageView.setImageResource(idResource);
    }
}
