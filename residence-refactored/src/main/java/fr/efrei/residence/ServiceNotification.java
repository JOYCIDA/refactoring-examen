package fr.efrei.residence;

/**
 * Envoie les messages aux étudiants sur leur canal préféré.
 */
public class ServiceNotification {

    private final NotificationFactory fabrique;

    public ServiceNotification(NotificationFactory fabrique) {
        this.fabrique = fabrique;
    }

    public void notifier(Etudiant e, String sujet, String message) {
        Canal canal = e.getCanalPrefere();
        fabrique.creer(canal).envoyer(e, sujet, message);
        // les internationaux reçoivent toujours un email en plus (demande de l'administration)
        if (e.getProfil() == Profil.INTERNATIONAL && canal != Canal.EMAIL) {
            fabrique.creer(Canal.EMAIL).envoyer(e, "[EN] " + sujet, message);
        }
    }

    public void relancer(Etudiant e, String idBail, double montantDu) {
        fabrique.creer(e.getCanalPrefere()).relancer(e, idBail, montantDu);
    }
}
