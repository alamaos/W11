//Sources: https://www.geeksforgeeks.org/navigation-drawer-in-android/

package com.example.w11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static final String PREF_EDITTEXT_SIZE ="prefFont";
    public static final String PREF_EDITABLE = "prefEditable";
    public static final String PREF_COLOR = "prefColor";


    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    SharedPreferences sharedPreferences;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView textView;
    EditText editText;

    public void onSaveInstanceState(Bundle outState) {
        outState.putString("message", "Kirjoitettava");
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String message = savedInstanceState.getString("message");
        }
        setContentView(R.layout.activity_main);

        textView  = findViewById(R.id.textView2);
        editText = findViewById(R.id.editTextTextPersonName3);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        loadEditable(sharedPreferences);
        loadFontSize(sharedPreferences);
        loadFontColor(sharedPreferences);


        preferenceChangeListener = (sharedPreferences, s) -> {
            if(s.equals(PREF_EDITTEXT_SIZE)){
                String fontSize = sharedPreferences.getString(s, "");
                changeTextSize(fontSize);
            }
            if (s.equals(PREF_EDITABLE)){
                switchEditableStatus(sharedPreferences.getBoolean(s,true));
            }
            if (s.equals(PREF_COLOR)){
                String colorName = sharedPreferences.getString(s, "");
                changeFontColor(colorName);
            }

        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setNavigationViewListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Settings(View v){
        Intent intent = new Intent();
        startActivity(intent);
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;    }

    private void loadFontSize(SharedPreferences sharedPreferences) {
        Log.d("Pref_x", sharedPreferences.getString(PREF_EDITTEXT_SIZE,""));
        changeTextSize(sharedPreferences.getString(PREF_EDITTEXT_SIZE, ""));
    }
    private void loadEditable(SharedPreferences sharedPreferences) {
        Log.d("Pref_x", String.valueOf(sharedPreferences.getBoolean(PREF_EDITABLE, true)));
        switchEditableStatus(sharedPreferences.getBoolean(PREF_EDITABLE, true));
    }
    private void loadFontColor(SharedPreferences sharedPreferences) {
        Log.d("Pref_x", String.valueOf(sharedPreferences.getString(PREF_COLOR, getString(R.string.pref_color_value_black))));
        changeFontColor(sharedPreferences.getString(PREF_COLOR, getString(R.string.pref_color_value_black)));
    }

    private void changeFontColor(String color) {
        Log.d("Pref_x", color);
        if (color.equals("Black")) {
            textView.setTextColor(Color.BLACK);
        }else if(color.equals(("Blue"))){
            textView.setTextColor(Color.BLUE);
        }else if(color.equals("Red")){
            textView.setTextColor(Color.RED);
        }
    }


    private void switchEditableStatus(boolean editable) {
        editText.setEnabled(editable);
        editText.setFocusable(editable);
        if (!editable){
            showInTextView(editText.getText().toString());
        }
    }

    private void showInTextView(String text) {
        textView.setText(text);
    }

    private void changeTextSize(String font_size) {
        float size = Float.parseFloat(font_size);
        textView.setTextSize(size);
    }
}
