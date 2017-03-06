package com.azell.chatify_android.Services;

import com.azell.chatify_android.Api.Model.User;

import java.util.ArrayList;

/**
 * Created by mcanhisares on 04/03/17.
 */

public interface UserServiceListener {
    void onUsersDataChanged(ArrayList<User> usersList);
    void onUserDataReturned(User user);
    void onUserCreated(User user);
}
