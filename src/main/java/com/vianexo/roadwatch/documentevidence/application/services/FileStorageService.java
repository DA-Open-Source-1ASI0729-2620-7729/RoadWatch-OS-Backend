package com.vianexo.roadwatch.documentevidence.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService(@Value("${app.storage.upload-dir:uploads}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String store(MultipartFile file) {
        try {
            Files.createDirectories(rootLocation);
            var storedFileName = UUID.randomUUID().toString();
            Files.copy(file.getInputStream(), rootLocation.resolve(storedFileName),
                    StandardCopyOption.REPLACE_EXISTING);
            return storedFileName;
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not store file", exception);
        }
    }

    public Optional<Resource> load(String storedFileName) {
        var path = rootLocation.resolve(storedFileName).normalize();
        if (!path.startsWith(rootLocation) || !Files.isReadable(path)) {
            return Optional.empty();
        }
        return Optional.of(new FileSystemResource(path));
    }
}