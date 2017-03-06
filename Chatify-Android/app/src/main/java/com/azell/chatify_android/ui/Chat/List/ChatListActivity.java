package com.azell.chatify_android.ui.Chat.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.R;
import com.azell.chatify_android.Services.AuthenticationService;
import com.azell.chatify_android.Services.UserServiceListener;
import com.azell.chatify_android.Services.UsersService;
import com.azell.chatify_android.Utils.ActivityRoutes;
import com.azell.chatify_android.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListActivity extends BaseActivity implements UserServiceListener {

    private AuthenticationService authenticationService;
    private UsersService usersService;
    private ArrayList<User> usersList = new ArrayList<>();
    private ChatListAdapter listAdapter;

    @BindView(R.id.list_users_chat)
    ListView listUsersChat;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ChatListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.chat_list_activity_title);
        authenticationService = AuthenticationService.getInstance();
        usersService = UsersService.getInstance();
        usersService.getUsersList(this);
        listAdapter = new ChatListAdapter(this, usersList);
        listUsersChat.setAdapter(listAdapter);
        listUsersChat.setOnItemClickListener( (parent, view, position, id) ->  {
            User user = usersList.get(position);
            ActivityRoutes.getInstance().openChatDetailActivity(this, user);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                authenticationService.logOff();
                ActivityRoutes.getInstance().openLoginActivity(this);
                break;
            case R.id.menu_user_profile:
                ActivityRoutes.getInstance().openProfileActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUsersDataChanged(ArrayList<User> usersList) {
        this.usersList.clear();
        this.usersList.addAll(usersList);
        listAdapter.notifyDataSetChanged();
        System.out.println(this.usersList);
    }

    @Override
    public void onUserDataReturned(User user) {
        //UNUSED
    }

    @Override
    public void onUserCreated(User user) {
        //UNUSED
    }
}
