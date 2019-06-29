package com.mss.instamat.view.imagelist;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Recycler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.mss.instamat.R;
import com.mss.instamat.di.RobolectricApp;
import com.mss.instamat.di.RobolectricComponent;
import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.robolectric.ShadowSnackbar;
import com.mss.instamat.view.detail.DetailActivity;
import com.mss.instamat.view.helpers.ImageLoader;

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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class, shadows = {ShadowSnackbar.class})
public class ImageListActivityTest {

    private ImageListActivity imageListActivity;
    private List<ImageInfo> imageInfoList;

    @Inject
    ImageListPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Before
    public void setUp() {
        imageListActivity = Robolectric.buildActivity(ImageListActivity.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        ((RobolectricComponent) RobolectricApp.getAppComponent()).inject(this);
    }

    @Test
    public void showProgress_Hide_pbDetailHide() {
        ProgressBar progressBar = imageListActivity.findViewById(R.id.pbList);

        imageListActivity.showProgress(false);

        assertEquals(View.INVISIBLE, progressBar.getVisibility());
    }

    @Test
    public void showProgress_Show_pbDetailShow() {
        ProgressBar progressBar = imageListActivity.findViewById(R.id.pbList);

        imageListActivity.showProgress(true);

        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void refreshImageList_callPresenterGetRvPresenter() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());

        imageListActivity.refreshImageList();
        imageListActivity.refreshImageList();

        verify(presenter).getRvPresenter();
    }

    @Test
    public void showNotFoundMessage_showSnackbar() {
        ShadowSnackbar.reset();

        imageListActivity.showNotFoundMessage();

        assertEquals(ShadowSnackbar.getTextOfLatestSnackbar(), imageListActivity.getString(R.string.not_found_message));
    }

    @Test
    public void openDetailActivity_startDetailActivityWithParam() {
        imageListActivity.openDetailActivity(1);

        Intent intent = Shadows.shadowOf(imageListActivity).getNextStartedActivity();
        assertEquals("com.mss.instamat.view.detail.DetailActivity", intent.getComponent().getClassName());
        assertEquals(1, intent.getIntExtra(DetailActivity.PARAMETER_POSITION_TAG, 0));
    }

    @Test
    public void onRequestPermissionsResult_NoPermission_FinishActivity() {
        imageListActivity.onRequestPermissionsResult(ImageListActivity.PERMISSION_REQUEST_CODE, new String[2], new int[2]);
        assertTrue(Shadows.shadowOf(imageListActivity).isFinishing());
    }

    @Test
    public void onRequestPermissionsResult_HasPermission_NoFinishActivity() {
        ShadowApplication application = new ShadowApplication();
        for (String p : ImageListActivity.NETWORK_PERMISSIONS) {
            application.grantPermissions(p);
        }

        imageListActivity.onRequestPermissionsResult(ImageListActivity.PERMISSION_REQUEST_CODE, new String[2], new int[2]);

        assertTrue(!Shadows.shadowOf(imageListActivity).isFinishing());
    }

    @Test
    public void onItemClick_callPresenter() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = imageListActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 800);
        imageListActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        viewHolder.ivItem.performClick();

        verify(presenter).onItemClick(1);
    }

    @Test
    public void holderSetImage_SuccessLoad_callPresenterOnImageLoaded() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = imageListActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        imageListActivity.refreshImageList();
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
        RecyclerView recyclerView = imageListActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        imageListActivity.refreshImageList();
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
        RecyclerView recyclerView = imageListActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        imageListActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        viewHolder.showProgress(true);

        assertEquals(View.VISIBLE, viewHolder.pbItem.getVisibility());
    }

    @Test
    public void holderShowProgress_False_ProgressBarVisible() {
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());
        RecyclerView recyclerView = imageListActivity.findViewById(R.id.rvImages);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 200);
        imageListActivity.refreshImageList();
        ImageListAdapter.ViewHolder viewHolder = (ImageListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        viewHolder.showProgress(false);

        assertEquals(View.GONE, viewHolder.pbItem.getVisibility());
    }

    @Test
    public void onAction_callPresenterOnSearchClick() {
        TextInputEditText etSearch = imageListActivity.findViewById(R.id.etSearch);
        etSearch.setText("one");
        etSearch.onEditorAction(EditorInfo.IME_ACTION_SEARCH);

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