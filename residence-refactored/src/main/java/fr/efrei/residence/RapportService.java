package fr.efrei.residence;

import java.util.List;

/**
 * Produit le rapport d'occupation et de trésorerie.
 */
public class RapportService {

    private final BailRepository baux;
    private final SmsService smsService;
    private final CourrierService courrierService;

    public RapportService(BailRepository baux, SmsService smsService, CourrierService courrierService) {
        this.baux = baux;
        this.smsService = smsService;
        this.courrierService = courrierService;
    }

    public void afficherRapport() {
        System.out.println();
        System.out.println(genererRapport());
    }

    public String genererRapport() {
        String r = "";
        r += "=============== RAPPORT " + Residence.NOM + " ===============" + "\n";
        int nbStudios = 0, nbStudiosOccupes = 0;
        int nbT1 = 0, nbT1Occupes = 0;
        int nbT2 = 0, nbT2Occupes = 0;
        int nbPlacesColoc = 0, nbPlacesColocOccupees = 0;
        double surfaceBalcons = 0;
        for (Logement l : Residence.logements) {
            if (l.getType() == TypeLogement.STUDIO) {
                nbStudios++;
                if (!l.estLibre()) nbStudiosOccupes++;
            } else if (l.getType() == TypeLogement.T1) {
                nbT1++;
                if (!l.estLibre()) nbT1Occupes++;
            } else if (l.getType() == TypeLogement.T2) {
                nbT2++;
                if (!l.estLibre()) nbT2Occupes++;
            } else if (l.getType() == TypeLogement.COLOCATION) {
                nbPlacesColoc += l.getNombrePlaces();
                nbPlacesColocOccupees += l.getNombreOccupants();
            }
            try {
                surfaceBalcons += l.getSurfaceBalcon();
            } catch (UnsupportedOperationException ex) {
            }
        }
        r += "Studios      : " + nbStudiosOccupes + "/" + nbStudios + "\n";
        r += "T1           : " + nbT1Occupes + "/" + nbT1 + "\n";
        r += "T2           : " + nbT2Occupes + "/" + nbT2 + "\n";
        r += "Colocation   : " + nbPlacesColocOccupees + "/" + nbPlacesColoc + " places" + "\n";
        int total = nbStudios + nbT1 + nbT2 + nbPlacesColoc;
        int occupes = nbStudiosOccupes + nbT1Occupes + nbT2Occupes + nbPlacesColocOccupees;
        r += "Occupation   : " + Math.round(occupes * 100.0 / total) + " %" + "\n";
        r += "Balcons      : " + surfaceBalcons + " m²" + "\n";

        double loyersMensuels = 0;
        double impayes = 0;
        double cautions = 0;
        List<Bail> liste = baux.tousLesBaux();
        for (Bail b : liste) {
            if (b.getStatut() == StatutBail.ACTIF || b.getStatut() == StatutBail.PREAVIS) {
                loyersMensuels += b.getLoyerChargesComprises();
                cautions += b.getCaution();
            }
            if (b.soldeDu > 0) impayes += b.soldeDu;
        }
        r += "Loyers CC    : " + Utils.euros(Utils.arrondir(loyersMensuels)) + " / mois" + "\n";
        r += "Cautions     : " + Utils.euros(Utils.arrondir(cautions)) + "\n";
        r += "Impayés      : " + Utils.euros(Utils.arrondir(impayes)) + "\n";
        r += "SMS envoyés  : " + smsService.getCompteurSms() + "\n";
        r += "Timbres      : " + Utils.euros(Utils.arrondir(courrierService.getTotalAffranchissement())) + "\n";
        r += "============================================================";
        return r;
    }
}
