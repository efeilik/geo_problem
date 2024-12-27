package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmailsForProblem(Problem problem, List<User> users) {
        for (User user : users) {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                helper.setFrom(sender);
                helper.setTo(user.getEmail());
                helper.setSubject("Yeni Problem: " + problem.getDescription());

                String emailContent = buildHtmlContent(user, problem);
                helper.setText(emailContent, true);

                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String buildHtmlContent(User user, Problem problem) {
        return "<html>" +
                "<body>" +
                "<h2>Merhaba, " + user.getName() + "!</h2>" +
                "<p>Yakın çevrenizde yeni bir problem bildirildi:</p>" +
                "<p><strong>Problem Açıklaması:</strong> " + problem.getDescription() + "</p>" +
                "<p>Konum: " + problem.getAddress() + "</p>" +
                "<p>Eğer bu problemi çözmek istiyorsanız, lütfen uygulamayı kullanarak tek kullanımlık kodla talep edin.</p>" +
                "<br>" +
                "<p>Teşekkürler,</p>" +
                "</body>" +
                "</html>";
    }
}
