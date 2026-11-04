package fr.efrei.residence;

public class TarifStudio implements TarifLogement {

    public static final double PRIX_M2 = 14.0;
    public static final double LOYER_MINIMUM = 350.0;
    public static final double CHARGES = 35.0;

    @Override
    public double calculerLoyerDeBase(Logement logement) {
        return Math.max(logement.getSurface() * PRIX_M2, LOYER_MINIMUM);
    }

    @Override
    public double getCharges() {
        return CHARGES;
    }
}
