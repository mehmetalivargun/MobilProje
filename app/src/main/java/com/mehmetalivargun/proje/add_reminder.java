package com.mehmetalivargun.proje;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.os.SystemClock;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.Calendar;


public class add_reminder extends AppCompatActivity  {
    Context context = this;
    private Toolbar mToolbar;
    private EditText mTitleText;
    private TextView details, getmDateText, getmTimeText, getmRepeatText, getmRepeatNoText, getmRepeatTypeText;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private Fragment fragment;

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        fragment =new Fragment();
        // Initialize Views
        mToolbar = findViewById(R.id.toolbar);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/SourceLight.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/SourceBold.ttf");
        mTitleText = findViewById(R.id.reminder_title);

        mDateText = findViewById(R.id.set_date);
        mTimeText = findViewById(R.id.set_time);
        mRepeatText = findViewById(R.id.set_repeat);
        mRepeatNoText = findViewById(R.id.set_repeat_no);
        mRepeatTypeText = findViewById(R.id.set_repeat_type);
        mFAB1 = findViewById(R.id.starred1);
        mFAB2 = findViewById(R.id.starred2);
       // details = findViewById(R.id.details);
        getmDateText = findViewById(R.id.date_text);
        getmTimeText = findViewById(R.id.time_text);
        getmRepeatText = findViewById(R.id.repeat_text);
        getmRepeatNoText = findViewById(R.id.repeat_no_text);
        getmRepeatTypeText = findViewById(R.id.repeat_type_text);


        getmRepeatTypeText.setTypeface(tf2);
        getmRepeatNoText.setTypeface(tf2);
        getmRepeatText.setTypeface(tf2);
        getmTimeText.setTypeface(tf2);
        getmDateText.setTypeface(tf2);
        mTitleText.setTypeface(tf);
        mDateText.setTypeface(tf);
//        details.setTypeface(tf2);
        mTimeText.setTypeface(tf);
        mRepeatText.setTypeface(tf);
        mRepeatNoText.setTypeface(tf);
        mRepeatTypeText.setTypeface(tf);

        // Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Date ReminD");

        // Initialize default values
        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        // Setup Reminder Title EditText
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Setup TextViews using reminder values
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mRepeatNoText.setText(mRepeatNo);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeatText.setText(saveRepeat);
            mRepeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            mRepeatNoText.setText(savedRepeatNo);
            mRepeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatTypeText.setText(savedRepeatType);
            mRepeatType = savedRepeatType;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }

        // Setup up active buttons
        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }
    }

    // To save state on device rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, mTitleText.getText());
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_REPEAT, mRepeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO, mRepeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatTypeText.getText());
        outState.putCharSequence(KEY_ACTIVE, mActive);
    }

    // On clicking Time picker
    public void setTime(View v) {
        Calendar now = Calendar.getInstance();

        TimePickerDialog tpd = new android.app.TimePickerDialog(context, new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute < 10) {
                    mTime = hourOfDay + ":" + "0" + minute;
                } else {
                    mTime = hourOfDay + ":" + minute;
                }
                mTimeText.setText(mTime);
            }
        },mHour,mMinute,true);
        tpd.show();


    }

    // On clicking Date picker
    public void setDate(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;
                mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
                mDateText.setText(mDate);
            }
        },mYear,mMonth,mDay);
        dpd.show();
    }

    /* // Obtain time from time picker
     @Override
     public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
         mHour = hourOfDay;
         mMinute = minute;
         if (minute < 10) {
             mTime = hourOfDay + ":" + "0" + minute;
         } else {
             mTime = hourOfDay + ":" + minute;
         }
         mTimeText.setText(mTime);
     }

     // Obtain date from date picker
     @Override
     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         monthOfYear++;
         mDay = dayOfMonth;
         mMonth = monthOfYear;
         mYear = year;
         mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
         mDateText.setText(mDate);
     }
 */
    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    // On clicking the repeat switch
    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            mRepeat = "true";
            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        } else {
            mRepeat = "false";
            mRepeatText.setText(R.string.repeat_off);
        }
    }

    // On clicking repeat type button
    public void selectRepeatType(View v) {
        final String[] items = new String[5];

        items[0] = "Minute";
        items[1] = "Hour";
        items[2] = "Day";
        items[3] = "Week";
        items[4] = "Month";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // On clicking repeat interval button
    public void setRepeatNo(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (input.getText().toString().length() == 0) {
                    mRepeatNo = Integer.toString(1);
                    mRepeatNoText.setText(mRepeatNo);
                    mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                } else {
                    mRepeatNo = input.getText().toString().trim();
                    mRepeatNoText.setText(mRepeatNo);
                    mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    // On clicking the save button
    public void saveReminder() {
        Database rb = new Database(this);

        // Creating Reminder
        int ID = rb.addReminder(new Remind(mTitle, mDate, mTime, mRepeat, null, mRepeatType, "true"));

        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        // Check repeat type
        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        }

        // Create a new notification
       if (mActive.equals("true")) {
            if (mRepeat.equals("true")) {
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent intent = new Intent(add_reminder.this,AlarmReciever.class);
                PendingIntent pendingIntent =PendingIntent.getBroadcast(add_reminder.this,0,intent,0);
                manager.set(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),pendingIntent);







                Calendar c = Calendar.getInstance();
                long currentTime = c.getTimeInMillis();
                long diffTime = mCalendar.getTimeInMillis() - currentTime;

                // Start alarm using initial notification time and repeat interval time
                manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + diffTime, mRepeatTime, pendingIntent);

               /* AlarmReciever a=new AlarmReciever();
                a.setRepeatAlarm(getApplicationContext(), mCalendar, ID, mRepeatTime);
                a.details=mTitle;*/
            } else if (mRepeat.equals("false")) {
                AlarmReciever a=new AlarmReciever();
                a.setAlarm(getApplicationContext(), mCalendar, ID);
                a.details=mTitle;
            }
        }

        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

        onBackPressed();
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }


    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;

            // On clicking save reminder button
            // Update reminder
            case R.id.save_reminder:
                mTitleText.setText(mTitle);

                if (mTitleText.getText().toString().length() == 0)
                    mTitleText.setError("Reminder title cannot be blank!");

                else {
                    saveReminder();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_reminder:
                Toast.makeText(getApplicationContext(), "Discarded", Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
