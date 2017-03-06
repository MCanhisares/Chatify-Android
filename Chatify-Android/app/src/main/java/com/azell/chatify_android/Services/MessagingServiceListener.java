package com.azell.chatify_android.Services;

import com.azell.chatify_android.Api.Model.Message;

import java.util.List;

/**
 * Created by mcanhisares on 05/03/17.
 */

public interface MessagingServiceListener {
    void onMessagesDataChanged(List<Message> messages);
}
