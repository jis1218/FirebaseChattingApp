package com.example.firebasechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasechat.model.User;
import com.example.firebasechat.util.DialogUtil;
import com.example.firebasechat.util.VeriUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    Button btnSignUp;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    EditText editEmail, editPW, editPWRepeat, editName, editID, editPhoneNumber;
    boolean checkEmail, checkPassword, checkRepeat, checkName;
    String email, pw, pwRepeat, name, phoneNumber, m_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_actity);
        initField();
        initView();
        setListener();

    }

    private void initField() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        checkEmail = false;
        checkName = false;
        checkPassword = false;
        checkRepeat = false;
    }

    private void initView() {
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPW = (EditText) findViewById(R.id.editPW);
        editPWRepeat = (EditText) findViewById(R.id.editPWRepeat);
        editName = (EditText) findViewById(R.id.editName);
        editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        editID = (EditText) findViewById(R.id.editID);
    }

    private void setListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editEmail.getText().toString();
                pw = editPW.getText().toString();
                pwRepeat = editPWRepeat.getText().toString();
                name = editName.getText().toString();
                phoneNumber = editPhoneNumber.getText().toString();
                m_ID = editID.getText().toString();

                createUser(email, pw);
            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (VeriUtil.isValidEmail(charSequence.toString())) {
                    checkEmail = true;
                } else {
                    checkEmail = false;
                }
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (VeriUtil.isValidPassword(charSequence.toString())) {
                    checkPassword = true;
                } else {
                    checkPassword = false;
                }
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (VeriUtil.isValidName(charSequence.toString())) {
                    checkName = true;
                } else {
                    checkName = false;
                }
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editPWRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkRepeat = editPW.getText().toString().equals(charSequence);
                //enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void enableSignupButton() {
        if (checkName && checkPassword && checkEmail) {
            btnSignUp.setEnabled(true);
        } else {
            btnSignUp.setEnabled(false);
        }
    }

    private void createUser(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
//                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();
                            // 정상등록시 안내 메일 발송
                            FirebaseUser m_user = mAuth.getCurrentUser();
                            m_user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUpActivity.this, "이메일이 발송되었습니다", Toast.LENGTH_SHORT).show();
                                    DialogUtil.showdialog("이메일이 발송되었습니다", SignUpActivity.this, false);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, "이메일이 발송되지 않았습니다다", Toast.LENGTH_SHORT).show();
                                }
                            });

                            User user = new User(m_user.getUid(), name, email, phoneNumber, m_ID);
                            userRef.child(m_user.getUid()).setValue(user);


                        } else {
                            Log.e("Auth", "creation is not success");
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
