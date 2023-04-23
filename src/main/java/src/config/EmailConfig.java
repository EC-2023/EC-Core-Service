package src.config;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${mailgun.smtp.host}")
    private String smtpHost;

    @Value("${mailgun.smtp.port}")
    private int smtpPort;

    @Value("${mailgun.smtp.username}")
    private String smtpUsername;

    @Value("${mailgun.smtp.password}")
    private String smtpPassword;
    @Bean
    public Session mailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host",smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });
    }
}
