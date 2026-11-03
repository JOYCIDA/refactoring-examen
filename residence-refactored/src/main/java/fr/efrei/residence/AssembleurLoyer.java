package fr.efrei.residence;

import java.util.Set;

public class AssembleurLoyer {

    public static CalculLoyer assembler(Logement logement, Set<Option> options) {
        CalculLoyer chaine = new LoyerBase(logement);

        if (logement.isMeuble()) {
            chaine = new DecorateurMeuble(chaine);
        }
        if (logement.getEtage() == 0) {
            chaine = new DecorateurRdc(chaine);
        }
        if (options != null) {
            // Ménage d'abord (pourcentage multiplicatif)
            if (options.contains(Option.MENAGE)) {
                chaine = new DecorateurMenage(chaine, logement.getType() == TypeLogement.COLOCATION);
            }
            // Options fixes ensuite (additives)
            if (options.contains(Option.PARKING)) {
                chaine = new DecorateurParking(chaine);
            }
            if (options.contains(Option.LAVERIE)) {
                chaine = new DecorateurLaverie(chaine);
            }
            if (options.contains(Option.FIBRE)) {
                chaine = new DecorateurFibre(chaine, logement.getType() == TypeLogement.COLOCATION);
            }
            if (options.contains(Option.ASSURANCE)) {
                chaine = new DecorateurAssurance(chaine);
            }
        }
        return chaine;
    }
}
