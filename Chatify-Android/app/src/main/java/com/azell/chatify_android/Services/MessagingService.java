package com.azell.chatify_android.Services;

import com.azell.chatify_android.Api.Model.Message;
import com.azell.chatify_android.Api.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mcanhisares on 28/02/17.
 */

public class MessagingService extends BaseService {

    private MessagingService() { }

    private static volatile MessagingService instance = null;

    public static MessagingService getInstance() {
        if (instance == null) {
            synchronized (MessagingService.class) {
                if (instance == null) {
                    instance = new MessagingService();
                }
            }
        }
        return instance;
    }

    List<Message> messages;

    public void getMessagesFor(User currentUser, User toUser, MessagingServiceListener listener) {
        Query query = super.databaseReference.child("messages");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println(dataSnapshot);
                if (messages == null) messages = new ArrayList<>();
                Message message = filterMessageByUser(currentUser, toUser, dataSnapshot);
                if (message != null) {
                    messages.add(message);
                    listener.onMessagesDataChanged(messages);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void postMessage(String messageText, User toUser) {
        Message message = new Message(messageText, toUser.getUid(),
                AuthenticationService.getInstance().getCurrentUser().getUid(),
                AuthenticationService.getInstance().getCurrentUser().getUsername());
        super.databaseReference.child("messages").push().setValue(message);
    }

    private Message filterMessageByUser(User currentUser, User toUser, DataSnapshot dataSnapshot) {
        Message message = dataSnapshot.getValue(Message.class);
        System.out.println(message);
        if (Objects.equals(message.getToId(), toUser.getUid())
                && Objects.equals(message.getUid(), currentUser.getUid())
                || Objects.equals(message.getToId(), currentUser.getUid())
                && Objects.equals(message.getUid(), toUser.getUid())) {
            return message;
        }
        return null;
    }

    public void clearMessages() {
        if (messages != null) {
            this.messages.clear();
            this.messages = null;
        }
    }
}
