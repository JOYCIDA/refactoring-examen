package fr.efrei.residence;

public class DecorateurMeuble extends DecorateurLoyer {

    public static final double COEFF_MEUBLE = 1.15;

    public DecorateurMeuble(CalculLoyer composant) {
        super(composant);
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() * COEFF_MEUBLE;
    }
}
