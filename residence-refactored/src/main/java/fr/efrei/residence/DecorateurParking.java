package fr.efrei.residence;

public class DecorateurParking extends DecorateurLoyer {

    public static final double PRIX = 45.0;

    public DecorateurParking(CalculLoyer composant) {
        super(composant);
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() + PRIX;
    }
}
