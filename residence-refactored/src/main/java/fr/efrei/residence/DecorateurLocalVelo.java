package fr.efrei.residence;

public class DecorateurLocalVelo extends DecorateurLoyer {

    public static final double PRIX = 6.0;

    public DecorateurLocalVelo(CalculLoyer composant) {
        super(composant);
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() + PRIX;
    }
}
