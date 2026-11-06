package fr.efrei.residence;

/**
 * Relance les étudiants qui ont des impayés.
 */
public class RelanceService {

    private final BailRepository baux;
    private final ServiceNotification notifications;

    public RelanceService(BailRepository baux, ServiceNotification notifications) {
        this.baux = baux;
        this.notifications = notifications;
    }

    public void relancerImpayes() {
        System.out.println("--- Relance des impayés");
        for (Bail b : baux.tousLesBaux()) {
            if (b.soldeDu <= 0) continue;
            if (b.getStatut() == StatutBail.ANNULE) continue;
            notifications.relancer(b.getEtudiant(), b.getId(), b.soldeDu);
        }
    }
}
