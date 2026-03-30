package com.example.lab7_devmobil.dao;

import java.util.List;

/**
 * Interface IDao : Définit le contrat pour les opérations de base de données (CRUD).
 * Utilise la généricité pour être réutilisable avec n'importe quelle entité.
 * Créée par Mohammed Amine L pour le Lab7.
 * @param <T> Le type de l'objet métier.
 */
public interface IDao<T> {
    boolean create(T o);     // Créer un nouvel objet
    boolean update(T o);     // Mettre à jour un objet existant
    boolean delete(T o);     // Supprimer un objet
    T findById(int id);      // Trouver un objet par son ID
    List<T> findAll();       // Récupérer tous les objets
}