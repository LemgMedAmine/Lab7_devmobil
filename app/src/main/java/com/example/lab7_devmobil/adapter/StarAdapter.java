package com.example.lab7_devmobil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab7_devmobil.R;
import com.example.lab7_devmobil.beans.Star;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * [PROJET LAB7 - DEV MOBIL]
 * GESTIONNAIRE D'AFFICHAGE PERSONNALISÉ (ADAPTER)
 * 
 * Cet adaptateur assure la liaison entre les données des célébrités et le RecyclerView.
 * Il intègre des fonctionnalités avancées de filtrage et de tri dynamique.
 * 
 * Développé avec soin par : Mohammed Amine L
 */
public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private final List<Star> starsSource;   // Liste dynamique pour l'affichage
    private final List<Star> starsBackup;   // Copie de sauvegarde pour la recherche
    private final Context hostContext;      // Contexte de l'activité parente

    public StarAdapter(Context context, List<Star> starData) {
        this.hostContext = context;
        this.starsSource = starData;
        this.starsBackup = new ArrayList<>(starData);
        reordonnerParNote(); // Organisation initiale par classement
    }

    /**
     * Méthode interne pour classer les stars de la mieux notée à la moins notée.
     */
    private void reordonnerParNote() {
        Collections.sort(starsSource, (a, b) -> Float.compare(b.getRating(), a.getRating()));
        Collections.sort(starsBackup, (a, b) -> Float.compare(b.getRating(), a.getRating()));
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Injection du design de l'item (star_item.xml)
        View layout = LayoutInflater.from(hostContext).inflate(R.layout.star_item, parent, false);
        return new StarViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        final Star currentStar = starsSource.get(position);
        
        // Assignation des textes et valeurs
        holder.labelName.setText(currentStar.getName());
        holder.barRating.setRating(currentStar.getRating());
        
        // Utilisation de la bibliothèque Glide pour le rendu visuel
        Glide.with(hostContext)
                .load(currentStar.getImg())
                .centerCrop()
                .placeholder(R.drawable.star)
                .into(holder.iconPhoto);

        // Déclenchement de l'action de modification au clic sur la ligne
        holder.itemView.setOnClickListener(view -> ouvrirInterfaceNotation(currentStar));
    }

    /**
     * Affiche un dialogue interactif pour ajuster le score de la star sélectionnée.
     */
    private void ouvrirInterfaceNotation(Star targetStar) {
        View popupContent = LayoutInflater.from(hostContext).inflate(R.layout.dialog_rating, null);
        RatingBar inputStars = popupContent.findViewById(R.id.dialog_rating_bar);
        inputStars.setRating(targetStar.getRating());

        new AlertDialog.Builder(hostContext)
                .setTitle("Mise à jour : " + targetStar.getName())
                .setMessage("Attribuez une nouvelle note à cet artiste :")
                .setView(popupContent)
                .setPositiveButton("VALIDER", (dialog, btn) -> {
                    targetStar.setRating(inputStars.getRating());
                    reordonnerParNote();    // On recalcule le classement
                    notifyDataSetChanged(); // Rafraîchissement global pour le tri
                })
                .setNegativeButton("RETOUR", null)
                .create()
                .show();
    }

    @Override
    public int getItemCount() {
        return starsSource.size();
    }

    // --- Moteur de recherche interne ---
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence key) {
                List<Star> matches = new ArrayList<>();
                if (key == null || key.length() == 0) {
                    matches.addAll(starsBackup);
                } else {
                    String query = key.toString().toLowerCase().trim();
                    for (Star s : starsBackup) {
                        if (s.getName().toLowerCase().contains(query)) {
                            matches.add(s);
                        }
                    }
                }
                FilterResults res = new FilterResults();
                res.values = matches;
                return res;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence key, FilterResults results) {
                starsSource.clear();
                starsSource.addAll((List<Star>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    /**
     * Composant interne pour le recyclage des vues.
     */
    static class StarViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconPhoto;
        final TextView labelName;
        final RatingBar barRating;

        StarViewHolder(View root) {
            super(root);
            iconPhoto = root.findViewById(R.id.imgStar);
            labelName = root.findViewById(R.id.tvName);
            barRating = root.findViewById(R.id.rating);
        }
    }
}
