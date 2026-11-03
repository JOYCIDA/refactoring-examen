package fr.efrei.residence;

public interface ProfilStrategy {

    double appliquerRemise(double loyer, int echelon);

    boolean estSolvable(double ressources, double totalChargesComprises);

    double calculerCaution(double loyerHorsCharges);

    double getFraisDossier();

    int getDureePreavis();

    double calculerPenaliteRetard(double loyerChargesComprises);
}
