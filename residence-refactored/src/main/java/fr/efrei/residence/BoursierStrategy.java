package fr.efrei.residence;

public class BoursierStrategy implements ProfilStrategy {

    public static final double TAUX_PAR_ECHELON = 0.05;
    public static final double PLAFOND_REMISE = 0.30;
    public static final int DUREE_PREAVIS = 15;

    @Override
    public double appliquerRemise(double loyer, int echelon) {
        double taux = echelon * TAUX_PAR_ECHELON;
        if (taux > PLAFOND_REMISE) {
            taux = PLAFOND_REMISE;
        }
        return loyer * (1 - taux);
    }

    @Override
    public boolean estSolvable(double ressources, double totalChargesComprises) {
        return true; // Garantie Visale
    }

    @Override
    public double calculerCaution(double loyerHorsCharges) {
        return 0.0;
    }

    @Override
    public double getFraisDossier() {
        return 0.0;
    }

    @Override
    public int getDureePreavis() {
        return DUREE_PREAVIS;
    }

    @Override
    public double calculerPenaliteRetard(double loyerChargesComprises) {
        return 0.0; // Exonéré de pénalité de retard
    }
}
