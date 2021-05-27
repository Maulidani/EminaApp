package emina.eminaapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import emina.eminaapp.MainActivity;
import emina.eminaapp.R;
import emina.eminaapp.api.ApiClient;
import emina.eminaapp.api.ApiInterface;
import emina.eminaapp.api.ModelApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText inputEmail, inputPassword, inputPassword2;
    private Button btnRegister;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputPassword2 = findViewById(R.id.inputPassword2);
        btnRegister = findViewById(R.id.btnContinue);
        cbAgree = findViewById(R.id.cbAgree);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String password2 = inputPassword2.getText().toString();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Password tidak sama", Toast.LENGTH_SHORT).show();
                } else if (!cbAgree.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Check Setuju terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    register(email, password);
                }
            }
        });
    }

    private void register(String email, String password) {

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelApiResponse> call = apiInterface.postRegister(email, password);
        call.enqueue(new Callback<ModelApiResponse>() {
            @Override
            public void onResponse(Call<ModelApiResponse> call, Response<ModelApiResponse> response) {
                int value = response.body().getValue();

                if (value == 1) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                            .putExtra("email", email)
                            .putExtra("password", password));
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