package com.example.lab7_devmobil.beans;

/**
 * Classe Star : Représente une entité Star (acteur/actrice) dans l'application.
 * Cette classe fait partie de la couche 'Model' (Beans) de notre architecture MVC.
 * Créée par Mohammed Amine L pour le Lab7.
 */
public class Star {
    private int id;           // Identifiant unique de la star
    private String name;      // Nom complet de l'acteur ou l'actrice
    private int img;          // ID de la ressource drawable (R.drawable.nom)
    private float rating;     // Note moyenne (RatingBar)
    private static int counter = 0; // Compteur statique pour l'auto-incrémentation

    public Star(String name, int img, float rating) {
        this.id = ++counter;
        this.name = name;
        this.img = img;
        this.rating = rating;
    }

    // Getters et Setters pour accéder et modifier les données
    public int getId() { return id; }
    public String getName() { return name; }
    public int getImg() { return img; }
    public float getRating() { return rating; }

    public void setName(String name) { this.name = name; }
    public void setImg(int img) { this.img = img; }
    public void setRating(float rating) { this.rating = rating; }
}