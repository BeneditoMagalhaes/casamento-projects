package com.casamento.wedding.rsvp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RsvpRepository extends JpaRepository<RsvpEntry, Long> {
    List<RsvpEntry> findAllByOrderByCreatedAtDesc();
}
