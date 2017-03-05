package com.azell.chatify_android.Services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mcanhisares on 01/03/17.
 */

public class BaseService {
    protected DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
}
