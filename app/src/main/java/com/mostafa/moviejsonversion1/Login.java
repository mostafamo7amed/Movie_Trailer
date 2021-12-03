package com.mostafa.moviejsonversion1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mostafa.moviejsonversion1.Activity.MainActivity;
import com.mostafa.moviejsonversion1.Activity.RegisterLogin;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText emailEt,passEt;
    Button btuLogin;
    CheckBox loginCheck;
    ProgressBar loginProgress;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEt =(EditText)findViewById(R.id.user_login);
        passEt =(EditText)findViewById(R.id.pass_login);
        btuLogin =(Button)findViewById(R.id.btu_login);
        loginCheck =(CheckBox)findViewById(R.id.show_pass);
        loginProgress =(ProgressBar)findViewById(R.id.progress_login);
        firebaseAuth= FirebaseAuth.getInstance();
        loginCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    passEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else
                {
                    passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        btuLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= emailEt.getText().toString();
                String pass= passEt.getText().toString();
                loginProgress.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
                {
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                GoToMain();
                                loginProgress.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                loginProgress.setVisibility(View.INVISIBLE);
                                String error= Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(Login.this, "error :"+error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    loginProgress.setVisibility(View.INVISIBLE);
                    emailEt.setBackgroundResource(R.drawable.costum_input_error);
                    passEt.setBackgroundResource(R.drawable.costum_input_error);
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            GoToMain();
        }
    }
    private void GoToMain() {
        Intent intent=new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}