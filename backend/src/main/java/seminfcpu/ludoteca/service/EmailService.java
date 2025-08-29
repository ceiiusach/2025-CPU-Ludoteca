package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.entity.Loan;
import seminfcpu.ludoteca.entity.User;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


@Service
public final class EmailService {
    private final JavaMailSender mailSender;
    private final UserService userService;

    public EmailService(@NotNull JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public boolean sendVerificationCode(String toEmail) {
        User responseUser = userService.getByEmail(toEmail).orElseThrow();
        String code = generate6DigitCode();
        responseUser.setCode(code);
        responseUser.setExpirationCode(LocalDateTime.now().plusMinutes(5));
        userService.update(responseUser);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Código de verificación");
        message.setText("Tu código de verificación es: " + code);
        mailSender.send(message);

        responseUser.setCode(code);
        responseUser.setExpirationCode(LocalDateTime.now().plusMinutes(5));

        return true;
    }

    private String generate6DigitCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(900000) + 100000;
        return String.valueOf(num);
    }

    public boolean verifyCode(String email, String inputCode) {
        User user = userService.getByEmail(email).orElseThrow();
        if (user.getCode() == null || user.getExpirationCode() == null) return false;
        if (!user.getCode().equals(inputCode)) return false;
        if (user.getExpirationCode().isBefore(LocalDateTime.now())) return false;

        user.setExpirationCode(null);
        user.setCode(null);
        userService.update(user);

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
