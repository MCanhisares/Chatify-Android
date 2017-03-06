package com.azell.chatify_android.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.R;
import com.azell.chatify_android.Services.AuthenticationService;
import com.azell.chatify_android.Services.AuthenticationServiceListener;
import com.azell.chatify_android.Services.UserServiceListener;
import com.azell.chatify_android.Services.UsersService;
import com.azell.chatify_android.Utils.ActivityRoutes;
import com.azell.chatify_android.ui.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RegisterActivity extends BaseActivity implements AuthenticationServiceListener{

    private FirebaseAuth.AuthStateListener authListener;
    private AuthenticationService authService;

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.register_progress_bar)
    ProgressBar registerProgressBar;
    @BindView(R.id.btn_register)
    Button btnRegister;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        authService.addListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            authService.removeListener(authListener);
        }
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.register_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        authService = AuthenticationService.getInstance();
        btnRegister.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            String username = inputUsername.getText().toString();
            register(email, password, username);
        });
        setupAuthListener();
    }

    private void register(String email, String password, String username) {
        if (!email.isEmpty() && !password.isEmpty() && !username.isEmpty()) {
            authService.registerUser(this, email, password, this);
            registerProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void setupAuthListener() {
        authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d("onAuthStateChanged", user.getUid());
                UsersService.getInstance().createUser(inputUsername.getText().toString(),
                        inputEmail.getText().toString(), user.getUid(), authService);
                registerProgressBar.setVisibility(View.GONE);
                openChatList();
            } else {
                // User is signed out
                Timber.d("onAuthStateChanged:signed_out");
            }
        };
    }

    private void openChatList() {
        ActivityRoutes.getInstance().openChatListActivity(this);
    }

    @Override
    public void onUserLoginFailed(Exception exception) {
        //UNUSED
    }

    @Override
    public void onRegistrationFailed(Exception exception) {
        registerProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, R.string.auth_failed,
                Toast.LENGTH_SHORT).show();
        finish();
    }
}
