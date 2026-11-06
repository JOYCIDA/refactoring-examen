package fr.efrei.residence;

public class CanalSms implements CanalNotification {

    private final SmsService service;

    public CanalSms(SmsService service) {
        this.service = service;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        service.envoyerSms(etudiant.getTelephone(), sujet + " - " + message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double montantDu) {
        service.envoyerSms(etudiant.getTelephone(),
                "Campus Vert : " + Utils.euros(montantDu) + " impayés sur " + idBail);
    }
}
