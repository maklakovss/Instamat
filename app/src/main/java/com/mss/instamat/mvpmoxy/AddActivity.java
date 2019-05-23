package com.mss.instamat.mvpmoxy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.R;

public class AddActivity extends MvpAppCompatActivity implements AddView {

    @InjectPresenter
    AddPresenter presenter;
    private EditText etInput;
    private Button btnAdd;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = findViewById(R.id.etInput);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonClick(etInput.getText().toString());
            }
        });
        tvResult = findViewById(R.id.tvResult);
    }

    @ProvidePresenter
    public AddPresenter providePresenter() {
        return new AddPresenter();
    }

    @Override
    public void setResultString(@NonNull String currentString) {
        tvResult.setText(currentString);
    }
}
