package com.casamento.wedding.config;

import com.casamento.wedding.gifts.GiftItem;
import com.casamento.wedding.gifts.GiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Na primeira vez que o servidor roda (banco ainda vazio), cria a lista de
 * presentes com os itens combinados pelo casal, para o site nao nascer
 * vazio. Depois disso o casal pode editar tudo (nome, foto, preco, links de
 * pagamento) pelo painel admin.
 *
 * As descricoes trazem o link da loja onde o item foi encontrado, apenas
 * como referencia visual ate que uma foto de verdade seja enviada pelo
 * painel admin (edite o presente -> "Imagem" para trocar pela foto real).
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
            giftRepository.save(gift("Fritadeira Elétrica Air Fryer Mondial Afn40bi 4l Preto/Inox", "https://www.mercadolivre.com.br/fritadeira-eletrica-air-fryer-mondial-afn40bi-4l-cor-blackinox/p/MLB38759163"));
            giftRepository.save(gift("Aparelho de Jantar e Chá 20 Peças Oxford Bege e Marrom", "https://www.magazineluiza.com.br/aparelho-de-jantar-e-cha-20-pecas-oxford-de-ceramica-bege-e-marrom-redondo-unni-brisa/p/237715800/ud/apja/"));
            giftRepository.save(gift("Kit de 6 Copos Altos Wolff 430ml Vidro com Fio Ouro", "https://www.mercadolivre.com.br/conjunto-6-copos-altos-lines-com-fio-ouro-430ml-vidro-wolff/p/MLB70237129"));
            giftRepository.save(gift("Tábua de Corte Premium Dupla Face Inox e PP com Afiador", "https://www.amazon.com.br/dp/B0G14TH8GX"));
            giftRepository.save(gift("Jogo de Talheres Faqueiro Inox 24 Peças Búzios Tramontina", "https://www.amazon.com.br/dp/B07WGQ64QR"));
            giftRepository.save(gift("Jogo Americano Auxom Kit 6 Peças Prata", "https://www.mercadolivre.com.br/jogo-americano-auxom-kit-6-pecas-mesa-jantar-sala-cor-prata/up/MLBU4530407158"));
            giftRepository.save(gift("Kit 15 Potes Herméticos Cadência Mantimentos Branco", "https://www.mercadolivre.com.br/kit-15-potes-hermetico-cadencia-mantimentos-quadrado-cozinha-branco/p/MLB54481836"));
            giftRepository.save(gift("Kit 10 Potes Herméticos de Vidro 640ml Starhouse", "https://www.mercadolivre.com.br/kit-10-potes-hermeticos-vidro-640ml-starhouse-marmita-forno-micro-ondas-airfryer-com-4-travas-de-super-vedacao/p/MLB53222689"));
            giftRepository.save(gift("Jogo de Panelas Tramontina Antiaderente Turim 10 Peças Preto", "https://www.mercadolivre.com.br/jogo-de-panelas-tramontina-antiaderente-turim-10-pc-preto/p/MLB32643222"));
            giftRepository.save(gift("Micro-ondas Electrolux 20L MT30S Prata", "https://www.mercadolivre.com.br/micro-ondas-electrolux-20l-mt30s-prata/p/MLB6351928"));
            giftRepository.save(gift("Liquidificador Mondial Turbo Inox L-1100 BI 1100W", "https://www.magazineluiza.com.br/liquidificador-mondial-turbo-inox-l-1100-bi-preto-com-filtro-12-velocidades-1100w/p/021756300/ep/liqu/"));
            giftRepository.save(gift("Sanduicheira Elétrica Cadence SAN400 Preta", "https://www.mercadolivre.com.br/sanduicheira-eletrica-cadence-san400-preta/p/MLB25741527"));
            giftRepository.save(gift("Jogo de Taças para Vinho Cristal 450ml 6 Peças Bohemia", "https://www.magazineluiza.com.br/jogo-de-tacas-para-vinho-cristal-450ml-6-pecas-haus-sense-bohemia/p/142270100/ud/tavi/"));
            giftRepository.save(gift("Jogo de 6 Taças de Vidro para Sobremesa e Sorvete", "https://www.mercadolivre.com.br/jogo-6-tacas-de-vidro-para-sobremesa-e-sorvete-caneladas-transparentes-com-pe-para-mousse-acai-frutas-gelatina-doces-mesa-posta-elegante-qg-imports/p/MLB76434942"));
            giftRepository.save(gift("Jogo de 4 Tigelas 500ml Oxford Ryo Maresia", "https://www.amazon.com.br/dp/B0G6TL8NX2"));
            giftRepository.save(gift("Conjunto de Travessas Refratárias Marinex Vidro 10 Peças", "https://www.mercadolivre.com.br/conjunto-de-travessas-refratarias-marinex-vidro-10-pecas-cor/up/MLBU1740538695"));
            giftRepository.save(gift("Kit 5 Potes de Vidro Hermético Redondo com Tampa", "https://www.mercadolivre.com.br/kit-conjunto-5-pote-vidro-hermetico-redondo-com-tampa-trava-tigela-bowls-amordicaneca-casa-cozinha-mantimentos/p/MLB58338152"));
            giftRepository.save(gift("Jarra de Vidro Borossilicato 1,9L com Tampa Inox", "https://www.mercadolivre.com.br/jarra-vidro-borossilicato-19-litros-tampa-inox-transparente/up/MLBU4479977321"));
            giftRepository.save(gift("Escorredor de Massas 40cm Inox para Macarrão", "https://www.mercadolivre.com.br/escorredor-de-massas-40cm-inox-para-macarrao-cor-cinza/p/MLB38330497"));
            giftRepository.save(gift("Organizador de Talheres de Bambu 7 Divisórias Extensível", "https://www.mercadolivre.com.br/organizador-de-talheres-madeira-bambu-7-divisorias-extensivel-smoofy/p/MLB74810105"));
            giftRepository.save(gift("Boleira de Vidro com Pé para Mesa Posta", "https://www.mercadolivre.com.br/petalas-boleira-de-vidro-com-pe-mesa-posta-cafeteria-porta-doces-cor-transparente/p/MLB23131495"));
            giftRepository.save(gift("Colcha Cobre-Leito Queen 3 Peças Hypercal 400 Fios", "https://produto.mercadolivre.com.br/MLB-6187902048-colcha-cobre-leito-queen-3-pcs-hypercal-400-fios-com-porta-_JM"));
            giftRepository.save(gift("Edredom King Dupla Face 400 Fios", "https://produto.mercadolivre.com.br/MLB-4460171567-edredom-king-dupla-face-400-fios-mais-grosso-toque-macio-_JM"));
            giftRepository.save(gift("Cuscuzeira Tramontina Turim Antiaderente 1,9L", "https://www.tramontina.com.br/cuscuzeira-tramontina-turim-em-aluminio-com-revestimento-interno-e-externo-em-antiaderente-starflon-max-chumbo-14-cm-1-9-l/20268614.html"));
            giftRepository.save(gift("Conjunto Aparelho de Fondue Grande Queijo e Chocolate", "https://www.mercadolivre.com.br/conjunto-aparelho-fondue-grande-queijo-chocolate-petisqueira-preto-grande/p/MLB74888231"));
            giftRepository.save(gift("Ar-condicionado Electrolux Split Inverter 9.000 BTUs Wi-Fi", "https://www.mercadolivre.com.br/ar-condicionado-electrolux-split-inverter-9000-btus-color-adapt-wi-fi-so-frio-cor-branco/p/MLB28473123"));
            giftRepository.save(gift("Máquina de Lavar Consul 10kg CWB10BB Branca", "https://www.mercadolivre.com.br/maquina-de-lavar-consul-10-kg-dosagem-economica-cwb10bb-branca/p/MLB67841910"));
            giftRepository.save(gift("Purificador de Água Electrolux Pure 4x PE12G", "https://www.mercadolivre.com.br/purificador-de-agua-natural-fria-e-gelada-eletronico-placa-compacta-painel-touch-pure-4x-pe12g-electrolux-cinza-grafite/p/MLB25541813"));
            giftRepository.save(gift("Churrasqueira Elétrica Mondial 1200W CH-07", "https://www.mercadolivre.com.br/churrasqueira-eletrica-mondial-1200w-ch-07/p/MLB45966467"));
            giftRepository.save(gift("Cafeteira Elétrica Dolce Arome Mondial 550W", "https://www.mercadolivre.com.br/cafeteira-eletrica-dolce-arome-mondial-550w-c-30-18x-fb/p/MLB18716121"));
            giftRepository.save(gift("Garrafa Térmica para Café/Chá 1L Cabo de Madeira", "https://www.mercadolivre.com.br/garrafa-termica-para-cafe-cha-fosca-cabo-de-madeira-1l-cor-preto/p/MLB25846388"));
            giftRepository.save(gift("Kit Lixeira 5L + Escova Sanitária para Banheiro Inox", "https://www.mercadolivre.com.br/kit-lixeira-5l--escova-sanitaria-p-banheiro-inox-22918/up/MLBU4132132126"));
            giftRepository.save(gift("Kit 15 Utensílios para Cozinha em Aço Inox", "https://www.mercadolivre.com.br/kit-15-utensilios-para-cozinha-em-aco-inox-jogo-de-completo/up/MLBU3131810329"));
            giftRepository.save(gift("Porta Temperos Inox Kit 12 Unidades Base Giratória", "https://www.mercadolivre.com.br/porta-temperos-condimentos-inox-kit-12-unidades-potes-vidro-base-giratorio-360-preto-organizador-cozinha-dosador/p/MLB77336317"));
            giftRepository.save(gift("Kit Jogo Americano Redondo Sousplat", "https://www.mercadolivre.com.br/kit-jogo-americano-redondo-sousplat-cozinha-jantar/up/MLBU3857380970"));
            giftRepository.save(gift("Escorredor Porta 6 Copos de Mesa", "https://www.mercadolivre.com.br/escorredor-porta-6-copos-de-mesa-para-organizacao-de-cozinha/up/MLBU3699467196"));
            giftRepository.save(gift("Varal de Chão Reforçado Dobrável Retrátil Slim", "https://produto.mercadolivre.com.br/MLB-2652687796-varal-de-cho-reforcado-com-abas-dobravel-retratil-slim-_JM"));
            giftRepository.save(gift("Kit de Acessórios para Banheiro 5 Peças Banho Duplo", "https://www.mercadolivre.com.br/kit-de-acessorios-para-banheiro-5-pecas-banho-duplo/up/MLBU1725226132"));
            giftRepository.save(gift("Dispenser Organizador de Pia com Detergente Inox", "https://www.mercadolivre.com.br/dispenser-organizador-de-pia-com-detergente-sabao-aco-inox/up/MLBU3366769447"));
            giftRepository.save(gift("Escorredor de Pratos Aço Inox Duplo", "https://www.mercadolivre.com.br/escorredor-de-pratos--aco-inox-duplo-louca-talheres-copos/up/MLBU4103345751"));
            giftRepository.save(gift("Jogo de Toalhas Rosto + Banhão 5 Peças Azul Rose", "https://www.mercadolivre.com.br/jogo-de-toalhas-rosto-banhao-algodao-5-pecas-azul-rose-classic-appel/p/MLB43281951"));
            giftRepository.save(gift("Kit 2 Roupões de Casal Microfibra com Cinto", "https://produto.mercadolivre.com.br/MLB-4432383621-kit-2pc-roupo-casal-100microfibra-bolso-cinto-ajustavel-_JM"));
            giftRepository.save(gift("Mesa de Canto em L Escrivaninha para Escritório 157cm", "https://www.mercadolivre.com.br/mesa-de-canto-em-l-escrivaninha-para-escritorio-157cm-branco-me4116-tecno-mobili/p/MLB11148575"));
            giftRepository.save(gift("Kit 5 Caixas Organizadoras com Tampa Multiuso", "https://www.mercadolivre.com.br/kit-5-caixas-organizadoras-cesto-tampa-multiuso-2l-6l-16l-casa-cozinha-banheiro/p/MLB51764004"));
            giftRepository.save(gift("Cortina Persiana de Rolo Blackout Linho Natural", "https://www.amazon.com.br/dp/B0FWMWZD22"));
            giftRepository.save(gift("Espelho Retangular Grande 170x70 Corpo Inteiro", "https://www.magazineluiza.com.br/espelho-retangular-grande-170-x-70-corpo-inteiro-moderno-lapidado-com-suporte-cubas-e-gabinetes/p/jdhj7ak513/de/elde/"));
            giftRepository.save(gift("Depurador e Exaustor Philco PDR60P Slim 60cm Preto", "https://www.philco.com.br/depurador-slim-pdr60p-059011006/p?idsku=5699"));
            giftRepository.save(gift("Ventilador de Mesa Mondial 30cm 6 Pás Super Power", "https://www.amazon.com.br/dp/B09B159RYL"));
            giftRepository.save(gift("Cesto de Roupa Rattan 50L com Tampa Basculada", "https://www.mercadolivre.com.br/cesto-de-roupa-rattan-50-litros-com-tampa-basculada-preto/up/MLBU4615055591"));
            giftRepository.save(gift("Smart TV 50\" Samsung UHD 4K Crystal U8600F", "https://www.magazineluiza.com.br/smart-tv-50-samsung-uhd-4k-crystal-uhd-u8600f-un50u8600fgxzd-tizen-crystal-4k-bixby-e-alexa-3-hdmi/p/240147400/et/elit/"));
            giftRepository.save(gift("Manta Microfibra Cobertor Queen Antialérgico", "https://www.mercadolivre.com.br/manta-microfibra-cobertor-queen-antialergico-poa-quentinho/up/MLBU4017627771"));
            giftRepository.save(gift("Sofá-cama Belize Estofados Ferrari 2 Lugares Cinza", "https://www.mercadolivre.com.br/sofa-cama-belize-estofados-ferrari-de-2-lugares-cor-cinza/p/MLB22432830"));
            giftRepository.save(gift("Kit Tapete para Banheiro Premium Antiderrapante", "https://www.mercadolivre.com.br/kit-tapete-para-banheiro-premium-completo-antiderrapante/up/MLBU3840894106"));
            giftRepository.save(gift("Kit Suporte Porta Shampoo de Canto para Banheiro", "https://www.mercadolivre.com.br/kit-suporte-porta-shampoo-de-canto-cantoneira-para-banheiro/up/MLBU3099296635"));
        }
    }

    /**
     * Cria um presente com o titulo combinado pelo casal. O link da loja
     * (storeLink) fica so aqui no codigo-fonte, como referencia para quem
     * for procurar a foto/modelo do produto depois - nao aparece pro
     * convidado.
     */
    private GiftItem gift(String title, String storeLink) {
        GiftItem g = new GiftItem();
        g.setIcon("🎁");
        g.setTitle(title);
        g.setDescription("");
        g.setPrice("");
        return g;
    }
}
