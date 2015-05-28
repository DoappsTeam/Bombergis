package me.doapps.bombergis.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        if (isValidEmail(txtUser.getText().toString())){
            //Toast.makeText(this,"Correo v�lido",Toast.LENGTH_SHORT).show();
            /*Verificar contrase�a*/

            //Intent i = new Intent(this, DashboardActivity.class);
            //startActivity(i);
        }
        else {
            Toast.makeText(this,"Correo no valido",Toast.LENGTH_SHORT).show();
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
