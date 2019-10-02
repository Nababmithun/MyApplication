package com.androidlime.loginandregistrationandprofilefirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button btnlogout;
    private TextView textViewemailname;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextPhone;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }

        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextName=(EditText)findViewById(R.id.edittextname);
        editTextPhone=(EditText)findViewById(R.id.edittextphone);
        btnsave=(Button)findViewById(R.id.addinform_btn);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        btnlogout=(Button)findViewById(R.id.btnlogout);
        textViewemailname=(TextView)findViewById(R.id.profilename);
        textViewemailname.setText("Welcome"+  user.getEmail());
        btnlogout.setOnClickListener(this);
        btnsave.setOnClickListener(this);

    }

    private void userInformation(){

        String name=editTextName.getText().toString().trim();
        String phone=editTextPhone.getText().toString().trim();
        Userinformation userinformation=new Userinformation(name,phone);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinformation);
        Toast.makeText(getApplicationContext(),"Information Saved",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        if (view==btnlogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }

        if (view==btnsave){
            userInformation();
            finish();
            startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
        }
    }
}