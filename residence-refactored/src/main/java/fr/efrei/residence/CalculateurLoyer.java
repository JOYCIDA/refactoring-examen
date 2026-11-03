package fr.efrei.residence;

public class CalculateurLoyer {

    public static final double PRIX_PARKING = 45.0;
    public static final double PRIX_LAVERIE = 12.0;
    public static final double PRIX_FIBRE = 20.0;
    public static final double PRIX_ASSURANCE = 9.0;
    public static final double COEFF_MENAGE_STANDARD = 1.08;
    public static final double COEFF_MENAGE_COLOC = 1.04;

    public static final double COEFF_MEUBLE = 1.15;
    public static final double COEFF_RDC = 0.95;

    public static final double CHARGES_STANDARD = 35.0;
    public static final double CHARGES_COLOCATION = 25.0;

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, boolean parking, boolean menage,
                                boolean laverie, boolean fibre, boolean assurance) {
        // loyer de base selon le type
        double loyer;
        if (l.getType() == TypeLogement.STUDIO) {
            loyer = l.getSurface() * 14;
            if (loyer < 350) loyer = 350;
        } else if (l.getType() == TypeLogement.T1) {
            loyer = l.getSurface() * 13 + 50;
        } else if (l.getType() == TypeLogement.T2) {
            loyer = l.getSurface() * 12 + 100;
        } else if (l.getType() == TypeLogement.COLOCATION) {
            loyer = l.getSurface() * 11 / l.getNombrePlaces();
        } else {
            throw new IllegalArgumentException("Type de logement inconnu : " + l.getType());
        }

        if (l.isMeuble()) loyer = loyer * COEFF_MEUBLE;
        if (l.getEtage() == 0) loyer = loyer * COEFF_RDC;

        // options
        if (menage) {
            if (l.getType() == TypeLogement.COLOCATION) {
                loyer = loyer * COEFF_MENAGE_COLOC; // parties communes déjà nettoyées
            } else {
                loyer = loyer * COEFF_MENAGE_STANDARD;
            }
        }
        if (parking) loyer = loyer + PRIX_PARKING;
        if (laverie) loyer = loyer + PRIX_LAVERIE;
        if (fibre && l.getType() != TypeLogement.COLOCATION) loyer = loyer + PRIX_FIBRE; // fibre incluse en coloc
        if (assurance) loyer = loyer + PRIX_ASSURANCE;

        // remise selon le profil déléguée à la stratégie
        ProfilStrategy strategy = ProfilStrategyFactory.creer(profil);
        loyer = strategy.appliquerRemise(loyer, echelon);

        return Math.round(loyer * 100.0) / 100.0;
    }

    public double calculerCharges(Logement l) {
        if (l.getType() == TypeLogement.COLOCATION) return CHARGES_COLOCATION;
        return CHARGES_STANDARD;
    }
}
