package com.tamanna.mvvm_loginpage_livedata_databinding.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import com.tamanna.mvvm_loginpage_livedata_databinding.model.LoginUser;
import com.tamanna.mvvm_loginpage_livedata_databinding.R;
import com.tamanna.mvvm_loginpage_livedata_databinding.viewmodel.LoginViewModel;
import com.tamanna.mvvm_loginpage_livedata_databinding.databinding.ActivityMainBinding;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginViewModel = ViewModelProviders.of(MainActivity.this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);


        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {

                    public void onChanged(@Nullable LoginUser loginUser) {

                        if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                            binding.txtEmailAddress.setError("Enter an E-Mail Address");
                            binding.txtEmailAddress.requestFocus();
                        }
                        else if (!loginUser.isEmailValid()) {
                            binding.txtEmailAddress.setError("Enter a Valid E-mail Address");
                            binding.txtEmailAddress.requestFocus();
                        }
                        else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                            binding.txtPassword.setError("Enter a Password");
                            binding.txtPassword.requestFocus();
                        }
                        else if (!loginUser.isPasswordLengthGreaterThan5()) {
                            binding.txtPassword.setError("Enter at least 6 Digit password");
                            binding.txtPassword.requestFocus();
                        }
                        else {
                            binding.lblEmailAnswer.setText(loginUser.getStrEmailAddress());
                            binding.lblPasswordAnswer.setText(loginUser.getStrPassword());
                        }

                    }
                });

    }
}