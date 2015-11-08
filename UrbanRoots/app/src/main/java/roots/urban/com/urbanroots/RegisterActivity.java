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
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.RegistrationData;
import helper.API;
import helper.Validator;

public class RegisterActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String password;
    private String passwordConfirmation;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etPasswordConfirmation;

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
        etFirstName = (EditText) findViewById(R.id.first_name);
        etLastName = (EditText) findViewById(R.id.last_name);
        etAddress = (EditText) findViewById(R.id.address);
        etEmail = (EditText) findViewById(R.id.email);
        etPhoneNumber = (EditText) findViewById(R.id.phone_number);
        etPassword = (EditText) findViewById(R.id.password);
        etPasswordConfirmation = (EditText) findViewById(R.id.password_confirmation);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
                lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
                address = ((EditText) findViewById(R.id.address)).getText().toString();
                email = ((EditText) findViewById(R.id.email)).getText().toString();
                phoneNumber = ((EditText) findViewById(R.id.phone_number)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();
                passwordConfirmation = ((EditText) findViewById(R.id.password_confirmation)).getText().toString();

                if (isValid()) {
                    sendData();
                }
            }
        });
    }

    private boolean isValid(){
        if(!Validator.isEmailValid(email)){
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Validator.isPhoneNumberValid(phoneNumber)){
            Toast.makeText(this, "Phone Number is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Validator.isPasswordValid(password)){
            Toast.makeText(this, "Password should follow the following: at least 8 characters, contains lower case, upper case, special character @#$%^&+=, no whitespace", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(passwordConfirmation)){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void sendData(){
        RegistrationData data = new RegistrationData(firstName, lastName, address, email, phoneNumber, password);

        API.register(RegisterActivity.this, data);
    }

}
