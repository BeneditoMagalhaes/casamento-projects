package com.casamento.wedding.gifts;

import jakarta.persistence.*;

@Entity
@Table(name = "gift_item")
public class GiftItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String icon = "🎁";

    private String title;

    @Column(length = 1000)
    private String description;

    private String price;

    /** URL da foto do presente (upload local em /uploads/gifts/... ou link externo). Se vazio, usa o icone/emoji. */
    @Column(length = 2000)
    private String imageUrl;

    /** Link de pagamento via Pix, gerado no site/app do banco. */
    @Column(length = 2000)
    private String pixLink;

    /** Link de pagamento no cartao de credito, gerado no site/app do banco. */
    @Column(length = 2000)
    private String cardLink;

    public GiftItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getPixLink() { return pixLink; }
    public void setPixLink(String pixLink) { this.pixLink = pixLink; }
    public String getCardLink() { return cardLink; }
    public void setCardLink(String cardLink) { this.cardLink = cardLink; }
}
