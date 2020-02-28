package com.blaze.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    TextView textViewLogin;
    EditText editTextRegisterEmail, editTextRegisterPass;
    Button buttonSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPass = findViewById(R.id.editTextRegisterPass);
        textViewLogin = findViewById(R.id.textViewLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        mAuth = FirebaseAuth.getInstance();
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    final String email = editTextRegisterEmail.getText().toString().trim();
                    String pass = editTextRegisterPass.getText().toString();

                    Log.d("Email: ", email);
                    Log.d("Password: ", pass);

                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isComplete()) {
                                Toast.makeText(Registration.this, "Email: " + email, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Registration.this, HomeScreen.class));
                                finish();
                            } else {
                                Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });

                }
            }
        });


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validate() {
        boolean r = false;
        String email = editTextRegisterEmail.getText().toString();
        String pass = editTextRegisterPass.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(Registration.this, "Enter all the details", Toast.LENGTH_LONG).show();
        } else {
            r = true;
        }
        return r;
    }
}
