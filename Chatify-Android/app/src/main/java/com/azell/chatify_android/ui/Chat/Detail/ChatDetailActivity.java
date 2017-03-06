package com.azell.chatify_android.ui.Chat.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.azell.chatify_android.Api.Model.Message;
import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.R;
import com.azell.chatify_android.Services.AuthenticationService;
import com.azell.chatify_android.Services.MessagingService;
import com.azell.chatify_android.Services.MessagingServiceListener;
import com.azell.chatify_android.Utils.Enums.LoginResults;
import com.azell.chatify_android.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatDetailActivity extends BaseActivity implements MessagingServiceListener {

    private static final String USER = "USER";

    @BindView(R.id.button_send_message)
    FloatingActionButton buttonSendMessage;
    @BindView(R.id.edit_message)
    EditText editMessage;
    @BindView(R.id.list_message)
    ListView listMessage;

    private User user;
    private MessagingService messagingService;
    private MessageListAdapter messageListAdapter;
    private ArrayList<Message> messages = new ArrayList<>();

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

    @Override
    protected void onStop() {
        super.onStop();
        messagingService.clearMessages();
    }

    private void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(user.getUsername().toUpperCase());
        messagingService = MessagingService.getInstance();
        messagingService.getMessagesFor(AuthenticationService.getInstance().getCurrentUser(),
                user, this);
        messageListAdapter = new MessageListAdapter(this, messages);
        listMessage.setAdapter(messageListAdapter);
        buttonSendMessage.setOnClickListener(v -> {
            if (!editMessage.getText().toString().isEmpty()) {
                messagingService.postMessage(editMessage.getText().toString(), user);
                editMessage.setText("");
            }
        });
    }

    @Override
    public void onMessagesDataChanged(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        this.messageListAdapter.notifyDataSetChanged();
    }
}
