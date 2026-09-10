package com.casamento.wedding.gallery;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "gallery_photo")
public class GalleryPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private Instant uploadedAt = Instant.now();

    public GalleryPhoto() {}

    public GalleryPhoto(String filename) {
        this.filename = filename;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }
}
