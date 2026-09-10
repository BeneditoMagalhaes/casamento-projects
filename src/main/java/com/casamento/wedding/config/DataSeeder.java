package com.casamento.wedding.config;

import com.casamento.wedding.gifts.GiftItem;
import com.casamento.wedding.gifts.GiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Na primeira vez que o servidor roda (banco ainda vazio), cria a lista de
 * presentes com os mesmos itens que existiam no HTML original, para o site
 * nao nascer vazio. Depois disso o casal pode editar tudo pelo painel admin.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final GiftRepository giftRepository;

    public DataSeeder(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    @Override
    public void run(String... args) {
        if (giftRepository.count() == 0) {
            giftRepository.save(gift("✈", "Lua de mel", "Uma contribuicao para as proximas aventuras do casal.", "A partir de R$ 100"));
            giftRepository.save(gift("⌂", "Casa nova", "Ajude a montar o nosso primeiro lar como casal.", "A partir de R$ 80"));
            giftRepository.save(gift("♡", "Jantar especial", "Uma noite especial para comemorarmos essa nova fase.", "A partir de R$ 60"));
        }
    }

    private GiftItem gift(String icon, String title, String description, String price) {
        GiftItem g = new GiftItem();
        g.setIcon(icon);
        g.setTitle(title);
        g.setDescription(description);
        g.setPrice(price);
        return g;
    }
}
