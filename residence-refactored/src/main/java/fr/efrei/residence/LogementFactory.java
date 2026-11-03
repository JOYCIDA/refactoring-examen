package fr.efrei.residence;

/**
 * Seul endroit qui sait créer un logement à partir de son type.
 * param = nombre de places pour une colocation, surface du balcon pour un T2.
 */
public class LogementFactory {

    public Logement creer(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        return switch (type) {
            case STUDIO -> new Studio(numero, surface, etage, meuble);
            case T1 -> new T1(numero, surface, etage, meuble);
            case T2 -> new T2(numero, surface, etage, meuble, param);
            case COLOCATION -> new Colocation(numero, surface, etage, meuble, Math.max(param, Colocation.PLACES_MIN));
        };
    }
}
