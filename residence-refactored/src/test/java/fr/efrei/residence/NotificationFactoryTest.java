package fr.efrei.residence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationFactoryTest {

    private EmailService email;
    private SmsService sms;
    private CourrierService courrier;
    private PushService push;
    private NotificationFactory fabrique;
    private ServiceNotification service;

    @BeforeEach
    void setUp() {
        email = new EmailService();
        sms = new SmsService();
        courrier = new CourrierService();
        push = new PushService();
        fabrique = new NotificationFactory(email, sms, courrier, push);
        service = new ServiceNotification(fabrique);
    }

    private Etudiant etudiant(Profil profil, Canal canal) {
        return new Etudiant("ETU-1", "Martin", "Alice", "alice@mail.fr", "0601020304", "1 rue de Paris", profil, 0, 1500,
                false, canal);
    }

    @Test
    void la_fabrique_cree_le_bon_canal() {
        assertInstanceOf(CanalEmail.class, fabrique.creer(Canal.EMAIL));
        assertInstanceOf(CanalSms.class, fabrique.creer(Canal.SMS));
        assertInstanceOf(CanalCourrier.class, fabrique.creer(Canal.COURRIER));
        assertInstanceOf(CanalPush.class, fabrique.creer(Canal.PUSH));
    }

    @Test
    void push_envoie_le_sujet_a_l_email_de_l_etudiant() {
        PrintStream ancien = System.out;
        ByteArrayOutputStream sortie = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sortie));
        try {
            service.notifier(etudiant(Profil.STANDARD, Canal.PUSH), "Votre bail", "Bienvenue");
        } finally {
            System.setOut(ancien);
        }

        assertTrue(sortie.toString().contains("[PUSH] -> alice@mail.fr : Votre bail"));
        assertEquals(1, push.getJournal().size());
        assertEquals("alice@mail.fr", push.getJournal().get(0)[0]);
        assertEquals("Votre bail", push.getJournal().get(0)[1]);
    }

    @Test
    void push_n_envoie_ni_email_ni_sms_ni_courrier() {
        service.notifier(etudiant(Profil.STANDARD, Canal.PUSH), "Sujet", "Message");

        assertTrue(email.getJournal().isEmpty());
        assertEquals(0, sms.getCompteurSms());
        assertTrue(courrier.getJournal().isEmpty());
    }

    @Test
    void un_international_en_push_recoit_aussi_un_email_en_anglais() {
        service.notifier(etudiant(Profil.INTERNATIONAL, Canal.PUSH), "Sujet", "Message");

        assertEquals(1, push.getJournal().size());
        assertEquals(1, email.getJournal().size());
        assertEquals("[EN] Sujet", email.getJournal().get(0)[1]);
    }

    @Test
    void la_relance_par_push_envoie_un_push() {
        service.relancer(etudiant(Profil.STANDARD, Canal.PUSH), "BAIL-001", 120);

        assertEquals(1, push.getJournal().size());
        assertEquals("RELANCE - loyer impayé", push.getJournal().get(0)[1]);
    }

    @Test
    void sms_est_tronque_a_160_caracteres() {
        service.notifier(etudiant(Profil.STANDARD, Canal.SMS), "Sujet", "x".repeat(300));

        assertEquals(160, sms.getJournal().get(0)[1].length());
        assertEquals(1, sms.getCompteurSms());
    }

    @Test
    void courrier_coute_un_timbre() {
        service.notifier(etudiant(Profil.STANDARD, Canal.COURRIER), "Sujet", "Message");
        service.notifier(etudiant(Profil.STANDARD, Canal.COURRIER), "Sujet", "Message");

        assertEquals(2 * CourrierService.PRIX_TIMBRE, courrier.getTotalAffranchissement(), 1e-9);
    }
}
