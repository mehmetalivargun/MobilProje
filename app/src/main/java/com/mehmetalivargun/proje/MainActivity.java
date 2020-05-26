package com.mehmetalivargun.proje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Database db;
    private RecyclerView list;
    private RemindAdapter remindAdapter;
    Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    protected void onRestart() {
// TODO Auto-generated method stub
        super.onRestart();
        remindAdapter.notifyDataSetChanged();
        //Do your code here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);




        Intent intent = new Intent(MainActivity.this,AlarmReciever.class);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        AlarmManager alarmManager1=(AlarmManager)getSystemService(ALARM_SERVICE);

        long time =System.currentTimeMillis();
        long tenSec=1000*10;
      //  alarmManager.set(AlarmManager.RTC_WAKEUP,time+2*tenSec,pendingIntent);

        toolbar=findViewById(R.id.toolbar);
        db= new Database(getApplicationContext());



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Date ReminD");
        FloatingActionButton mAddReminderButton = findViewById(R.id.floatingActionButton);

        list = findViewById(R.id.recyclerView);

        ArrayList<Remind> alarms=db.getAllReminders();
        remindAdapter = new RemindAdapter(this,alarms);
        list.setAdapter(remindAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, add_reminder.class);
                startActivity(intent);

            }
        });


        if(alarms.isEmpty()){
            Toast.makeText(this, "Deneme", Toast.LENGTH_LONG).show();
        }

    }


    private void createNotificationChannel(){
        CharSequence name = "Alarm Project";
        String desc="Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel= new NotificationChannel("notifyLemubit",name,importance);
        channel.setDescription(desc);
        NotificationManager notificationManager= getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }
}
