package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class CourrierService {

    public static final double PRIX_TIMBRE = 1.29;
    private double totalAffranchissement = 0;
    private final List<String[]> journal = new ArrayList<String[]>();

    public void imprimerEtPoster(String nom, String adresse, String objet, String texte) {
        totalAffranchissement = totalAffranchissement + PRIX_TIMBRE;
        journal.add(new String[]{adresse, objet, texte});
        System.out.println("[COURRIER] " + nom + ", " + adresse + " | Objet : " + objet);
        System.out.println("        " + texte);
    }

    public double getTotalAffranchissement() { return totalAffranchissement; }
    public List<String[]> getJournal() { return journal; }
}
