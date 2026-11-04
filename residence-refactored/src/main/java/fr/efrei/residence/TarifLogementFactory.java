package fr.efrei.residence;

public class TarifLogementFactory {

    public static TarifLogement creer(TypeLogement type) {
        return switch (type) {
            case STUDIO -> new TarifStudio();
            case T1 -> new TarifT1();
            case T2 -> new TarifT2();
            case COLOCATION -> new TarifColocation();
        };
    }
}
