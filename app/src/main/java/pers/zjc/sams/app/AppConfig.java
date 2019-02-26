package pers.zjc.sams.app;

import android.content.SharedPreferences;

@SuppressWarnings("unused")
public class AppConfig {

    private boolean isLogin;
    private String userId;
    private String account;
    private String userName;
    private String passWord;
    private boolean isRemember;
    private String token;
    private SharedPreferences preferences;
    private String role;
    private String tel;
    private String major;

    public AppConfig(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void load() {
        userId = preferences.getString("userId", "");
        isLogin = "1".equals(preferences.getString("isLogin", ""));
        userName = preferences.getString("username", "");
        passWord = preferences.getString("password", "");
        isRemember = "1".equals(preferences.getString("remember", "0"));
        account = preferences.getString("account", "");
        token = preferences.getString("token", "");
        tel = preferences.getString("tel", "");
        role = preferences.getString("role", "");
        major = preferences.getString("major", "");
    }


    public void save() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", userId);
        editor.putString("isLogin", isLogin ? "1" : "0");
        editor.putString("username", userName);
        editor.putString("password", passWord);
        editor.putString("remember", isRemember ? "1" : "0");
        editor.putString("account", account);
        editor.putString("token", token);
        editor.putString("tel", tel);
        editor.putString("role", role);
        editor.putString("major", major);
        editor.apply();
    }

    public void saveSysParams() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.apply();
    }


    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
