package fr.efrei.residence;

public class TarifT2 implements TarifLogement {

    public static final double PRIX_M2 = 12.0;
    public static final double SUPPLEMENT = 100.0;
    public static final double CHARGES = 35.0;

    @Override
    public double calculerLoyerDeBase(Logement logement) {
        return logement.getSurface() * PRIX_M2 + SUPPLEMENT;
    }

    @Override
    public double getCharges() {
        return CHARGES;
    }
}
