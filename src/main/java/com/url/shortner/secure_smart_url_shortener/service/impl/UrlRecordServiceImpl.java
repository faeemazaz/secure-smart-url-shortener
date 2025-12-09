package com.url.shortner.secure_smart_url_shortener.service.impl;

import com.url.shortner.secure_smart_url_shortener.constant.UtilityFields;
import com.url.shortner.secure_smart_url_shortener.dto.UrlRecordRequest;
import com.url.shortner.secure_smart_url_shortener.dto.UrlRecordResponse;
import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import com.url.shortner.secure_smart_url_shortener.exception.HttpMessageNotReadableException;
import com.url.shortner.secure_smart_url_shortener.repo.UrlRecordRepo;
import com.url.shortner.secure_smart_url_shortener.repo.UserRepository;
import com.url.shortner.secure_smart_url_shortener.service.UrlRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Service
public class UrlRecordServiceImpl implements UrlRecordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlRecordRepo urlRecordRepo;

    @Override
    public UrlRecordResponse shortenUrl(UrlRecordRequest request, Authentication authentication) {

        // fetch login user id
        String email = authentication.getName();
        Long userId = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found")).getUserId();

        // generate unique code
        String shortCode = generateShortUrl();

        // calculate expiry time
        Instant expiryTime = request.getExpiryTime() != null ? Instant.now().plus(Duration.ofMinutes(request.getExpiryTime())) : null;

        if (request.getAccessType() == null ||
                !(request.getAccessType().toString().equalsIgnoreCase("PUBLIC") ||
                        request.getAccessType().toString().equalsIgnoreCase("PRIVATE") ||
                        request.getAccessType().toString().equalsIgnoreCase("ROLE_BASED"))) {
            throw new HttpMessageNotReadableException("Invalid accessType. Allowed values: PUBLIC, PRIVATE, ROLE_BASED");
        }

        // Validate allowedRole only if accessType is PRIVATE or ROLE_BASED
        if (request.getAllowedRole() == null ||
                !(request.getAllowedRole().toString().equalsIgnoreCase("USER") ||
                        request.getAllowedRole().toString().equalsIgnoreCase("ADMIN"))) {
            throw new HttpMessageNotReadableException("Invalid allowedRole. Allowed values: USER, ADMIN");
        }

        // save url shorten data
        UrlRecord record = new UrlRecord();
        record.setOriginalUrl(request.getOriginalUrl());
        record.setOwnerId(userId);
        record.setShortCode(shortCode);
        record.setAccessType(request.getAccessType().toString());
        record.setAllowedRole(request.getAllowedRole().toString());
        record.setExpiryTime(expiryTime);
        record.setMaxClicks(request.getMaxClicks());
        urlRecordRepo.save(record);

        return new UrlRecordResponse(UtilityFields.BASE_URL + shortCode);
    }

    public String generateShortUrl() {
        final int code_length = 5;
        String chars = """
                abcdefghijklmnopqrstuvwxyz
                ABCDEFGHIJKLMNOPQRSTUVWXYZ
                0123456789
                """;
        Random random = new Random();
        while (true) {
            StringBuilder shortCode = new StringBuilder();
            //
            for (int i = 0; i <= code_length; i++) {
                // fetch random character from chars and store in shortCode
                shortCode.append(chars.charAt(random.nextInt(chars.length())));
            }

            /* if same url not exist in db then condition become false and while loop is closed
            otherwise generate new shortcode
             */
            if (!urlRecordRepo.existsByShortCode(shortCode.toString())) {
                return shortCode.toString();
            }
        }
    }
}
