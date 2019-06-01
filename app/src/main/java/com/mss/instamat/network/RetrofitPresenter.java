package com.mss.instamat.network;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RetrofitPresenter extends MvpPresenter<RetrofitView> {

    private final GithubRepository githubRepository = new GithubRepository();

    public void onGetAvatarClick() {
        githubRepository.loadAvatarData("JakeWharton")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(a -> getViewState().showAvatar(a.avatarUrl));
    }
}
