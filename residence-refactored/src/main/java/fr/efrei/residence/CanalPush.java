package fr.efrei.residence;

public class CanalPush implements CanalNotification {

    private final PushService service;

    public CanalPush(PushService service) {
        this.service = service;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        service.envoyerPush(etudiant.getEmail(), sujet);
    }
}
