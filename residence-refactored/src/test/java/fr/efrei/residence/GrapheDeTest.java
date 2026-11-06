package fr.efrei.residence;

/**
 * Construit un graphe d'objets neuf pour chaque test : plus aucun état global à vider.
 */
class GrapheDeTest {

    final BaseMemoire base = new BaseMemoire();
    final EmailService email = new EmailService();
    final SmsService sms = new SmsService();
    final CourrierService courrier = new CourrierService();
    final PushService push = new PushService();
    final QuittanceFormatter quittances = new QuittanceFormatter();
    final GestionResidence gestion;

    GrapheDeTest() {
        ServiceNotification notifications = new ServiceNotification(new NotificationFactory(email, sms, courrier, push));
        gestion = new GestionResidence(
                new InscriptionService(base),
                new AttributionService(base, new CalculateurLoyer(), notifications),
                new CycleVieBailService(base, notifications),
                new RelanceService(base, notifications),
                new RapportService(base, sms, courrier),
                new LogementFactory());
    }
}
