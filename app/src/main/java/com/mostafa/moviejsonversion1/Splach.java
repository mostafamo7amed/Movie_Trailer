package com.mostafa.moviejsonversion1;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.mostafa.moviejsonversion1.Activity.RegisterLogin;

public class Splach extends AppCompatActivity {
    TextView textView;
    long animationTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        textView=findViewById(R.id.app_name);
        ObjectAnimator animatorX=ObjectAnimator.ofFloat(textView,"x",200f);
        animatorX.setDuration(animationTime);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splach.this,RegisterLogin.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}
