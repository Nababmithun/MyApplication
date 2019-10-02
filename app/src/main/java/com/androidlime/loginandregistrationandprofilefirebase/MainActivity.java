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

public class MainActivity extends AppCompatActivity   implements View.OnClickListener{

    private Button btnregister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewsignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){

            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }

        progressDialog=new ProgressDialog(this);
        btnregister=(Button)findViewById(R.id.buttonregister);
        editTextEmail=(EditText)findViewById(R.id.edittextEmail);
        editTextPassword=(EditText)findViewById(R.id.edittextPassword);
        textViewsignup=(TextView)findViewById(R.id.signinbtn);
        btnregister.setOnClickListener(this);
        textViewsignup.setOnClickListener(this);
    }

    private void registerUser(){

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(getApplicationContext(),"Enter Your Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            //password is empty.
            Toast.makeText(getApplicationContext(),"Enter Your Password",Toast.LENGTH_SHORT).show();
            return;
        }
        //if validation ok,we will show progressbar.
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            //user is successfully registered and logd in
                            //we start create user profile activity
                            finish();
                            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                            Toast.makeText(getApplicationContext(),"Register is successfuly",Toast.LENGTH_SHORT).show();

                        }

                        else {

                            Toast.makeText(getApplicationContext(),"Register failed,Try again",Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if (view==btnregister){

            registerUser();

        }

        if (view==textViewsignup){
            //we will add log in
            startActivity(new Intent(this,LoginActivity.class));
        }
    }


}