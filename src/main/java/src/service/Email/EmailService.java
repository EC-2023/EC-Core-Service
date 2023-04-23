package src.service.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    private final Session mailSession;

    private final SpringTemplateEngine templateEngine;

    public EmailService(Session mailSession, SpringTemplateEngine templateEngine) {
        this.mailSession = mailSession;
        this.templateEngine = templateEngine;
    }

    public void sendWelcomeEmail(String to, String name) throws MessagingException {
        String subject = "Chào mừng bạn";
        Context context = new Context();
        context.setVariable("name", name);
        String emailContent = templateEngine.process("welcome", context);
        sendEmail(to, subject, emailContent);
    }

    public void sendResetPasswordEmail(String to, String resetLink) throws MessagingException {
        String subject = "Đặt lại mật khẩu";
        Context context = new Context();
        context.setVariable("resetLink", resetLink);
        String emailContent = templateEngine.process("reset_password", context);
        sendEmail(to, subject, emailContent);
    }

    public void sendSellerRegistrationEmail(String to, String name) throws MessagingException {
        String subject = "Thông báo đăng ký người bán hàng thành công";
        Context context = new Context();
        context.setVariable("name", name);
        String emailContent = templateEngine.process("seller_registration", context);
        sendEmail(to, subject, emailContent);
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(new InternetAddress("20161332@student.hcmute.edu.vn"));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        Transport.send(message);
    }

}
