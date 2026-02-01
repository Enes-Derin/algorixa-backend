package com.enesderin.softvra_backend.servis;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // Sana gelen bildirim
    public void sendAdminNotification(String fromName, String fromEmail, String message) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, false, "UTF-8");

            helper.setTo("enesderin.contact@gmail.com");
            helper.setSubject("🔔 Yeni İletişim Talebi - " + fromName);
            helper.setText("""
                    ═══════════════════════════════════════
                    YENİ İLETİŞİM TALEBİ
                    ═══════════════════════════════════════
                    
                    👤 İsim: %s
                    📧 E-posta: %s
                    📅 Tarih: %s
                    
                    ───────────────────────────────────────
                    MESAJ İÇERİĞİ:
                    ───────────────────────────────────────
                    
                    %s
                    
                    ═══════════════════════════════════════
                    
                    ⚠️ En kısa sürede yanıt verilmesi önerilir.
                    """.formatted(
                    fromName,
                    fromEmail,
                    java.time.LocalDateTime.now()
                            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    message
            ));

            mailSender.send(mail);

        } catch (Exception e) {
            throw new RuntimeException("Admin mail gönderilemedi", e);
        }
    }

    // Kullanıcıya profesyonel bilgilendirme maili
    public void sendAutoReplyWithInformation(String toEmail, String name) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            // Multipart gerekmediği için false
            MimeMessageHelper helper = new MimeMessageHelper(mail, false, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom("enesderin.contact@gmail.com", "Algorixa");
            helper.setSubject("✅ Talebiniz Alındı - Algorixa Kurumsal Web & Yazılım");

            helper.setText("""
                    Sayın %s,
                    
                    İletişim talebiniz başarıyla alınmıştır. 
                    
                    📋 SÜREÇ HAKKINDA BİLGİLENDİRME
                    ═══════════════════════════════════════
                    
                    ✓ Talebiniz inceleniyor
                    ✓ En geç 24 saat içinde size dönüş yapacağız
                    ✓ Proje detaylarınızı değerlendiriyoruz
                    ✓ Size özel teklif hazırlanacak
                    
                    ───────────────────────────────────────
                    
                    💼 HİZMETLERİMİZ
                    
                    🌐 Kurumsal Web Siteleri
                    ⚡ Özel Yazılım Geliştirme
                    🎨 UI/UX Tasarım Hizmetleri
                    🔧 Teknik Destek & Bakım
                    
                    ───────────────────────────────────────
                    
                    📞 ACİL İHTİYAÇ İÇİN
                    
                    WhatsApp: +90 546 970 54 51
                    E-posta: enesderin.contact@gmail.com
                    Web: https://www.algorixa.com.tr
                    
                    ═══════════════════════════════════════
                    
                    İşletmenizin başarılarına ortak olmaktan 
                    mutluluk duyarız.
                    
                    Saygılarımızla,
                    
                    Algorixa
                    "Fikirleri Kodluyoruz"
                    
                    ───────────────────────────────────────
                    
                    💡 İpucu: Proje detaylarınızı ne kadar 
                    detaylı paylaşırsanız, size o kadar 
                    hızlı ve doğru teklif sunabiliriz.
                    
                    📌 Bu e-posta otomatik olarak gönderilmiştir.
                    Lütfen yanıtlamayın. İletişim için yukarıdaki
                    kanalları kullanabilirsiniz.
                    """.formatted(name));

            mailSender.send(mail);

        } catch (Exception e) {
            throw new RuntimeException("Bilgilendirme maili gönderilemedi", e);
        }
    }
}