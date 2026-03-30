package com.example.lab7_devmobil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab7_devmobil.adapter.StarAdapter;
import com.example.lab7_devmobil.service.StarService;

/**
 * MainActivity : Point d'entrée principal de l'application.
 * Gère l'affichage de la liste, la recherche, le partage et l'intégration du SplashScreen.
 * Développé par Mohammed Amine L pour le Lab7 - Architecture MVC.
 */
public class MainActivity extends AppCompatActivity {

    private StarAdapter adapter;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialisation de l'écran d'accueil fluide (SplashScreen)
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration de la barre d'outils (Toolbar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialisation du service de données (Singleton)
        service = StarService.getInstance();
        
        // Configuration du RecyclerView
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Liaison de l'adaptateur avec la liste des stars
        adapter = new StarAdapter(this, service.findAll());
        recycler.setAdapter(adapter);

        // Gestion de la barre de recherche (SearchView)
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrage instantané lors de la saisie
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    /**
     * Crée le menu d'options (Partage).
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Gère les clics sur les éléments du menu.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Lance l'action de partage de l'application.
     */
    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Application Stars - Lab7");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Découvrez cette application développée par Mohammed Amine L pour gérer les stars !");
        startActivity(Intent.createChooser(shareIntent, "Partager via"));
    }
}