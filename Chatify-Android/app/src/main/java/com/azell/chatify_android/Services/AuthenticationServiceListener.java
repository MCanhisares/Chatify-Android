package com.azell.chatify_android.Services;

import com.azell.chatify_android.Api.Model.User;

/**
 * Created by mcanhisares on 01/03/17.
 */

public interface AuthenticationServiceListener {
    void onUserLoginFailed(Exception exception);
}
