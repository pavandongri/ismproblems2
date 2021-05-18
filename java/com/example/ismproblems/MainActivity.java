package com.example.ismproblems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null)
//        {
//            Toast.makeText(this, "already registered", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, welcomeActivity.class));
//            finish();
//        }
//    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try
                {
                    sleep(2000);
                }
                catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, "error in flash "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finally
                {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if(currentUser!=null)
                    {
//                        Toast.makeText(MainActivity.this, "already registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, welcomeActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(MainActivity.this, loginActivity.class));
                        finish();
                    }
                }
            }
        };
        thread.start();
    }


}