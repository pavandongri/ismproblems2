package com.example.ismproblems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registrationActivity extends AppCompatActivity {
    EditText email, pword,cpword, name, adnumber;
    Button register;
    ProgressDialog pd;
    FirebaseAuth  mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText)findViewById(R.id.name);
        adnumber = (EditText)findViewById(R.id.adnumber);
        email = (EditText)findViewById(R.id.rEmail);
        pword = (EditText)findViewById(R.id.rPWord);
        cpword = (EditText)findViewById(R.id.rCPWord);
        register = (Button)findViewById(R.id.register);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name, admissionNumber, mail, password, cpassword;
                Name = name.getText().toString().trim();
                admissionNumber = adnumber.getText().toString().trim();
                mail = email.getText().toString().trim();
                password= pword.getText().toString().trim()   ;
                cpassword = cpword.getText().toString().trim()  ;


                if(name.length()==0)
                    name.setError("enter your name");
                else if(admissionNumber.length()!=8)
                  adnumber.setError("Enter correct Admission number");
                else if(! mail.toLowerCase().contains(".iitism.ac.in") || !(mail.toLowerCase().contains(adnumber.getText().toString())))
                     email.setError("enter IIT ISM domain emial");
                else  if (password.length()<=7)
                    pword.setError("password minimum length is 8");
                else if(!(cpassword.equals(password))) {
                    pword.setError("password didn't match");
                    cpword.setError("password didn't match");
                }
                else
                    registration(mail, password);


            }
        });

    }

    public void registration (String email, String password)
    {
        Toast.makeText(this, "entered registration method", Toast.LENGTH_SHORT).show();
        pd.setTitle("REGISTRATION");
        pd.setMessage("Please Wait...");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pd.dismiss();
                        Toast.makeText(registrationActivity.this, "Registration successful...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registrationActivity.this, welcomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(registrationActivity.this, "error in registration -> " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}