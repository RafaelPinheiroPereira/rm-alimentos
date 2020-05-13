package br.com.app.rmalimentos.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import br.com.app.rmalimentos.utils.Singleton;
import br.com.app.rmalimentos.viewmodel.SessionManagerViewModel;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SessionManagerActivity extends AppCompatActivity {

    SessionManagerViewModel sessionManagerViewModel;

    AbstractActivity abstractActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagerViewModel = ViewModelProviders.of(this).get(SessionManagerViewModel.class);
        try {
            abstractActivity = Singleton.getInstance(AbstractActivity.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            this.sessionManagerViewModel
                    .checkedLogin()
                    .observe(
                            this,
                            employees->{
                                if (!Optional.ofNullable(employees).isPresent()) {
                                    AbstractActivity.navigateToActivity(
                                            getApplicationContext(),
                                            new Intent(SessionManagerActivity.this, LoginActivity.class));
                                } else {

                                    AbstractActivity.navigateToActivity(
                                            getApplicationContext(),
                                            new Intent(SessionManagerActivity.this, HomeActivity.class));
                                }
                            });
        } catch (ExecutionException e) {
            abstractActivity.showErrorMessage(getApplicationContext(), e.getMessage());
        } catch (InterruptedException e) {
            abstractActivity.showErrorMessage(getApplicationContext(), e.getMessage());
        }
    }
}
