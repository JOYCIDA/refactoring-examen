package fr.efrei.residence;

/**
 * Valide les demandes d'inscription et enregistre les étudiants.
 */
public class InscriptionService {

    private final EtudiantRepository etudiants;
    private int compteurEtudiant = 0;

    public InscriptionService(EtudiantRepository etudiants) {
        this.etudiants = etudiants;
    }

    public Etudiant inscrire(String nom, String prenom, String email, String telephone, String adresse,
                             String profilStr, int echelonBourse, double ressources, boolean pmr,
                             Canal canalPrefere) {
        Profil profil;
        try {
            profil = profilStr == null ? null : Profil.valueOf(profilStr);
        } catch (IllegalArgumentException ex) {
            System.out.println("Inscription refusée : profil inconnu " + profilStr);
            return null;
        }
        if (profil == null) {
            System.out.println("Inscription refusée : profil inconnu " + profilStr);
            return null;
        }
        return inscrire(nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr, canalPrefere);
    }

    public Etudiant inscrire(String nom, String prenom, String email, String telephone, String adresse,
                             Profil profil, int echelonBourse, double ressources, boolean pmr,
                             Canal canalPrefere) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("Inscription refusée : nom obligatoire");
            return null;
        }
        if (!Utils.emailValide(email)) {
            System.out.println("Inscription refusée : email invalide (" + email + ")");
            return null;
        }
        if (etudiants.trouverEtudiantParEmail(email) != null) {
            System.out.println("Inscription refusée : " + email + " déjà inscrit");
            return null;
        }
        if (profil == null) {
            System.out.println("Inscription refusée : profil inconnu null");
            return null;
        }
        if (profil == Profil.BOURSIER && (echelonBourse < 0 || echelonBourse > 7)) {
            System.out.println("Inscription refusée : échelon de bourse invalide");
            return null;
        }
        if (profil == Profil.INTERNATIONAL && (telephone == null || !telephone.startsWith("+"))) {
            System.out.println("Inscription refusée : numéro international (+XX) obligatoire");
            return null;
        }
        if (canalPrefere == Canal.SMS && telephone == null) {
            System.out.println("Inscription refusée : téléphone obligatoire pour les SMS");
            return null;
        }
        if (canalPrefere == Canal.COURRIER && (adresse == null || adresse.isEmpty())) {
            System.out.println("Inscription refusée : adresse obligatoire pour le courrier");
            return null;
        }
        compteurEtudiant++;
        Etudiant e = new Etudiant("ETU-" + compteurEtudiant, nom, prenom, email, telephone, adresse, profil,
                echelonBourse, ressources, pmr, canalPrefere);
        etudiants.sauvegarderEtudiant(e);
        System.out.println("Étudiant inscrit : " + e.getId() + " " + e.getNomComplet() + " (" + profil + ")");
        return e;
    }
}
