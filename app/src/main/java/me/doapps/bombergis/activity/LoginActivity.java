package me.doapps.bombergis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.doapps.bombergis.R;
import me.doapps.bombergis.session.SessionManager;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class LoginActivity extends ActionBarActivity {

    TextView txtUser;
    TextView txtPass;
    Button btnLogin;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(LoginActivity.this);
        if(sessionManager.getUserLogin()){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }


        txtUser = (TextView)findViewById(R.id.txtUser);
        txtPass = (TextView)findViewById(R.id.txtPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }
    public void ingresarCuenta(View view) {

        if (isValidEmail(txtUser.getText().toString())){
            //Toast.makeText(this,"Correo válido",Toast.LENGTH_SHORT).show();
            /*Verificar contraseña*/
            if(txtPass.getText().toString().length() < 6 || txtPass.getText().toString().equals("")){
                txtPass.requestFocus();
                Toast.makeText(LoginActivity.this,"Ingrese minimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }else{
                sessionManager.setUserLogin(true);
                sessionManager.setUserEmail(txtPass.getText().toString());
                sessionManager.setUserPass(txtPass.getText().toString());
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
        }
        else {
            txtUser.requestFocus();
            Toast.makeText(this,"Correo no valido",Toast.LENGTH_SHORT).show();
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
