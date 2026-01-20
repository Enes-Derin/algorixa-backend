package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.ContactMessageController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.request.ContactMessageRequest;
import com.enesderin.softvra_backend.dto.response.ContactMessageResponse;
import com.enesderin.softvra_backend.servis.ContactMessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactMessageControllerImpl extends RestBaseController implements ContactMessageController {

    private ContactMessageService contactMessageService;

    @PostMapping()
    @Override
    public RootEntity<String> sendMessage(@RequestBody ContactMessageRequest message) {
        contactMessageService.handleNewMessage(message);
        return ok("success");
    }

    // 🔹 Admin panel → tüm mesajları listele
    @GetMapping("/admin")
    public RootEntity<List<ContactMessageResponse>> getAllMessages() {
        return ok(contactMessageService.getAllMessages());
    }

    // 🔹 Admin → mesaj okundu olarak işaretle
    @PutMapping("/admin/read/{id}")
    public RootEntity<String> markAsRead(@PathVariable Long id) {
        contactMessageService.markAsRead(id);
        return RootEntity.ok("okundu");
    }
}
