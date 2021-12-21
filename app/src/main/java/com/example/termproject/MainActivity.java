package com.example.termproject;


import static com.example.termproject.R.id;
import static com.example.termproject.R.layout;
import static com.example.termproject.R.string;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.termproject.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private final String mKeyPrefsName = "PREFS";
    private Boolean mKeyAutoSave;
    private String mKeyTextView = "TEXT_VIEW";
    private String mKeyTextInputView = "TEXT_INPUT_VIEW";
    private TextView textView;
    private TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(id.fab);
        this.textInputEditText = findViewById(id.textInputEditText);
        this.textView = findViewById(id.textView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(textInputEditText.getText());
            }
        });
        restoreAppSettingsFromPrefs();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_auto_save_checkox) {
            toggleAutoSave(item);
            mKeyAutoSave = item.isChecked();
        } else if (id == R.id.action_about_item) {
            showAboutItem(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAboutItem(Context context) {
        Utils.showInfoDialog(context, "About me", "This the 1st page. It Echos text input");
    }

    private void toggleAutoSave(MenuItem item) {
        item.setChecked(!item.isChecked());

    }

    public void saveToSharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(getString(string.autoSave), mKeyAutoSave);
        editor.putString(mKeyTextView, String.valueOf(textView.getText()));
        editor.putString(mKeyTextInputView, String.valueOf(textInputEditText.getText()));
        editor.apply();
        Toast.makeText(this, "Save Data",
                Toast.LENGTH_LONG).show();


    }

    private void restoreAppSettingsFromPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        mKeyAutoSave = sharedPreferences.getBoolean(getString(string.autoSave), false);
        if (mKeyAutoSave) {
            textView.setText(sharedPreferences.getString(mKeyTextView, ""));
            textInputEditText.setText(sharedPreferences.getString(mKeyTextInputView, ""));
        }
        Toast.makeText(this, "Restore Data",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        saveToSharedPref();
        super.onStop();
    }

}