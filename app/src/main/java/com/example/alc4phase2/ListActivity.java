package com.example.alc4phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
        if (FirebaseUtil.isAdmin == true){
            insertMenu.setVisible(true);
        }else {
            insertMenu.setVisible(false);
        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                startActivity(new Intent(this, DealActivity.class));
                 return true;
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUtil.openFbReference("traveldeals", this );
        RecyclerView rvDeals = findViewById(R.id.rvDeals);
        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);
        rvDeals.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        FirebaseUtil.attachListener();

    }


    public void showMenu(){
        invalidateOptionsMenu();
    }
}
