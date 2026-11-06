package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class EmailService {

    public static final String SMTP_HOST = "smtp.campusvert.fr";
    public static final String EXPEDITEUR = "gestion@campusvert.fr";

    // garde une trace des emails envoyés (demandé par l'administration)
    private final List<String[]> journal = new ArrayList<String[]>();

    public boolean envoyer(String destinataire, String sujet, String corps) {
        if (!Utils.emailValide(destinataire)) {
            return false;
        }
        System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
        System.out.println("        " + corps);
        journal.add(new String[]{destinataire, sujet, corps});
        return true;
    }

    public List<String[]> getJournal() { return journal; }
}
