package com.casamento.wedding.settings;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Configuracoes gerais do site (linha unica, id fixo = 1L).
 * Por enquanto guarda so a foto de capa da home, mas pode crescer.
 */
@Entity
@Table(name = "site_settings")
public class SiteSettings {

    @Id
    private Long id = 1L;

    private String coverPhotoUrl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCoverPhotoUrl() { return coverPhotoUrl; }
    public void setCoverPhotoUrl(String coverPhotoUrl) { this.coverPhotoUrl = coverPhotoUrl; }
}
