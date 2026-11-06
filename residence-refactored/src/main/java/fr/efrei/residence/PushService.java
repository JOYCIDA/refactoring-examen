package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

/**
 * Envoi de notifications vers l'application mobile.
 */
public class PushService {

    private final List<String[]> journal = new ArrayList<String[]>();

    public void envoyerPush(String destinataire, String sujet) {
        journal.add(new String[]{destinataire, sujet});
        System.out.println("[PUSH] -> " + destinataire + " : " + sujet);
    }

    public List<String[]> getJournal() { return journal; }
}
