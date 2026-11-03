package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursTest {

    private final CalculLoyer base100 = () -> 100.0;

    @Test
    void testDecorateurMeuble() {
        CalculLoyer decorateur = new DecorateurMeuble(base100);
        assertEquals(115.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurRdc() {
        CalculLoyer decorateur = new DecorateurRdc(base100);
        assertEquals(95.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurMenageStandard() {
        CalculLoyer decorateur = new DecorateurMenage(base100, false);
        assertEquals(108.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurMenageColocation() {
        CalculLoyer decorateur = new DecorateurMenage(base100, true);
        assertEquals(104.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurParking() {
        CalculLoyer decorateur = new DecorateurParking(base100);
        assertEquals(145.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurLaverie() {
        CalculLoyer decorateur = new DecorateurLaverie(base100);
        assertEquals(112.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurFibreStandard() {
        CalculLoyer decorateur = new DecorateurFibre(base100, false);
        assertEquals(120.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurFibreColocation() {
        CalculLoyer decorateur = new DecorateurFibre(base100, true);
        assertEquals(100.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testDecorateurAssurance() {
        CalculLoyer decorateur = new DecorateurAssurance(base100);
        assertEquals(109.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testAssembleurLoyerComplet() {
        Studio studio = new Studio("A101", 20, 0, true); // 20m², étage 0, meublé
        // Base : 20 * 14 = 280 < 350 -> min 350
        // Meublé : 350 * 1.15 = 402.5
        // RDC (étage 0) : 402.5 * 0.95 = 382.375
        // Ménage : 382.375 * 1.08 = 412.965
        // Parking : +45 -> 457.965
        // Assurance : +9 -> 466.965
        CalculLoyer chaine = AssembleurLoyer.assembler(studio, Set.of(Option.MENAGE, Option.PARKING, Option.ASSURANCE));
        assertEquals(466.965, chaine.getLoyer(), 0.001);
    }
}
