package fr.efrei.residence;

/**
 * Un moyen d'envoyer un message à un étudiant.
 */
public interface CanalNotification {

    void envoyer(Etudiant etudiant, String sujet, String message);

    /**
     * Relance d'impayé : par défaut un message classique ; chaque canal peut avoir son propre texte.
     */
    default void relancer(Etudiant etudiant, String idBail, double montantDu) {
        envoyer(etudiant, "RELANCE - loyer impayé",
                "Il reste " + Utils.euros(montantDu) + " à régler sur le bail " + idBail + ".");
    }
}
