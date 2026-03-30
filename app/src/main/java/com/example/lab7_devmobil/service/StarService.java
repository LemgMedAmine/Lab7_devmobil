package com.example.lab7_devmobil.service;

import com.example.lab7_devmobil.R;
import com.example.lab7_devmobil.beans.Star;
import com.example.lab7_devmobil.dao.IDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe StarService : Fournit les services de gestion des données pour l'entité Star.
 * Implémente le pattern Singleton pour assurer une instance unique des données.
 * Développé par Mohammed Amine L - Architecture MVC avec ressources locales.
 */
public class StarService implements IDao<Star> {

    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed(); // Initialisation des données avec ressources locales
    }

    /**
     * @return L'instance unique de StarService (Singleton).
     */
    public static StarService getInstance() {
        if (instance == null)
            instance = new StarService();
        return instance;
    }

    /**
     * Remplit la liste avec les acteurs dont les photos sont présentes dans res/drawable.
     */
    private void seed() {
        stars.add(new Star("Robert Downey Jr.", R.drawable.robertdowneyjr, 4.5f));
        stars.add(new Star("Leonardo DiCaprio", R.drawable.leonardo, 4.8f));
        stars.add(new Star("Brad Pitt", R.drawable.bradpitt, 4.6f));
    }

    @Override
    public List<Star> findAll() {
        return stars;
    }

    @Override
    public boolean create(Star o) { return stars.add(o); }

    @Override
    public boolean update(Star o) { return true; }

    @Override
    public boolean delete(Star o) { return stars.remove(o); }

    @Override
    public Star findById(int id) {
        for (Star s : stars) {
            if (s.getId() == id) return s;
        }
        return null;
    }
}