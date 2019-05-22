package com.mss.instamat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity implements AddView {

    private EditText etInput;
    private Button btnAdd;
    private TextView tvResult;

    private AddPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new AddPresenter(this);
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

    @Override
    public void setResultString(@NonNull String currentString) {
        tvResult.setText(currentString);
    }
}
