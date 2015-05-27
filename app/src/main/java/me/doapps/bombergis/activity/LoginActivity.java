package me.doapps.bombergis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import me.doapps.bombergis.R;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void ingresar(View view) {
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
    }
}
