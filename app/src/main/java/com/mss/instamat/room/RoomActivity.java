package com.mss.instamat.room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomActivity extends MvpAppCompatActivity implements RoomView {

    @BindView(R.id.tvUsers)
    TextView tvUsers;

    @InjectPresenter
    RoomPresenter presenter;

    @ProvidePresenter
    RoomPresenter providePresenter() {
        return new RoomPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddUser)
    public void onAddUserClick(View view) {
        presenter.onAddUserClick();
    }

    @OnClick(R.id.btnAddUsers)
    public void onAddUsersClick(View view) {
        presenter.onAddUsersClick();
    }

    @OnClick(R.id.btnDeleteUser)
    public void onDeleteUserClick(View view) {
        presenter.onDeleteUserClick();
    }

    @OnClick(R.id.btnUpdateUser)
    public void onUpdateUserClick(View view) {
        presenter.onUpdateUserClick();
    }

    @Override
    public void showUsers(String users) {
        tvUsers.setText(users);
    }
}
