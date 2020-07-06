package br.com.app.rmalimentos.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import br.com.app.rmalimentos.utils.Singleton;
import br.com.app.rmalimentos.viewmodel.SessionManagerViewModel;

public class SessionManagerActivity extends AppCompatActivity {

    SessionManagerViewModel sessionManagerViewModel;

    AbstractActivity abstractActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagerViewModel = new ViewModelProvider(this).get(SessionManagerViewModel.class);
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

        if (this.sessionManagerViewModel.checkedLogin()) {
            AbstractActivity.navigateToActivity(
                    getApplicationContext(),
                    new Intent(SessionManagerActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(SessionManagerActivity.this, LoginActivity.class));
        }


    }
}
