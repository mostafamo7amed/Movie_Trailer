package com.mostafa.moviejsonversion1.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mostafa.moviejsonversion1.Login;
import com.mostafa.moviejsonversion1.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RegisterLogin extends AppCompatActivity {
    EditText userEt,passEt,confirmEt;
    Button btuSign;
    CheckBox registerCheck;
    ProgressBar registerProgress;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        userEt =(EditText)findViewById(R.id.username_register);
        passEt =(EditText)findViewById(R.id.pass_register);
        confirmEt= (EditText)findViewById(R.id.pass_register_confirm);
        btuSign=(Button)findViewById(R.id.btu_register);
        registerCheck =(CheckBox)findViewById(R.id.show_pass_register);
        registerProgress =(ProgressBar)findViewById(R.id.progress_register);
        firebaseAuth= FirebaseAuth.getInstance();


        registerCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    passEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else
                {
                    passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
        btuSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= userEt.getText().toString();
                String pass= passEt.getText().toString();
                String confirm_pass=confirmEt.getText().toString();
                if( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass))
                {
                    if(pass.equals(confirm_pass))
                    {
                        registerProgress.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    GoToMain();
                                    registerProgress.setVisibility(View.INVISIBLE);
                                }else{
                                    registerProgress.setVisibility(View.INVISIBLE);
                                    String error= Objects.requireNonNull(task.getException()).getMessage();
                                    Toast.makeText(RegisterLogin.this, "error :"+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                    else
                    {
                        registerProgress.setVisibility(View.INVISIBLE);
                        confirmEt.setBackgroundResource(R.drawable.costum_input_error);
                    }
                }
                else
                {
                    userEt.setBackgroundResource(R.drawable.costum_input_error);
                    passEt.setBackgroundResource(R.drawable.costum_input_error);
                    confirmEt.setBackgroundResource(R.drawable.costum_input_error);
                }
            }
        });
    }


    public void fromRegisterTOLogin(View view) {
        Intent intent = new Intent(RegisterLogin.this, Login.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null)
        {
            GoToMain();
        }
    }
    private void GoToMain() {
        Intent intent=new Intent(RegisterLogin.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}