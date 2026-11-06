package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

/**
 * Point d'entrée de l'application : délègue chaque cas d'usage au service qui en est responsable.
 */
public class GestionResidence {

    private final InscriptionService inscription;
    private final AttributionService attribution;
    private final CycleVieBailService cycleVie;
    private final RelanceService relance;
    private final RapportService rapport;
    private final LogementFactory fabriqueLogement;

    public GestionResidence(InscriptionService inscription, AttributionService attribution,
                            CycleVieBailService cycleVie, RelanceService relance, RapportService rapport,
                            LogementFactory fabriqueLogement) {
        this.inscription = inscription;
        this.attribution = attribution;
        this.cycleVie = cycleVie;
        this.relance = relance;
        this.rapport = rapport;
        this.fabriqueLogement = fabriqueLogement;
    }

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                                     String profil, int echelonBourse, double ressources, boolean pmr,
                                     Canal canalPrefere) {
        return inscription.inscrire(nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr,
                canalPrefere);
    }

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                                     Profil profil, int echelonBourse, double ressources, boolean pmr,
                                     Canal canalPrefere) {
        return inscription.inscrire(nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr,
                canalPrefere);
    }

    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        if (Residence.trouver(numero) != null) {
            System.out.println("Logement " + numero + " existe déjà");
            return null;
        }
        Logement l = fabriqueLogement.creer(type, numero, surface, etage, meuble, param);
        Residence.logements.add(l);
        System.out.println("Logement ajouté : " + numero + " (" + type + ")");
        return l;
    }

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, Set<Option> options, LocalDate dateSignature) {
        return attribution.attribuer(e, typeSouhaite, options, dateSignature);
    }

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, boolean parking, boolean menage, boolean laverie,
                                  boolean fibre, boolean assurance, LocalDate dateSignature) {
        return attribution.attribuer(e, typeSouhaite, parking, menage, laverie, fibre, assurance, dateSignature);
    }

    public boolean entrerDansLesLieux(String idBail, LocalDate date) {
        return cycleVie.entrerDansLesLieux(idBail, date);
    }

    public boolean payerLoyer(String idBail, String mois, LocalDate datePaiement, double montant) {
        return cycleVie.payerLoyer(idBail, mois, datePaiement, montant);
    }

    public boolean donnerPreavis(String idBail, LocalDate date) {
        return cycleVie.donnerPreavis(idBail, date);
    }

    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return cycleVie.sortir(idBail, date, retenuesDegats);
    }

    public boolean annulerBail(String idBail) {
        return cycleVie.annulerBail(idBail);
    }

    public void relancerImpayes() {
        relance.relancerImpayes();
    }

    public void afficherRapport() {
        rapport.afficherRapport();
    }

    public String genererRapport() {
        return rapport.genererRapport();
    }
}
