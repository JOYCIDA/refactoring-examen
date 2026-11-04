package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifLogementTest {

    private final LogementFactory fabrique = new LogementFactory();

    @Test
    void testStudioAuMetreCarre() {
        Logement studio = fabrique.creer(TypeLogement.STUDIO, "X1", 30, 1, false, 0);
        assertEquals(420.0, new TarifStudio().calculerLoyerDeBase(studio), 0.001);
    }

    @Test
    void testStudioMinimum() {
        Logement studio = fabrique.creer(TypeLogement.STUDIO, "X1", 20, 1, false, 0);
        assertEquals(350.0, new TarifStudio().calculerLoyerDeBase(studio), 0.001);
    }

    @Test
    void testT1() {
        Logement t1 = fabrique.creer(TypeLogement.T1, "X1", 28, 1, false, 0);
        assertEquals(414.0, new TarifT1().calculerLoyerDeBase(t1), 0.001);
    }

    @Test
    void testT2() {
        Logement t2 = fabrique.creer(TypeLogement.T2, "X1", 45, 1, false, 6);
        assertEquals(640.0, new TarifT2().calculerLoyerDeBase(t2), 0.001);
    }

    @Test
    void testColocationParPlace() {
        Logement coloc = fabrique.creer(TypeLogement.COLOCATION, "X1", 80, 1, false, 4);
        assertEquals(220.0, new TarifColocation().calculerLoyerDeBase(coloc), 0.001);
    }

    @Test
    void testCharges() {
        assertEquals(35.0, new TarifStudio().getCharges(), 0.001);
        assertEquals(35.0, new TarifT1().getCharges(), 0.001);
        assertEquals(35.0, new TarifT2().getCharges(), 0.001);
        assertEquals(25.0, new TarifColocation().getCharges(), 0.001);
    }
}
