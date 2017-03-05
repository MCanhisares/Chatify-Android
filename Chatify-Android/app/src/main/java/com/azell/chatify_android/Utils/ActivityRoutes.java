package com.azell.chatify_android.Utils;

import android.content.Context;

import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.ui.Chat.Detail.ChatDetailActivity;
import com.azell.chatify_android.ui.Chat.List.ChatListActivity;
import com.azell.chatify_android.ui.Login.LoginActivity;
import com.azell.chatify_android.ui.Login.RegisterActivity;

/**
 * Created by mcanhisares on 01/03/17.
 */

public class ActivityRoutes {
    private static ActivityRoutes instance;

    public static ActivityRoutes getInstance() {
        if (instance == null) {
            instance = new ActivityRoutes();
        }
        return instance;
    }

    public void openRegisterActivity(Context context) {
        context.startActivity(RegisterActivity.createIntent(context));
    }

    public void openChatListActivity(Context context) {
        context.startActivity(ChatListActivity.createIntent(context));
    }

    public void openLoginActivity(Context context) {
        context.startActivity(LoginActivity.createIntent(context));
    }

    public void openChatDetailActivity(Context context, User user) {
        context.startActivity(ChatDetailActivity.createIntent(context, user));
    }
}
