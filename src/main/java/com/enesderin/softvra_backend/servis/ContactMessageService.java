package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.ContactMessageRequest;
import com.enesderin.softvra_backend.dto.response.ContactMessageResponse;
import com.enesderin.softvra_backend.model.ContactMessage;

import java.util.List;

public interface ContactMessageService {
    void handleNewMessage(ContactMessageRequest message);

    List<ContactMessageResponse> getAllMessages();

    void markAsRead(Long id);
}
