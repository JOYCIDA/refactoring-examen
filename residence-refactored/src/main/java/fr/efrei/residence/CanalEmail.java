package fr.efrei.residence;

public class CanalEmail implements CanalNotification {

    private final EmailService service;

    public CanalEmail(EmailService service) {
        this.service = service;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        service.envoyer(etudiant.getEmail(), sujet, message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double montantDu) {
        envoyer(etudiant, "RELANCE - loyer impayé", "Bonjour " + etudiant.getPrenom() + ", il reste "
                + Utils.euros(montantDu) + " à régler sur le bail " + idBail + ".");
    }
}
