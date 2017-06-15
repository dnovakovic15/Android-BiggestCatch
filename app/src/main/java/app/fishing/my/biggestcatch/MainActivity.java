package app.fishing.my.biggestcatch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //onLoginSuccess("DELETE THIS");
    }

    public void login(View v) {
        login();
    }

    public void login() {

        //Delete line below later. There to skip log in process.
        //onLoginSuccess("dnovakovic21@yahoo.com");
        //Testing

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        findViewById(R.id.btn_login).setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = ((EditText)findViewById(R.id.input_email)).getText().toString();
        final String password = ((EditText)findViewById(R.id.input_password)).getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        API_Log_in asyncTask1 = new API_Log_in();
                        List<String> verification = null;

                        try {
                            verification = asyncTask1.execute(email, password).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        if(verification.get(2).indexOf("pass") > -1){
                            TokenSaver.setToken(getApplicationContext(), verification.get(0));
                            System.out.println("Token: " + TokenSaver.getToken(getApplicationContext()));
                            username = verification.get(1);
                            onLoginSuccess(username);
                        }
                        else{
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 300);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String username) {
        findViewById(R.id.btn_login).setEnabled(true);
        Toast.makeText(getBaseContext(), "Log in Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, UserAccount.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        findViewById(R.id.btn_login).setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = ((EditText) findViewById(R.id.input_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.input_password)).getText().toString();

        if (email.isEmpty()) {
            final EditText e_mail = (EditText) findViewById(R.id.input_email);
            e_mail.setError("Enter a valid email address");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            final EditText pass = (EditText) findViewById(R.id.input_password);
            pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    public void link_signup(View view){
        startActivity(new Intent(MainActivity.this, Signup_Activity.class));
    }
}
