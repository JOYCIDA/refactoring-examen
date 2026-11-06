package fr.efrei.residence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bail {

    private String id;
    private Etudiant etudiant;
    private Logement logement;
    private LocalDate dateSignature;
    private LocalDate dateEntree;
    private LocalDate dateFin;
    private StatutBail statut;

    private double loyerHorsCharges;
    private double charges;
    private double caution;
    private double fraisDossier;

    private Set<Option> options = new HashSet<>();

    public List<Paiement> paiements = new ArrayList<Paiement>();
    public double soldeDu = 0;
    public double retenues = 0;
    public double cautionRestituee = 0;

    public Bail(String id, Etudiant etudiant, Logement logement, LocalDate dateSignature) {
        this.id = id;
        this.etudiant = etudiant;
        this.logement = logement;
        this.dateSignature = dateSignature;
        this.statut = StatutBail.SIGNE;
    }

    public String getId() { return id; }
    public Etudiant getEtudiant() { return etudiant; }
    public Logement getLogement() { return logement; }
    public LocalDate getDateSignature() { return dateSignature; }
    public LocalDate getDateEntree() { return dateEntree; }
    public void setDateEntree(LocalDate d) { this.dateEntree = d; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate d) { this.dateFin = d; }
    public StatutBail getStatut() { return statut; }
    public void setStatut(StatutBail statut) { this.statut = statut; }
    public double getLoyerHorsCharges() { return loyerHorsCharges; }
    public void setLoyerHorsCharges(double l) { this.loyerHorsCharges = l; }
    public double getCharges() { return charges; }
    public void setCharges(double c) { this.charges = c; }
    public double getLoyerChargesComprises() { return Utils.arrondir(loyerHorsCharges + charges); }
    public double getCaution() { return caution; }
    public void setCaution(double c) { this.caution = c; }
    public double getFraisDossier() { return fraisDossier; }
    public void setFraisDossier(double f) { this.fraisDossier = f; }

    public Set<Option> getOptions() { return options; }
    public void setOptions(Set<Option> options) {
        this.options = (options != null) ? new HashSet<>(options) : new HashSet<>();
    }

    public String listerOptions() {
        List<String> labels = new ArrayList<>();
        if (options.contains(Option.MENAGE)) labels.add("ménage");
        if (options.contains(Option.PARKING)) labels.add("parking");
        if (options.contains(Option.LAVERIE)) labels.add("laverie");
        if (options.contains(Option.FIBRE)) labels.add("fibre");
        if (options.contains(Option.ASSURANCE)) labels.add("assurance");
        if (options.contains(Option.LOCAL_VELO)) labels.add("local vélo");
        if (labels.isEmpty()) return "aucune";
        return String.join(", ", labels);
    }
}
