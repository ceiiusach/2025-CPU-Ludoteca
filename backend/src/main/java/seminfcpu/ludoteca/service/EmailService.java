package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.entity.Loan;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.entity.ValidationEmail;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


@Service
public final class EmailService {
    private final JavaMailSender mailSender;
    private final UserService userService;
    @Autowired
    private ValidationService validationService;

    public EmailService(@NotNull JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public boolean sendVerificationCode(String toEmail) {
        ValidationEmail responseUser = validationService.getValidation(toEmail);

        String code = generate6DigitCode();
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        if (responseUser == null) {
            responseUser = new ValidationEmail();
            responseUser.setEmail(toEmail);
            responseUser.setCode(code);
            responseUser.setExpirationCode(expiration);
            validationService.update(responseUser); // usar .save para nuevo
        } else {
            // Si existe, actualiza solo el código y expiración
            responseUser.setCode(code);
            responseUser.setExpirationCode(expiration);
            validationService.update(responseUser); // usar .update para existente
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Código de verificación");
        message.setText("Tu código de verificación es: " + code);
        mailSender.send(message);

        return true;
    }


    private String generate6DigitCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(900000) + 100000;
        return String.valueOf(num);
    }

    public boolean verifyCode(String email, String inputCode) {
        ValidationEmail user = validationService.getValidation(email);
        if (user.getCode() == null || user.getExpirationCode() == null) return false;
        if (!user.getCode().equals(inputCode)) return false;
        if (user.getExpirationCode().isBefore(LocalDateTime.now())) return false;

        user.setExpirationCode(null);
        user.setCode(null);
        validationService.update(user);

        return true;
    }

    public void sendDelayNotice(@NotNull Loan loan) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(loan.getUser().getEmail());
        message.setSubject("Recordatorio de devolución de %s".formatted(loan.getItem().getName()));
        message.setText("Ludoteca CEII te recuerda que aún no has devuelto %s que pediste el %s por aproximadamente %s minutos, ¡evita las sanciones!".formatted(loan.getItem().getName(), loan.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm")), loan.getEstimatedMinutes()));
        mailSender.send(message);
    }
}
