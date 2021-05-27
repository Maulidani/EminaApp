package emina.eminaapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import emina.eminaapp.MainActivity;
import emina.eminaapp.R;
import emina.eminaapp.api.ApiClient;
import emina.eminaapp.api.ApiInterface;
import emina.eminaapp.api.ModelApiResponse;
import emina.eminaapp.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private TextInputEditText inputEmail, inputPassword;
    private Button btnLogin, btnSkip;
    private LinearLayout register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(LoginActivity.this);

        String intentEmail = getIntent().getStringExtra("email");
        String intentPassword = getIntent().getStringExtra("password");

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSkip = findViewById(R.id.btnSkipLogin);
        register = findViewById(R.id.parentRegister);

        inputEmail.setText(intentEmail);
        inputPassword.setText(intentPassword);

        clickListener();

    }

    private void clickListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    login(email, password);
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }

    private void login(String email, String password) {

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelApiResponse> call = apiInterface.postLogin(email, password);
        call.enqueue(new Callback<ModelApiResponse>() {
            @Override
            public void onResponse(Call<ModelApiResponse> call, Response<ModelApiResponse> response) {
                int value = response.body().getValue();

                if (value == 1) {
                    sessionManager.CreatLoginSession(email, password);
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}