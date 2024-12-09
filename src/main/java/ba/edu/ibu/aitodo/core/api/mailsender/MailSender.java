package ba.edu.ibu.aitodo.core.api.mailsender;

import ba.edu.ibu.aitodo.core.model.User;

import java.util.List;

public interface MailSender {
    String sendConfirmationEmail(User user, String code);
    String sendBulkEmail(String subject, String body, String addresses);
    String sendReminderEmail(User user);
}
