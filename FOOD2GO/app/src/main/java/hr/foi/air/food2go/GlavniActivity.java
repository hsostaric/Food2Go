package hr.foi.air.food2go;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.controller.Internet;
import hr.foi.air.food2go.controller.LogInActivity;
import hr.foi.air.food2go.fragmenti.kategorije.KategorijeFragment;
import hr.foi.air.food2go.fragmenti.moje_narudzbe.MojeNarudzbeFragment;
import hr.foi.air.food2go.fragmenti.nagrade.NagradeFragment;
import hr.foi.air.food2go.fragmenti.postavke.PostavkeFragment;
import hr.foi.air.food2go.fragmenti.stanje_bodova.StanjeBodovaFragment;
import hr.foi.air.food2go.fragmenti.trenutna_narudzba.TrenutnaNarudzbaFragment;

public class GlavniActivity extends AppCompatActivity  {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_glavni);
        try {
            initializeLayout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private void initializeLayout() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        drawerLayout=findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.kategorije, R.id.trenutna_narudzba, R.id.moje_narudzbe,
                R.id.nagrade, R.id.stanje_bodova, R.id.postavke, R.id.odjava)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headView = navigationView.getHeaderView(0);
        TextView prijavljeniKorisnik = headView.findViewById(R.id.prijavljeniKorisnik);
        prijavljeniKorisnik.setText(Korisnik.getPrijavljeniKorisnik().vratiImeiPrezime());

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
