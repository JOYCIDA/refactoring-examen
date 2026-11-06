package fr.efrei.residence;

import java.util.List;

/**
 * Stockage des étudiants.
 */
public interface EtudiantRepository {

    void sauvegarderEtudiant(Etudiant etudiant);

    Etudiant trouverEtudiantParEmail(String email);

    List<Etudiant> tousLesEtudiants();
}
