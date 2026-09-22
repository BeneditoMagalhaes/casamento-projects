package com.casamento.wedding.settings;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Textos e data do casamento editáveis pelo painel admin (linha única,
 * id fixo = 1L). Guardado como um mapa chave->valor flexível: cada texto
 * editável da página (nomes, data, endereços, mensagens...) tem uma chave
 * (ex: "coupleNames", "weddingDate") e só sobrescreve o texto padrão do
 * HTML quando o admin de fato preenche/salva aquele campo.
 */
@Entity
@Table(name = "site_content")
public class SiteContent {

    @Id
    private Long id = 1L;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "site_content_entries", joinColumns = @JoinColumn(name = "site_content_id"))
    @MapKeyColumn(name = "entry_key", length = 100)
    @Column(name = "entry_value", length = 4000)
    private Map<String, String> entries = new HashMap<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Map<String, String> getEntries() { return entries; }
    public void setEntries(Map<String, String> entries) { this.entries = entries; }
}
