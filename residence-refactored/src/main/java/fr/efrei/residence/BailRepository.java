package fr.efrei.residence;

import java.util.List;

/**
 * Stockage des baux.
 */
public interface BailRepository {

    void sauvegarderBail(Bail bail);

    void mettreAJourBail(Bail bail);

    Bail trouverBail(String id);

    List<Bail> tousLesBaux();
}
