package fr.efrei.residence;

public class TarifColocation implements TarifLogement {

    public static final double PRIX_M2 = 11.0;
    public static final double CHARGES = 25.0;

    /** Loyer par colocataire. */
    @Override
    public double calculerLoyerDeBase(Logement logement) {
        return logement.getSurface() * PRIX_M2 / logement.getNombrePlaces();
    }

    @Override
    public double getCharges() {
        return CHARGES;
    }
}
