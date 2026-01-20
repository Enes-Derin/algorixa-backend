package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {
    // Okunmamış mesajlar
    List<ContactMessage> findByReadFalse();

    // En yeni mesajlar üstte
    List<ContactMessage> findAllByOrderByCreatedAtDesc();
}
