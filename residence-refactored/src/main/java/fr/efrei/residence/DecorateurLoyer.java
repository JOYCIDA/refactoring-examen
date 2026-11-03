package fr.efrei.residence;

public abstract class DecorateurLoyer implements CalculLoyer {

    protected final CalculLoyer composant;

    public DecorateurLoyer(CalculLoyer composant) {
        this.composant = composant;
    }
}
