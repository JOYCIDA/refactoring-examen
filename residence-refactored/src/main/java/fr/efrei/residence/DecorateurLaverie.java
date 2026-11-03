package fr.efrei.residence;

public class DecorateurLaverie extends DecorateurLoyer {

    public static final double PRIX = 12.0;

    public DecorateurLaverie(CalculLoyer composant) {
        super(composant);
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() + PRIX;
    }
}
