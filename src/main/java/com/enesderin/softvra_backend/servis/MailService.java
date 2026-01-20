package com.enesderin.softvra_backend.servis;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // Sana gelen bildirim (PDF eklemeden)
    public void sendAdminNotification(String fromName, String fromEmail, String message) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, false, "UTF-8");

            helper.setTo("enesderin.contact@gmail.com");
            helper.setSubject("Yeni İletişim Mesajı");
            helper.setText("""
                    Yeni bir mesaj aldınız.

                    İsim: %s
                    E-posta: %s

                    Mesaj:
                    %s
                    """.formatted(fromName, fromEmail, message));

            mailSender.send(mail);

        } catch (Exception e) {
            throw new RuntimeException("Admin mail gönderilemedi", e);
        }
    }

    // Kullanıcıya otomatik cevap + TEKLİF PDF
    public void sendAutoReplyWithOffer(String toEmail, String name) {

        try {
            MimeMessage mail = mailSender.createMimeMessage();

            // ⚠️ multipart = true
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Dijital Hizmet ve Çözüm Teklifimiz | Algorixa");

            helper.setText("""
                    Merhaba %s,

                    İletişim talebiniz alınmıştır.

                    İhtiyacınıza yönelik hazırlanmış
                    dijital hizmet ve çözüm teklifimizi
                    ekte bulabilirsiniz.

                    İnceledikten sonra sorularınızı
                    memnuniyetle yanıtlarız.

                    Saygılarımla,
                    
                    
                    Algorixa
                    Dijital Çözümler
                    """.formatted(name));

            // 📎 PDF EKLE
            ClassPathResource pdf = new ClassPathResource(
                    "static/Dijital-Çözümler.pdf"
            );

            helper.addAttachment("Dijital-Çözümler.pdf", pdf);

            mailSender.send(mail);

        } catch (Exception e) {
            throw new RuntimeException("Teklif PDF maili gönderilemedi", e);
        }
    }
}
