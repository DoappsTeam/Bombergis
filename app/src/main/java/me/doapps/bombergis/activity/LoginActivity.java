package me.doapps.bombergis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.doapps.bombergis.R;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class LoginActivity extends ActionBarActivity {

    TextView txtUser;
    TextView txtPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = (TextView)findViewById(R.id.txtUser);
        txtPass = (TextView)findViewById(R.id.txtPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }
    public void ingresarCuenta(View view) {
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
    }
}
