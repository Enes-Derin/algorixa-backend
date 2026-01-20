package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.ContactMessageRequest;

public interface ContactMessageController {
    RootEntity<String> sendMessage(ContactMessageRequest contactMessageRequest);
}
