package com.azell.chatify_android.Services;

import android.provider.ContactsContract;
import android.util.Log;

import com.azell.chatify_android.Api.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by mcanhisares on 02/03/17.
 */

public class UsersService extends BaseService {

    private UsersService() { }

    private static volatile UsersService instance = null;

    public static UsersService getInstance() {
        if (instance == null) {
            synchronized (UsersService.class) {
                if (instance == null) {
                    instance = new UsersService();
                }
            }
        }
        return instance;
    }

    private ArrayList<User> users;

    public void getUsersList(UserServiceListener listener) {
        Query query = super.databaseReference.child("users").orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println(dataSnapshot);
                if (users == null) users = new ArrayList<>();
                User user = filterUsers(dataSnapshot);
                if (user != null) {
                    users.add(user);
                    listener.onUsersDataChanged(users);
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

    public void getUserByUid(String uid, AuthenticationServiceListener listener) {
        Query query = super.databaseReference.child("users").orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private User filterUsers(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        System.out.println(user);
        return Objects.equals(user.getUid(),
                AuthenticationService.getInstance().getCurrentUser().getUid()) ? null : user;
    }
}
