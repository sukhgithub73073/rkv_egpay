package com.app.rkvmoneyrecharge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.CommonDB;
import com.app.rkvmoneyrecharge.utils.Utility;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new CommonDB(getApplicationContext()).getString("USER_LOGIN_RESPONSE_V1").equalsIgnoreCase("")) {
                    startActivity(new Intent(getApplicationContext()  , LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)) ;

                }else{
                    LoginModel loginModel = Utility.getLoginUser(getApplicationContext()) ;
                    AppData.token = loginModel.getDynamic().getToken() ;
                    startActivity(new Intent(getApplicationContext()  , MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)) ;
                }
            }
        }, 3000) ;

    }
}