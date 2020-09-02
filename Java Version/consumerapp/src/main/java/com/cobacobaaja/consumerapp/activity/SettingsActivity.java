package com.cobacobaaja.consumerapp.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.cobacobaaja.consumerapp.R;
import com.cobacobaaja.consumerapp.notification.AlarmReceiver;

public class SettingsActivity extends AppCompatActivity {
    public static AlarmReceiver alarmReceiver;
    public static final String REMINDER = "REMINDER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingsss, new SettingsFragment())
                .commit();
    }
    public static class SettingsFragment extends PreferenceFragmentCompat{
        SwitchPreferenceCompat alarm;
        SharedPreferences sharedPreferences;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_preference, rootKey);
            alarm = findPreference(getResources().getString(R.string.daily));
            sharedPreferences = getActivity().getPreferences(0);
            alarm.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if (alarm.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(REMINDER,true);
                        editor.apply();
                        alarmReceiver = new AlarmReceiver();
                        alarmReceiver.setRepeatingAlarm(getContext());
                    } else if (!alarm.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(REMINDER,false);
                        editor.apply();
                        alarmReceiver = new AlarmReceiver();
                        alarmReceiver.setAlarmRepeatingCancel(getContext());
                    }

                    return false;
                }
            });

        }
    }
}