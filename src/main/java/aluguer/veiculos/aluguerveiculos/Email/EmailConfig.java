package aluguer.veiculos.aluguerveiculos.Email;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public final class EmailConfig {
    public static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aluguer.veiculos.dias@gmail.com", "flrdudkawgbvpqty");
            }
        });
    }
}
