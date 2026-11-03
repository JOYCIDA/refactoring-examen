package fr.efrei.residence;

public class LoyerBase implements CalculLoyer {

    private final Logement logement;

    public LoyerBase(Logement logement) {
        this.logement = logement;
    }

    @Override
    public double getLoyer() {
        if (logement.getType() == TypeLogement.STUDIO) {
            double loyer = logement.getSurface() * 14.0;
            return Math.max(loyer, 350.0);
        } else if (logement.getType() == TypeLogement.T1) {
            return logement.getSurface() * 13.0 + 50.0;
        } else if (logement.getType() == TypeLogement.T2) {
            return logement.getSurface() * 12.0 + 100.0;
        } else if (logement.getType() == TypeLogement.COLOCATION) {
            return (logement.getSurface() * 11.0) / logement.getNombrePlaces();
        }
        throw new IllegalArgumentException("Type de logement inconnu : " + logement.getType());
    }
}
