package com.azell.chatify_android.ui.Chat.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.R;
import com.azell.chatify_android.Utils.Enums.LoginResults;
import com.azell.chatify_android.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatDetailActivity extends BaseActivity {

    private static final String USER = "USER";

    @BindView(R.id.button_send_message)
    FloatingActionButton buttonSendMessage;
    @BindView(R.id.edit_message)
    EditText editMessage;
    @BindView(R.id.list_message)
    ListView listMessage;

    private User user;

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtra(USER, user);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        user = data.getParcelable(USER);
        init();
    }

    private void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(user.getUsername().toUpperCase());
    }

    private void displayChatMessages() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LoginResults.SIGN_IN_REQUEST_CODE.code) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }
}
