package roots.urban.com.urbanroots;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import entity.RegistrationData;
import helper.API;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                String passwordConfirmation = ((EditText)findViewById(R.id.password_confirmation)).getText().toString();

                if(password.equals(passwordConfirmation)) {
                    String firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
                    String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
                    String email = ((EditText) findViewById(R.id.email)).getText().toString();
                    String phoneNumber = ((EditText) findViewById(R.id.phone_number)).getText().toString();

                    RegistrationData data = new RegistrationData(firstName, lastName, email, phoneNumber, password);

                    API.register(RegisterActivity.this, data);
                }
            }
        });
    }

}
