package fr.efrei.residence;

public class AlternantStrategy implements ProfilStrategy {

    public static final double TAUX_REMISE = 0.10;
    public static final double RATIO_SOLVABILITE = 2.0;
    public static final double FRAIS_DOSSIER = 100.0;
    public static final int DUREE_PREAVIS = 15;

    @Override
    public double appliquerRemise(double loyer, int echelon) {
        return loyer * (1 - TAUX_REMISE);
    }

    @Override
    public boolean estSolvable(double ressources, double totalChargesComprises) {
        return ressources >= totalChargesComprises * RATIO_SOLVABILITE;
    }

    @Override
    public double calculerCaution(double loyerHorsCharges) {
        return loyerHorsCharges;
    }

    @Override
    public double getFraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public int getDureePreavis() {
        return DUREE_PREAVIS;
    }

    @Override
    public double calculerPenaliteRetard(double loyerChargesComprises) {
        double penalite = loyerChargesComprises * 0.05;
        if (penalite < 10.0) {
            penalite = 10.0;
        }
        return Utils.arrondir(penalite);
    }
}
