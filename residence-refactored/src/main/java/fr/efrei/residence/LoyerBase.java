package fr.efrei.residence;

public class LoyerBase implements CalculLoyer {

    private final Logement logement;

    public LoyerBase(Logement logement) {
        this.logement = logement;
    }

    @Override
    public double getLoyer() {
        return TarifLogementFactory.creer(logement.getType()).calculerLoyerDeBase(logement);
    }
}
