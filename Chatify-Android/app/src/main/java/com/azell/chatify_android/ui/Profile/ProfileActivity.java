package com.azell.chatify_android.ui.Profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.azell.chatify_android.R;
import com.azell.chatify_android.Services.AuthenticationService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_PHOTO_FOR_AVATAR = 0;

    private BroadcastReceiver broadcastReceiver;

    @BindView(R.id.image_user_profile)
    ImageView imageUserProfile;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        //TODO IMPLEMENT BROADCAST MANAGER FOR IMAGE UPLOAD
//        manager.registerReceiver();
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.profile_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        System.out.println(AuthenticationService.getInstance().getCurrentUser());
        if (AuthenticationService.getInstance().getCurrentUser() != null) {
            String url = AuthenticationService.getInstance().getCurrentUser().getProfileImageUrl();
            setImageUserProfile(url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_add:
                pickImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PICK_PHOTO_FOR_AVATAR:

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void uploadPhoto(Uri fileUri) {
        //TODO IMPLEMENT SERVICE FOR IMAGE UPLOAD
//        startService()
    }

    public void pickImage() {
        //TODO IMPLEMENT IMAGE PICKER FOR IMAGE UPLOAD
//        Intent intent = new ImagePicker.getPickImageIntent(this);
//        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    private void setImageUserProfile(String url) {
        Picasso.with(this)
                .load(Uri.parse(url))
                .into(imageUserProfile);
    }

}
