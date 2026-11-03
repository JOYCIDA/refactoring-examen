package fr.efrei.residence;

public class DecorateurAssurance extends DecorateurLoyer {

    public static final double PRIX = 9.0;

    public DecorateurAssurance(CalculLoyer composant) {
        super(composant);
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() + PRIX;
    }
}
