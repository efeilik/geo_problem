package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.enums.Role;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setup() {
        emailService = new EmailService(javaMailSender);
        ReflectionTestUtils.setField(emailService, "sender", "test@test.com");
    }

    @Test
    public void sendEmailsForProblem_ShouldSendEmails() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        Problem problem = new Problem();
        problem.setId(1L);
        problem.setTaken(false);
        problem.setLongitude(1);
        problem.setLatitude(1);
        problem.setAddress("test");
        problem.setDescription("test");
        problem.setUniqueCode("test");

        User user = new User();
        user.setId(1L);
        user.setEmail("test");
        user.setName("test");
        user.setPassword("test");
        user.setRole(Role.ROLE_USER);
        user.setAddress("test");
        user.setLatitude(1);
        user.setLongitude(1);

        List<User> users = Collections.singletonList(user);

        emailService.sendEmailsForProblem(problem, users);

        verify(javaMailSender, times(1)).send(mimeMessage);

        verify(javaMailSender).send(mimeMessage);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("test@test.com");
        helper.setTo("test");
        helper.setSubject("Yeni Problem: test");

        String expectedHtmlContent = "<html>" +
                "<body>" +
                "<h2>Merhaba, test!</h2>" +
                "<p>Yakın çevrenizde yeni bir problem bildirildi:</p>" +
                "<p><strong>Problem Açıklaması:</strong> test</p>" +
                "<p>Konum: test </p>" +
                "<p>Eğer bu problemi çözmek istiyorsanız, lütfen uygulamayı kullanarak aşağıda yer alan kodla talep edin.</p>" +
                "<br>" +
                "<p><strong>test</strong></p>" +
                "<br>" +
                "<p>Teşekkürler,</p>" +
                "</body>" +
                "</html>";
        verify(javaMailSender, times(1)).send(mimeMessage);

        assertNotNull(expectedHtmlContent);
    }
}
