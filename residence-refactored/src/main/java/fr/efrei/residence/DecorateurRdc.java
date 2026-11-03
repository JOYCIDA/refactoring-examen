package fr.efrei.residence;

public class DecorateurRdc extends DecorateurLoyer {

    public static final double COEFF_RDC = 0.95;

    public DecorateurRdc(CalculLoyer composant) {
        super(composant);
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() * COEFF_RDC;
    }
}
