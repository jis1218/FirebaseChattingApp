package com.example.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firebasechat.util.DialogUtil;
import com.example.firebasechat.util.PreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editEmail, editPassword;
    TextView tvSignUp;
    Button btnSignIn;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if(PreferenceUtil.getStringValue(this, "auto_sign").equals("true")){
            String email = PreferenceUtil.getStringValue(this, "email");
            String password = PreferenceUtil.getStringValue(this, "password");
            signInUser(email, password);
        }

        setContentView(R.layout.activity_main);
        initView();
        initField();
        setListener();
    }

    private void initField() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("");
    }


    private void signInUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            //Log.w(TAG, "signInWithEmail:failed", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            if(fUser.isEmailVerified()){
                                PreferenceUtil.setValue(getBaseContext(), "user_id", fUser.getUid());
                                PreferenceUtil.setValue(getBaseContext(), "email", email);
                                PreferenceUtil.setValue(getBaseContext(), "password", password);
                                PreferenceUtil.setValue(getBaseContext(), "auto_sign", "true");
                                //로그인 진행
                                Intent intent = new Intent(MainActivity.this, RoomListActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                DialogUtil.showdialog("이메일 인증이 필요합니다", MainActivity.this, false);
                            }
                        } else {
                            DialogUtil.showdialog("로그인 실패하였습니다", MainActivity.this, false);
                        }

                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DialogUtil.showdialog("오류 발생" + e.getMessage(), MainActivity.this, false);
                    }
                });

    }

    private void initView() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignin);
    }

    private void setListener() {

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                signInUser(email, password);

            }
        });
    }
}
