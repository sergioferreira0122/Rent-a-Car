package aluguer.veiculos.aluguerveiculos.Email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void sendEmail(String email) throws MessagingException {
        Session session = EmailConfig.getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("aluguer.veiculos.dias@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

        String html = "";

        message.setSubject("");
        message.setContent(html, "text/html");
        Transport.send(message);
    }
}
