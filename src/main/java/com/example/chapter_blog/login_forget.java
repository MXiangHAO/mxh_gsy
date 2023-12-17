package com.example.chapter_blog;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter_blog.util.Client;
import com.example.chapter_blog.util.validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class login_forget extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText verificationCodeEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;
    private Button getVerificationCodeButton;
    private Button resetPasswordButton;
    private ImageView backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);

        // 初始化视图
        usernameEditText = findViewById(R.id.username);
        verificationCodeEditText = findViewById(R.id.verificationCode);
        newPasswordEditText = findViewById(R.id.newPassword);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPassword);
        getVerificationCodeButton = findViewById(R.id.getVerificationCode);
        resetPasswordButton = findViewById(R.id.confirm);
        backButton = findViewById(R.id.back);

        // 设置点击事件
        getVerificationCodeButton.setOnClickListener(this);
        resetPasswordButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.getVerificationCode) {
            // TODO: 实现获取验证码的逻辑
            // 可以通过后端接口获取验证码
            // 示例：Toast 提示验证码已发送
            Toast.makeText(login_forget.this, "验证码已发送", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.confirm) {
            // 获取输入的用户名、验证码和新密码
            String username = usernameEditText.getText().toString().trim();
            String verificationCode = verificationCodeEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmNewPassword = confirmNewPasswordEditText.getText().toString().trim();

            // 检查用户名、验证码和密码是否为空
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(verificationCode)
                    || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword)) {
                validation.showAlertDialog(this,"提示", "用户名、验证码和密码不能为空");
                return;
            }

            // 检查手机号和邮箱格式是否正确
            if (!validation.isValidEmailOrPhone(username)) {
                validation.showAlertDialog(this,"提示", "请输入正确的手机号或邮箱");
                return;
            }

            // 检查新密码和确认密码是否一致
            if (!TextUtils.equals(newPassword, confirmNewPassword)) {
                validation.showAlertDialog(this,"提示", "新密码和确认密码不一致");
                return;
            }

            // TODO: 实现重置密码的逻辑
            // 可以通过后端接口实现密码重置
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", username);
                jsonBody.put("password", newPassword);
                jsonBody.put("type", validation.isValidEmail(username) ? "email" : "phone");

                // TODO: 将 jsonBody 作为请求的一部分发送到后端
                // 使用 Retrofit、Volley、OkHttp 等网络请求库
                boolean result = Client.post("/init/login", String.valueOf(jsonBody)).get();
                if(result){
                    // 示例：Toast 提示登录成功
                    Toast.makeText(login_forget.this, "修改成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(login_forget.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            // 重置密码成功后，可以跳转到其他页面
            // 示例：跳转到登录页面
            Intent intent = new Intent(login_forget.this, login.class);
            startActivity(intent);
            finish(); // 关闭当前找回密码页面
        } else if (id == R.id.back) {
            // 返回按钮的点击事件，可以根据需求进行处理
            finish(); // 关闭当前找回密码页面
        }
    }

    // 验证手机号或邮箱格式是否正确

}
