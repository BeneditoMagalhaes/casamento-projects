package com.casamento.wedding.gallery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<GalleryPhoto, Long> {
    List<GalleryPhoto> findAllByOrderByIdAsc();
}
