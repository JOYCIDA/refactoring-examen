package fr.efrei.residence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionPartieBTest {

    private GestionResidence gestion;

    @BeforeEach
    void setUp() {
        Residence.initialiser();
        gestion = new GrapheDeTest().gestion;
    }

    @Test
    void testDecorateurLocalVeloIsolament() {
        CalculLoyer base100 = () -> 100.0;
        DecorateurLocalVelo decorateur = new DecorateurLocalVelo(base100);
        assertEquals(106.0, decorateur.getLoyer(), 0.001);
    }

    @Test
    void testAssemblageAvecLocalVeloApresAssurance() {
        Studio studio = new Studio("A101", 20, 1, false); // 20m², étage 1, non meublé
        // Base : 20 * 14 = 280 < 350 -> 350 €
        // Assurance : +9 -> 359 €
        // Local vélo : +6 -> 365 €
        CalculLoyer chaine = AssembleurLoyer.assembler(studio, Set.of(Option.ASSURANCE, Option.LOCAL_VELO));
        assertEquals(365.0, chaine.getLoyer(), 0.001);
    }

    @Test
    void testSportifStrategyIsolament() {
        SportifStrategy strategy = new SportifStrategy();

        // 1. Remise 15 %
        assertEquals(85.0, strategy.appliquerRemise(100.0, 0), 0.001);

        // 2. Solvabilité 2 x loyer CC
        assertTrue(strategy.estSolvable(800.0, 400.0));
        assertFalse(strategy.estSolvable(799.0, 400.0));

        // 3. Caution 1 mois HC
        assertEquals(450.0, strategy.calculerCaution(450.0), 0.001);

        // 4. Frais de dossier 50 €
        assertEquals(50.0, strategy.getFraisDossier(), 0.001);

        // 5. Durée du préavis 15 jours
        assertEquals(15, strategy.getDureePreavis());

        // 6. Pénalités de retard normales : 5 % min 10 €
        assertEquals(20.0, strategy.calculerPenaliteRetard(400.0), 0.001); // 5% de 400 = 20
        assertEquals(10.0, strategy.calculerPenaliteRetard(100.0), 0.001); // 5% de 100 = 5 < 10 -> min 10
    }

    @Test
    void testScenarioCompletEtudiantSportifAvecLocalVelo() {
        // 1. Inscription d'un sportif de haut niveau
        Etudiant leo = gestion.inscrireEtudiant("Messi", "Leo", "leo.messi@sport.fr", "0611223344", null,
                Profil.SPORTIF, 0, 1000.0, false, Canal.EMAIL);
        assertNotNull(leo);
        assertEquals(Profil.SPORTIF, leo.getProfil());
        assertInstanceOf(SportifStrategy.class, leo.getStrategy());

        // 2. Attribution d'un Studio avec LOCAL_VELO et PARKING
        LocalDate signature = LocalDate.of(2026, 9, 1);
        Bail bail = gestion.attribuerLogement(leo, TypeLogement.STUDIO,
                Set.of(Option.PARKING, Option.LOCAL_VELO), signature);
        assertNotNull(bail);

        // Vérification des caractéristiques du bail
        // Studio 26m² (A001) : base = 26 * 14 = 364
        // Meublé (*1.15) = 418.6
        // RDC (*0.95) = 397.67
        // Options : parking (+45) + local vélo (+6) = 448.67
        // Remise sportif 15% (*0.85) = 381.37
        assertEquals(381.37, bail.getLoyerHorsCharges(), 0.01);
        assertEquals(381.37, bail.getCaution(), 0.01); // 1 mois de loyer HC
        assertEquals(50.0, bail.getFraisDossier(), 0.01); // Frais de dossier 50 €
        assertTrue(bail.listerOptions().contains("local vélo"));
        assertTrue(bail.listerOptions().contains("parking"));

        // 3. Préavis
        gestion.entrerDansLesLieux(bail.getId(), signature);
        gestion.donnerPreavis(bail.getId(), signature);
        assertEquals(signature.plusDays(15), bail.getDateFin()); // 15 jours de préavis
    }
}
