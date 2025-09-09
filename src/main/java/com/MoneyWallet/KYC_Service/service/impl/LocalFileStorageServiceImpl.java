package com.MoneyWallet.KYC_Service.service.impl;

import com.MoneyWallet.KYC_Service.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class LocalFileStorageServiceImpl implements FileStorageService {

    @Value("${kyc.upload-dir}")
    private String uploadDir;

    @Value("${server.url}")
    private String serverUrl;

    @Override
    public String storeFile(MultipartFile file, String subFolder) {
        try {
            String originalName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + "_" + originalName;

            Path folderPath = Paths.get(uploadDir, subFolder).toAbsolutePath().normalize();
            Files.createDirectories(folderPath);

            Path targetLocation = folderPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            // Build URL for later retrieval
            return serverUrl + "/files/" + subFolder + "/" + fileName;
        } catch (IOException ex) {
            log.error("Error storing file", ex);
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }
}
