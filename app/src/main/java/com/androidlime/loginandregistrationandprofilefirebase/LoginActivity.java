package com.androidlime.loginandregistrationandprofilefirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignin;
    private TextView forgetpassword;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog=new ProgressDialog(this);
        forgetpassword=(TextView)findViewById(R.id.forgetpassword_xml);
        buttonSignin=(Button)findViewById(R.id.buttonlogin);
        editTextEmail=(EditText)findViewById(R.id.edittextEmail);
        editTextPassword=(EditText)findViewById(R.id.edittextPassword);
        textViewSignup=(TextView) findViewById(R.id.signupbtn);
        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
    }


    private void userLogin(){

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(getApplicationContext(),"Please Insert Email..",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please Insert Password..",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Login Successfully..");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            //we will start the profile
                            finish();
                            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                        }

                        else {
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


    @Override
    public void onClick(View view) {

        if (view==buttonSignin){
            userLogin();
        }

        if (view==textViewSignup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        if (view==forgetpassword){
            finish();
            startActivity(new Intent(getApplicationContext(),ForgetPaswordActivity.class));
        }

    }



}