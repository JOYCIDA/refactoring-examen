package fr.efrei.residence;

import fr.efrei.residence.contrat.DemandeAttribution;
import fr.efrei.residence.contrat.DemandeDevis;
import fr.efrei.residence.contrat.DemandeInscription;
import fr.efrei.residence.contrat.DemandeLogement;
import fr.efrei.residence.contrat.EtatBail;
import fr.efrei.residence.contrat.EtatLogement;
import fr.efrei.residence.contrat.Notification;
import fr.efrei.residence.contrat.SystemeResidence;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Branche le code refactoré sur les tests de contrat.
 */
public class NouveauSystemeAdapter implements SystemeResidence {

    private GrapheDeTest graphe;

    @Override
    public void reinitialiser() {
        Residence.initialiser();
        graphe = new GrapheDeTest();
    }

    @Override
    public Optional<String> inscrire(DemandeInscription d) {
        Profil profil = d.profil() == null ? null : Profil.valueOf(d.profil().name());
        Etudiant e = graphe.gestion.inscrireEtudiant(d.nom(), d.prenom(), d.email(), d.telephone(), d.adresse(),
                profil, d.echelonBourse(), d.ressourcesMensuelles(), d.pmr(), Canal.valueOf(d.canalPrefere().name()));
        return Optional.ofNullable(e).map(Etudiant::getId);
    }

    @Override
    public boolean ajouterLogement(DemandeLogement d) {
        int param = d.type() == fr.efrei.residence.contrat.TypeLogement.COLOCATION ? d.places() : d.surfaceBalcon();
        return graphe.gestion.ajouterLogement(TypeLogement.valueOf(d.type().name()), d.numero(), d.surface(), d.etage(), d.meuble(), param) != null;
    }

    @Override
    public Optional<EtatLogement> logement(String numero) {
        Logement l = Residence.trouver(numero);
        if (l == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatLogement(l.getNumero(), fr.efrei.residence.contrat.TypeLogement.valueOf(l.getType().name()), l.getNombrePlaces(),
                l.getNombreOccupants(), l.estAccessiblePMR()));
    }

    @Override
    public OptionalDouble devisLoyer(DemandeDevis d) {
        Logement l = Residence.trouver(d.numeroLogement());
        if (l == null) {
            return OptionalDouble.empty();
        }
        Profil profil = d.profil() == null ? null : Profil.valueOf(d.profil().name());
        Set<Option> options = d.options().stream()
                .map(o -> Option.valueOf(o.name()))
                .collect(Collectors.toSet());
        return OptionalDouble.of(new CalculateurLoyer().calculerLoyer(l, profil, d.echelonBourse(), options));
    }

    @Override
    public Optional<String> attribuer(DemandeAttribution d) {
        Etudiant e = etudiant(d.refEtudiant());
        if (e == null) {
            return Optional.empty();
        }
        Set<Option> options = d.options().stream()
                .map(o -> Option.valueOf(o.name()))
                .collect(Collectors.toSet());
        Bail b = graphe.gestion.attribuerLogement(e, TypeLogement.valueOf(d.type().name()), options, d.dateSignature());
        return Optional.ofNullable(b).map(Bail::getId);
    }

    @Override
    public Optional<EtatBail> consulter(String idBail) {
        Bail b = graphe.base.trouverBail(idBail);
        if (b == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatBail(b.getId(), b.getLogement().getNumero(), fr.efrei.residence.contrat.StatutBail.valueOf(b.getStatut().name()),
                b.getLoyerHorsCharges(), b.getCharges(), b.getLoyerChargesComprises(), b.getCaution(),
                b.getFraisDossier(), b.getDateFin(), b.soldeDu, b.cautionRestituee));
    }

    @Override
    public boolean entrer(String idBail, LocalDate date) {
        return graphe.gestion.entrerDansLesLieux(idBail, date);
    }

    @Override
    public boolean payer(String idBail, YearMonth mois, LocalDate date, double montant) {
        return graphe.gestion.payerLoyer(idBail, mois.toString(), date, montant);
    }

    @Override
    public boolean donnerPreavis(String idBail, LocalDate date) {
        return graphe.gestion.donnerPreavis(idBail, date);
    }

    @Override
    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return graphe.gestion.sortir(idBail, date, retenuesDegats);
    }

    @Override
    public boolean annuler(String idBail) {
        return graphe.gestion.annulerBail(idBail);
    }

    @Override
    public Optional<String> quittance(String idBail, YearMonth mois) {
        return Optional.ofNullable(graphe.base.trouverBail(idBail)).map(b -> graphe.quittances.generer(b, mois.toString()));
    }

    @Override
    public void relancerImpayes() {
        graphe.gestion.relancerImpayes();
    }

    @Override
    public String rapport() {
        return graphe.gestion.genererRapport();
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> notifications = new ArrayList<>();
        for (String[] email : graphe.email.getJournal()) {
            notifications.add(new Notification(fr.efrei.residence.contrat.Canal.EMAIL, email[0], email[1]));
        }
        for (String[] sms : graphe.sms.getJournal()) {
            notifications.add(new Notification(fr.efrei.residence.contrat.Canal.SMS, sms[0], sms[1]));
        }
        for (String[] courrier : graphe.courrier.getJournal()) {
            notifications.add(new Notification(fr.efrei.residence.contrat.Canal.COURRIER, courrier[0], courrier[1]));
        }
        return notifications;
    }

    private Etudiant etudiant(String ref) {
        for (Etudiant e : graphe.base.tousLesEtudiants()) {
            if (e.getId().equals(ref)) return e;
        }
        return null;
    }
}
