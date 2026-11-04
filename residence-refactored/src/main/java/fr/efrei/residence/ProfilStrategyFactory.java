package fr.efrei.residence;

public class ProfilStrategyFactory {

    public static ProfilStrategy creer(Profil profil) {
        if (profil == null) {
            return new StandardStrategy();
        }
        return switch (profil) {
            case STANDARD -> new StandardStrategy();
            case BOURSIER -> new BoursierStrategy();
            case ALTERNANT -> new AlternantStrategy();
            case INTERNATIONAL -> new InternationalStrategy();
            case SPORTIF -> new SportifStrategy();
        };
    }
}
