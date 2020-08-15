package com.example.logintest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtUserName, edtPass;
    Button btnLogin;
    CheckBox cbRemember;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;
    String userLogin = "123duy99@gmail.com";
    String passLogin = "123456a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //khởi tạo shared preferences 
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        initWidgets();//khởi tạo các control 
        loadData();//lấy dữ liệu đã lưu nếu có 
        //thiết đặt button đăng nhập 
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nếu người dùng có chọn ghi nhớ 
                if (cbRemember.isChecked())
                    //lưu lại thông tin đăng nhập 
                    saveData(edtUserName.getText().toString(), edtPass.getText().toString());
                else
                    clearData();//xóa thông tin đã lưu 
                //nếu thông tin đăng nhập đúng thì đến màng hình home 
                if (edtUserName.getText().toString().equals(userLogin) && edtPass.getText().toString().equals(passLogin)) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    if (isValidEmail()==false) {
                        Toast.makeText(MainActivity.this, "Email không hợp lệ.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if (isValidPassword()==false){
                            Toast.makeText(MainActivity.this, "Password có ít hơn 6 kí tự.", Toast.LENGTH_SHORT).show();
                        } else
                    Toast.makeText(MainActivity.this, "Thông tin đăng nhập không đúng", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isValidEmail()
    {
        if(this.edtUserName != null && !TextUtils.isEmpty(edtUserName.getText()) && Patterns.EMAIL_ADDRESS.matcher(edtUserName.getText()).matches())
        {
            return true;
        }

        return false;
    }

    public boolean isValidPassword()
    {
        if(this.edtPass != null && this.edtPass.length() >= 6)
        {
            return true;
        }

        return false;
    }

    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void saveData(String username, String Pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, Pass);
        editor.putBoolean(REMEMBER,cbRemember.isChecked());
        editor.commit();
    }

    private void loadData() {
        if(sharedpreferences.getBoolean(REMEMBER,false)) {
            edtUserName.setText(sharedpreferences.getString(USERNAME, ""));
            edtPass.setText(sharedpreferences.getString(PASS, ""));
            cbRemember.setChecked(true);
        }
        else
            cbRemember.setChecked(false);

    }

    private void initWidgets() {
        edtUserName = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }



} 