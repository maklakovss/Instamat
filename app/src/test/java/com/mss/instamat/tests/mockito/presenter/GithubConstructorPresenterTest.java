package com.mss.instamat.tests.mockito.presenter;

import com.mss.instamat.tests.mockito.model.GithubApi;
import com.mss.instamat.tests.mockito.model.User;
import com.mss.instamat.tests.mockito.view.GithubView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class GithubConstructorPresenterTest {

    @Mock
    GithubView githubView;

    @Mock
    GithubApi githubApi;

    private GithubConstructorPresenter presenter;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new GithubConstructorPresenter(githubApi));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onClick_isCorrect() {
        User user = new User();
        user.login = "Jack";
        Mockito.when(githubApi.getUser(Mockito.anyString())).thenReturn(Single.just(user));

        presenter.attachView(githubView);
        presenter.onClick();

        Mockito.verify(githubView).showName("Jack");
    }
}