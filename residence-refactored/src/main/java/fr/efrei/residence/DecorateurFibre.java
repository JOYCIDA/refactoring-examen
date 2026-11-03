package fr.efrei.residence;

public class DecorateurFibre extends DecorateurLoyer {

    public static final double PRIX = 20.0;

    private final boolean colocation;

    public DecorateurFibre(CalculLoyer composant, boolean colocation) {
        super(composant);
        this.colocation = colocation;
    }

    @Override
    public double getLoyer() {
        if (colocation) {
            return composant.getLoyer();
        }
        return composant.getLoyer() + PRIX;
    }
}
