package com.azell.chatify_android.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.azell.chatify_android.Utils.ActivityRoutes;
import com.azell.chatify_android.ui.BaseActivity;
import com.azell.chatify_android.ui.Chat.List.ChatListActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements AuthenticationServiceListener{

    private AuthStateListener authListener;
    private AuthenticationService authService;

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.login_progress_bar)
    ProgressBar loginProgressBar;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
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
        authService = AuthenticationService.getInstance();

//        //TODO REMOVE AFTER TESTING
//        authService.logOff();

        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            login(email, password);
        });
        btnRegister.setOnClickListener(v -> register());
        setupAuthListener();
    }

    private void login(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            authService.loginUser(this, email, password, this);
            loginProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void register() {
        ActivityRoutes.getInstance().openRegisterActivity(this);
    }

    private void setupAuthListener() {
        authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d("onAuthStateChanged", user.getUid());
                authService.setCurrentUserUid(user.getUid());
                loginProgressBar.setVisibility(View.GONE);
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
        loginProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, R.string.auth_failed,
                Toast.LENGTH_SHORT).show();
    }
}
