package com.mss.instamat.tests.mockito.presenter;

import com.mss.instamat.tests.mockito.di.DaggerTestComponent;
import com.mss.instamat.tests.mockito.di.TestComponent;
import com.mss.instamat.tests.mockito.di.TestModule;
import com.mss.instamat.tests.mockito.model.GithubApi;
import com.mss.instamat.tests.mockito.model.User;
import com.mss.instamat.tests.mockito.view.GithubView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

public class GithubFieldPresenterTest {

    @Mock
    GithubView githubView;
    private GithubFieldPresenter presenter;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new GithubFieldPresenter());
    }

    @Test
    public void onClick() {
        TestComponent component = DaggerTestComponent.builder()
                .testModule(new TestModule() {

                    @Override
                    public GithubApi provideGithubApi() {
                        GithubApi retrofitApi = super.provideGithubApi();
                        User user = new User();
                        user.login = "Jack";
                        Mockito.when(retrofitApi.getUser(Mockito.anyString())).thenReturn(Single.just(user));
                        return retrofitApi;
                    }
                }).build();

        component.inject(presenter);
        presenter.attachView(githubView);
        presenter.onClick();
        
        Mockito.verify(githubView).showName("Jack");
    }
}