package fr.efrei.residence;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Choisit un logement pour un étudiant et signe le bail.
 */
public class AttributionService {

    private final BailRepository baux;
    private final CalculateurLoyer calculateur;
    private final ServiceNotification notifications;
    private int compteurBail = 0;

    public AttributionService(BailRepository baux, CalculateurLoyer calculateur, ServiceNotification notifications) {
        this.baux = baux;
        this.calculateur = calculateur;
        this.notifications = notifications;
    }

    public Bail attribuer(Etudiant e, TypeLogement typeSouhaite, Set<Option> options, LocalDate dateSignature) {
        System.out.println("--- Attribution d'un " + typeSouhaite + " à " + e.getNomComplet());
        Logement choisi = null;
        double loyer = 0;
        double charges = 0;
        for (Logement l : Residence.logements) {
            if (l.getType() != typeSouhaite) continue;
            if (!l.estLibre()) continue;
            if (e.isPmr() && !l.estAccessiblePMR()) continue;

            double loyerCandidat = calculateur.calculerLoyer(l, e.getProfil(), e.getEchelonBourse(), options);
            double chargesCandidat = calculateur.calculerCharges(l);
            double total = loyerCandidat + chargesCandidat;

            // solvabilité vérifiée via la stratégie de profil
            if (!e.getStrategy().estSolvable(e.getRessourcesMensuelles(), total)) {
                System.out.println("  " + l.getNumero() + " : ressources insuffisantes (" + Utils.euros(total) + "/mois)");
                continue;
            }
            choisi = l;
            loyer = loyerCandidat;
            charges = chargesCandidat;
            break;
        }
        if (choisi == null) {
            System.out.println("  Aucun logement disponible pour " + e.getNomComplet());
            return null;
        }

        choisi.loger(e);

        compteurBail++;
        String id = "BAIL-" + String.format("%03d", compteurBail);
        Bail b = new Bail(id, e, choisi, dateSignature);
        b.setOptions(options);
        b.setLoyerHorsCharges(loyer);
        b.setCharges(charges);

        // caution et frais de dossier via la stratégie de profil
        b.setCaution(Utils.arrondir(e.getStrategy().calculerCaution(loyer)));
        b.setFraisDossier(e.getStrategy().getFraisDossier());

        baux.sauvegarderBail(b);
        System.out.println("  Bail " + id + " signé : " + choisi.getNumero() + ", " + Utils.euros(b.getLoyerChargesComprises())
                + " CC/mois, caution " + Utils.euros(b.getCaution()) + ", frais de dossier " + Utils.euros(b.getFraisDossier()));
        notifications.notifier(e, "Votre bail " + id,
                "Bienvenue à la " + Residence.NOM + " ! Logement " + choisi.getNumero() + ", loyer "
                        + Utils.euros(b.getLoyerChargesComprises()) + " charges comprises.");
        return b;
    }

    public Bail attribuer(Etudiant e, TypeLogement typeSouhaite, boolean parking, boolean menage, boolean laverie,
                          boolean fibre, boolean assurance, LocalDate dateSignature) {
        Set<Option> options = new HashSet<>();
        if (parking) options.add(Option.PARKING);
        if (menage) options.add(Option.MENAGE);
        if (laverie) options.add(Option.LAVERIE);
        if (fibre) options.add(Option.FIBRE);
        if (assurance) options.add(Option.ASSURANCE);
        return attribuer(e, typeSouhaite, options, dateSignature);
    }
}
