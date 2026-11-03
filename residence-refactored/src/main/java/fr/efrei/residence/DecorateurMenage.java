package fr.efrei.residence;

public class DecorateurMenage extends DecorateurLoyer {

    public static final double COEFF_STANDARD = 1.08;
    public static final double COEFF_COLOCATION = 1.04;

    private final boolean colocation;

    public DecorateurMenage(CalculLoyer composant, boolean colocation) {
        super(composant);
        this.colocation = colocation;
    }

    @Override
    public double getLoyer() {
        return composant.getLoyer() * (colocation ? COEFF_COLOCATION : COEFF_STANDARD);
    }
}
