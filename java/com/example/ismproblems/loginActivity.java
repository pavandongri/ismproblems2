package com.example.ismproblems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class loginActivity extends AppCompatActivity {
    EditText email, pword;
    Button login;
    TextView forgotPword, newAccount;
    FirebaseAuth mAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        pword = (EditText)findViewById(R.id.pword);
        login = (Button)findViewById(R.id.login);
        forgotPword = (TextView)findViewById(R.id.forgotPword);
        newAccount = (TextView)findViewById(R.id.newAccount);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, registrationActivity.class));
            }
        });

        forgotPword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {   String mail = email.getText().toString();
                if(mail==null || mail.length()==0)
                email.setError("enter mail");
                else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(loginActivity.this, "password reset mail has been sent to your " + mail, Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(loginActivity.this, "error in sending password reset mail -> " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail, password;
                mail = email.getText().toString().trim();
                password = pword.getText().toString().trim();
                if(mail.length()==0)
                    Toast.makeText(loginActivity.this, "Enter Mail", Toast.LENGTH_SHORT).show();
                else if(password.length()<=7)
                    Toast.makeText(loginActivity.this, "password length didn't match", Toast.LENGTH_SHORT).show();
                else
                userLogin(mail, password);
            }
        });

    }

    private void userLogin(String email, String password)
    {  pd.setTitle("USER LOGIN");
       pd.setMessage("Please wait...");
       pd.show();
       mAuth.signInWithEmailAndPassword(email, password)
               .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       pd.dismiss();
                       Toast.makeText(loginActivity.this, "User Login Successful", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(loginActivity.this, welcomeActivity.class));
                       finish();
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               pd.dismiss();
               Toast.makeText(loginActivity.this, "error in login -> " + e.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });

    }

}