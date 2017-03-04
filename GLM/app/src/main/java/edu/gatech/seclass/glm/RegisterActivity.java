package edu.gatech.seclass.glm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private EditText regEmailEditText;
    private EditText regPassWordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        regEmailEditText = (EditText)findViewById(R.id.regEmailEditText);
        regPassWordEditText = (EditText)findViewById(R.id.regPassWordEditText);
        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                registerUser(v);
            }
        });
    }

    public void registerUser(View view) {
        String email = regEmailEditText.getText().toString().trim();
        String password = regPassWordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            displayAlert("Email or password missing");
        } else {
            mFirebaseAuth.createUserWithEmailAndPassword (email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, ManageListsActivity.class);
                                startActivity(intent);
                            } else {
                                displayAlert(task.getException().getMessage());
                            }
                        }
                    });
        }
    }

    private void displayAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(message)
                .setTitle("We couldn't register you :(")
                .setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
