package fr.efrei.residence;

/**
 * Seul endroit qui choisit l'implémentation d'un canal de notification.
 */
public class NotificationFactory {

    private final EmailService email;
    private final SmsService sms;
    private final CourrierService courrier;
    private final PushService push;

    public NotificationFactory(EmailService email, SmsService sms, CourrierService courrier, PushService push) {
        this.email = email;
        this.sms = sms;
        this.courrier = courrier;
        this.push = push;
    }

    public CanalNotification creer(Canal canal) {
        return switch (canal) {
            case EMAIL -> new CanalEmail(email);
            case SMS -> new CanalSms(sms);
            case COURRIER -> new CanalCourrier(courrier);
            case PUSH -> new CanalPush(push);
        };
    }
}
