package com.casamento.wedding.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Expoe a pasta local de uploads (fotos da galeria) como arquivos estaticos
 * em /uploads/**, para que o navegador consiga exibir as imagens enviadas
 * pelo painel admin.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void ensureUploadDirExists() throws IOException {
        // precisa existir ANTES de montarmos a URL do resource handler, para
        // que o Java reconheça o caminho como diretorio (com "/" no final)
        Files.createDirectories(Paths.get(uploadDir));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        String location = dir.toUri().toString(); // ex: file:/C:/.../uploads/ (funciona em Windows e Linux)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }
}
