package fr.efrei.residence;

public class CanalCourrier implements CanalNotification {

    private final CourrierService service;

    public CanalCourrier(CourrierService service) {
        this.service = service;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        service.imprimerEtPoster(etudiant.getNomComplet(), etudiant.getAdresse(), sujet, message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double montantDu) {
        envoyer(etudiant, "Relance loyer impayé",
                "Il reste " + Utils.euros(montantDu) + " à régler sur le bail " + idBail + ".");
    }
}
