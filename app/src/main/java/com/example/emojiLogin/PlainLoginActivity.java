package com.example.emojiLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class PlainLoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +             // at least 1 digit
                    "(?=.*[a-z])" +              // at least 1 lower case letter
                    "(?=.*[A-Z])" +              // at least 1 upper case letter
                    "(?=.*[!@#$%^&+=])" +        // at least 1 special character
                    ".{8,}$";                    // at least 8 characters

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        return pattern.matcher(password).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final EditText nameInput = findViewById(R.id.nameInput);
        final EditText passwordInput = findViewById(R.id.passwordInput);

        Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (name.isEmpty() || name.length() < 3) {
                    Toast.makeText(view.getContext(), "Username is not valid. It must be longer than 3 letters.", Toast.LENGTH_SHORT).show();
                } else if (!isValid(password)) {
                    Toast.makeText(view.getContext(), "Your password is too weak. Please make sure it meets the criteria.", Toast.LENGTH_LONG).show();
                } else {
                    String userId = mDatabase.child("users").push().getKey(); //unique key for each new user
                    mDatabase.child("users").child(userId).child("name").setValue(name);
                    Log.d("Emoji Login Activity", "Attempting to write to database.");
                    mDatabase.child("users").child(userId).child("name").setValue(name)
                            .addOnSuccessListener(aVoid ->Toast.makeText(view.getContext(), "Signed in successfully", Toast.LENGTH_LONG).show())
                            .addOnFailureListener(e ->Toast.makeText(view.getContext(), "Signed in failed", Toast.LENGTH_LONG).show() );

                    mDatabase.child("users").child(userId).child("password").setValue(password)
                            .addOnSuccessListener(aVoid ->Toast.makeText(view.getContext(), "Signed in successfully", Toast.LENGTH_LONG).show())
                            .addOnFailureListener(e ->Toast.makeText(view.getContext(), "Signed in failed", Toast.LENGTH_LONG).show() );
                    Intent intent=new Intent(PlainLoginActivity.this,MainActivity2.class);
                    startActivity(intent);
                }
            }
        });
        Button switchLoginMethodButton = findViewById(R.id.switchLoginMethodButton);
        switchLoginMethodButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlainLoginActivity.this,EmojiLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

