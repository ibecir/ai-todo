package ba.edu.ibu.aitodo.api.impl.mailgun;

import ba.edu.ibu.aitodo.core.api.mailsender.MailSender;
import ba.edu.ibu.aitodo.core.model.User;

public class MailgunMailSender implements MailSender {
    @Override
    public String sendConfirmationEmail(User user, String code) {
        return "";
    }

    @Override
    public String sendBulkEmail(String subject, String body, String addresses) {
        return "";
    }

    @Override
    public String sendReminderEmail(User user) {
        return "";
    }
}
