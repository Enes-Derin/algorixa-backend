package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.ContactMessageRequest;
import com.enesderin.softvra_backend.dto.response.ContactMessageResponse;
import com.enesderin.softvra_backend.model.ContactMessage;
import com.enesderin.softvra_backend.repo.ContactMessageRepository;
import com.enesderin.softvra_backend.servis.ContactMessageService;
import com.enesderin.softvra_backend.servis.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repository;
    private final MailService mailService;

    public void handleNewMessage(ContactMessageRequest message) {

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setMessage(message.getMessage());
        contactMessage.setName(message.getName());
        contactMessage.setEmail(message.getEmail());
        contactMessage.setCreatedAt(LocalDateTime.now());
        contactMessage.setRead(false);

        repository.save(contactMessage);

        try {
            // Sana mail
            mailService.sendAdminNotification(
                    message.getName(),
                    message.getEmail(),
                    message.getMessage()
            );

            // Kullanıcıya otomatik cevap
            mailService.sendAutoReplyWithInformation(
                    message.getEmail(),
                    message.getName()
            );
        } catch (Exception e) {
            // 🔴 MAIL HATASI LOG’LA AMA REQUEST’İ BOZMA
            System.err.println("Mail gönderim hatası: " + e.getMessage());
        }
    }


    @Override
    public List<ContactMessageResponse> getAllMessages() {
        List<ContactMessage> allByOrderByCreatedAtDesc = repository.findAllByOrderByCreatedAtDesc();
        List<ContactMessageResponse> contactMessageResponses = new ArrayList<>();
        for (ContactMessage contactMessage : allByOrderByCreatedAtDesc) {
            ContactMessageResponse contactMessageResponse = new ContactMessageResponse();
            contactMessageResponse.setId(contactMessage.getId());
            contactMessageResponse.setName(contactMessage.getName());
            contactMessageResponse.setEmail(contactMessage.getEmail());
            contactMessageResponse.setMessage(contactMessage.getMessage());
            contactMessageResponse.setCreatedAt(contactMessage.getCreatedAt());
            contactMessageResponse.setRead(contactMessage.getRead());
            contactMessageResponses.add(contactMessageResponse);
        }
        return contactMessageResponses;
    }

    @Override
    public void markAsRead(Long id) {
        ContactMessage message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı"));

        message.setRead(true);
        repository.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        repository.deleteById(id);
    }
}
