package com.azell.chatify_android.Services;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

/**
 * Created by mcanhisares on 01/03/17.
 */

public class AuthenticationService extends BaseService{

    private FirebaseAuth firebaseAuth;
    private User currentUser;
    private String currentUserUid;


    private AuthenticationService() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private static volatile AuthenticationService instance = null;

    public static AuthenticationService getInstance() {
        if (instance == null) {
            synchronized (AuthenticationService.class) {
                if (instance == null) {
                    instance = new AuthenticationService();
                }
            }
        }
        return instance;
    }

    public void setCurrentUserUid(String currentUserUid) {
        this.currentUserUid = currentUserUid;
        updateCurrentUser();
    }

    private void updateCurrentUser() {
        Query query = super.databaseReference.child("users")
                .child(currentUserUid)
                .orderByChild("uid");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Timber.d("getUsersList onChildAdded %s", dataSnapshot);
                System.out.println(dataSnapshot);
                currentUser = dataSnapshot.getValue(User.class);
                Log.d("getUsersList ", currentUser.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void registerUser(Activity activity, String email, String password)  {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    Log.d("", "createUserWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(activity, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loginUser(Activity activity, String email, String password,
                          AuthenticationServiceListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task ->  {
                    if (!task.isSuccessful()) {
                        Timber.e("signInWithEmail:failed %s", task.getException());
                        listener.onUserLoginFailed(task.getException());
                    }
                    Timber.d("Logged in successfully %s", task.getResult());
                });
    }

    public void logOff() {
        firebaseAuth.signOut();
    }

    public void addListener(AuthStateListener listener) {
        firebaseAuth.addAuthStateListener(listener);
    }

    public void removeListener(AuthStateListener listener) {
        firebaseAuth.removeAuthStateListener(listener);
    }
}
