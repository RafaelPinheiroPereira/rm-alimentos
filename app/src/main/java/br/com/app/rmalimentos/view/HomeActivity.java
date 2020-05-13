package br.com.app.rmalimentos.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import br.com.app.rmalimentos.R;
import br.com.app.rmalimentos.view.fragment.EmptyFragment;
import br.com.app.rmalimentos.view.fragment.HomeFragment;
import br.com.app.rmalimentos.viewmodel.HomeViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog.Builder;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;

    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViews();
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            homeViewModel
                    .getAllRoutes()
                    .observe(
                            this,
                            routes->{
                                if (routes.size() > 0) {
                                    HomeFragment homeFragment = new HomeFragment();
                                    this.loadFragment(homeFragment);

                                } else {

                                    EmptyFragment emptyFragment = new EmptyFragment();
                                    this.loadFragment(emptyFragment);
                                    // exibir spty state
                                }
                            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bottomNavigationView.setSelectedItemId(R.id.page_1);
        bottomNavigationView.setOnNavigationItemReselectedListener(
                item->{
                    switch (item.getItemId()) {
                        case R.id.page_1:
                            // Todo recarregar tela inicial sei lá
                            AbstractActivity.showMessage(this, "Clientes");
                            break;
                        case R.id.page_3:
                            if (homeViewModel.containsAllFiles()) {
                                //Todo implementar asystask de importacao

                                homeViewModel.downloadFiles();


                            } else {
                                //Todo consultar quais arquivos estao faltando e exibir para o usuario

                                StringBuilder message = homeViewModel.searchInexistsFilesNames();

                                BottomSheetMaterialDialog mBottomSheetDialog =
                                        new Builder(this)
                                                .setTitle("Atenção, não foram localizados os arquivos abaixo:")
                                                .setMessage(message.toString())
                                                .setCancelable(false)
                                                .setPositiveButton(
                                                        "OK",
                                                        (dialogInterface, which)->{
                                                            AbstractActivity.showMessage(this,
                                                                    "Por favor, realize a inclusão dos arquivos");

                                                            dialogInterface.dismiss();
                                                        })
                                                .build();

                                mBottomSheetDialog.show();
                            }
                            break;
                    }
                });
    }

    @Override
    public void onBackPressed() {
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initViews() {
        toolbar.setTitle("Trinity Mobile - R&M Alimentos");
        setSupportActionBar(toolbar);
    }
}
