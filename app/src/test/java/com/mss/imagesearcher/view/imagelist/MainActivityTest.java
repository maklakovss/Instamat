package com.mss.imagesearcher.view.imagelist;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.mss.imagesearcher.R;
import com.mss.imagesearcher.di.RobolectricApp;
import com.mss.imagesearcher.di.RobolectricComponent;
import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter;
import com.mss.imagesearcher.robolectric.ShadowSnackbar;
import com.mss.imagesearcher.view.detail.DetailActivity;
import com.mss.imagesearcher.view.helpers.ImageLoader;
import com.mss.imagesearcher.view.main.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class, shadows = {ShadowSnackbar.class})
public class MainActivityTest {

    private MainActivity mainActivity;
    private List<ImageInfo> imageInfoList;

    @Inject
    ImageListPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Before
    public void setUp() {
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        ((RobolectricComponent) RobolectricApp.getAppComponent()).inject(this);
    }

    @Test
    public void showProgress_Hide_pbDetailHide() {
        ProgressBar progressBar = mainActivity.findViewById(R.id.pbList);

        mainActivity.showProgress(false);

        assertEquals(View.INVISIBLE, progressBar.getVisibility());
    }

    @Test
    public void showProgress_Show_pbDetailShow() {
        ProgressBar progressBar = mainActivity.findViewById(R.id.pbList);

        mainActivity.showProgress(true);

        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void stopRefreshing_pbDetailShow() {
        SwipeRefreshLayout swipeRefreshLayout = mainActivity.findViewById(R.id.srlImages);
        swipeRefreshLayout.setRefreshing(true);

        mainActivity.stopRefreshing();

        assertFalse(swipeRefreshLayout.isRefreshing());
    }

    @Test
    public void refreshImageList_callPresenterGetRvPresenter() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());

        mainActivity.refreshImageList();
        mainActivity.refreshImageList();

        verify(presenter).getRvPresenter();
    }

    @Test
    public void showNotFoundMessage_showSnackbar() {
        ShadowSnackbar.reset();

        mainActivity.showNotFoundMessage();

        assertEquals(ShadowSnackbar.getTextOfLatestSnackbar(), mainActivity.getString(R.string.not_found_message));
    }

    @Test
    public void openDetailActivity_startDetailActivityWithParam() {
        mainActivity.openDetailActivity(1);

        Intent intent = Shadows.shadowOf(mainActivity).getNextStartedActivity();
        assertEquals("com.mss.imagesearcher.view.detail.DetailActivity", intent.getComponent().getClassName());
        assertEquals(1, intent.getIntExtra(DetailActivity.PARAMETER_POSITION_TAG, 0));
    }

    @Test
    public void onRequestPermissionsResult_NoPermission_FinishActivity() {
        mainActivity.onRequestPermissionsResult(MainActivity.PERMISSION_REQUEST_CODE, new String[2], new int[2]);
        assertTrue(Shadows.shadowOf(mainActivity).isFinishing());
    }

    @Test
    public void onRequestPermissionsResult_HasPermission_NoFinishActivity() {
        ShadowApplication application = new ShadowApplication();
        for (String p : MainActivity.Companion.getNETWORK_PERMISSIONS()) {
            application.grantPermissions(p);
        }

        mainActivity.onRequestPermissionsResult(MainActivity.PERMISSION_REQUEST_CODE, new String[2], new int[2]);

        assertTrue(!Shadows.shadowOf(mainActivity).isFinishing());
    }

    @Test
    public void onItemClick_callPresenter() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = mainActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 800);
        mainActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        viewHolder.getIvItem().performClick();

        verify(presenter).onItemClick(1);
    }

    @Test
    public void holderSetImage_SuccessLoad_callPresenterOnImageLoaded() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = mainActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        mainActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);
        doAnswer(answer -> {
            ((Runnable) answer.getArgument(3)).run();
            return null;
        }).when(imageLoader).load(any(), any(), any(), any(), any());

        viewHolder.setImage("path");

        assertEquals(1, viewHolder.getPos());
        verify(rvPresenter).onImageLoaded(any());
    }

    @Test
    public void holderSetImage_FailLoad_callPresenterOnImageLoaded() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = mainActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        mainActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);
        doAnswer(answer -> {
            ((Runnable) answer.getArgument(4)).run();
            return null;
        }).when(imageLoader).load(any(), any(), any(), any(), any());

        viewHolder.setImage("path");

        verify(rvPresenter).onImageLoadFailed(any());
    }

    @Test
    public void holderShowProgress_True_ProgressBarVisible() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = mainActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        mainActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        viewHolder.showProgress(true);

        assertEquals(View.VISIBLE, viewHolder.getPbItem().getVisibility());
    }

    @Test
    public void holderShowProgress_False_ProgressBarVisible() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = mainActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        mainActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        viewHolder.showProgress(false);

        assertEquals(View.GONE, viewHolder.getPbItem().getVisibility());
    }

    @Test
    public void onAction_callPresenterOnSearchClick() {
        TextInputEditText etSearch = mainActivity.findViewById(R.id.etSearch);
        etSearch.setText("one");
        etSearch.onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

        verify(presenter).onSearchClick("one");
    }

    private void initImageInfoList() {
        imageInfoList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setPreviewURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            imageInfoList.add(imageInfo);
        }
    }
}