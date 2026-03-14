// ContactMessageRepository.java - GÜNCELLEME
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findAllByOrderByCreatedAtDesc();

    Long countByReadFalse(); // EKLE
}