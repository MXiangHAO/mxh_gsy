package com.example.chapter_blog.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class validation {
    // 验证邮箱或手机号的规则
    public static boolean isValidEmailOrPhone(String input) {
        return isValidEmail(input) || isValidMobile(input);
    }

    // 验证邮箱的规则
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // 验证手机号的规则
    public static boolean isValidMobile(String mobile) {
        // 验证手机号的正则表达式
        String regMobile = "^(0|86|17951)?(1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regMobile);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    // 显示对话框的方法
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
}
