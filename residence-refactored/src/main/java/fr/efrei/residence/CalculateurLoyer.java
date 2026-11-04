package fr.efrei.residence;

import java.util.HashSet;
import java.util.Set;

public class CalculateurLoyer {

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite (via Decorator et Strategy).
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, Set<Option> options) {
        CalculLoyer chaine = AssembleurLoyer.assembler(l, options);
        double loyer = chaine.getLoyer();

        ProfilStrategy strategy = ProfilStrategyFactory.creer(profil);
        loyer = strategy.appliquerRemise(loyer, echelon);

        return Utils.arrondir(loyer);
    }

    /**
     * Surcharge pour compatibilité avec l'ancien passage de booléens.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, boolean parking, boolean menage,
                                boolean laverie, boolean fibre, boolean assurance) {
        Set<Option> options = new HashSet<>();
        if (parking) options.add(Option.PARKING);
        if (menage) options.add(Option.MENAGE);
        if (laverie) options.add(Option.LAVERIE);
        if (fibre) options.add(Option.FIBRE);
        if (assurance) options.add(Option.ASSURANCE);
        return calculerLoyer(l, profil, echelon, options);
    }

    public double calculerCharges(Logement l) {
        return TarifLogementFactory.creer(l.getType()).getCharges();
    }
}
