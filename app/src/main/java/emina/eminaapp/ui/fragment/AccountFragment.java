package emina.eminaapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import emina.eminaapp.R;
import emina.eminaapp.session.SessionManager;
import emina.eminaapp.ui.activity.LoginActivity;
import emina.eminaapp.ui.activity.RegisterActivity;

public class AccountFragment extends Fragment {
    private SessionManager sessionManager;
    private Button btnLogin, btnRegister;
    private LinearLayout parentLogin;
    private TextView tvLogout, tvEmail;
    private TextInputEditText inputEmail, inputPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(view.getContext());
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        parentLogin = view.findViewById(R.id.parentNotLogin);
        tvLogout = view.findViewById(R.id.tvLogout);
        tvEmail = view.findViewById(R.id.tvNameAccount);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);

        if (sessionManager.isLogin()) parentLogin.setVisibility(View.GONE);
        buttonClick();
    }

    private void buttonClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logOut();
                Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sessionManager.isLogin()) {
            parentLogin.setVisibility(View.GONE);
            tvEmail.setText(sessionManager.getDataCommit().get(SessionManager.EMAIL));
            inputEmail.setText(sessionManager.getDataCommit().get(SessionManager.EMAIL));
            inputPassword.setText(sessionManager.getDataCommit().get(SessionManager.PASSWORD));
        } else {
            parentLogin.setVisibility(View.VISIBLE);
        }
    }
}