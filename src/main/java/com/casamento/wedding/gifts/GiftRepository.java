package com.casamento.wedding.gifts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<GiftItem, Long> {
}
