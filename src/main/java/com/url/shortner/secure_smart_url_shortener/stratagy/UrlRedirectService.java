package com.url.shortner.secure_smart_url_shortener.stratagy;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import com.url.shortner.secure_smart_url_shortener.repo.UrlRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.Map;

public class UrlRedirectService {

    @Autowired
    private UrlRecordRepo urlRecordRepo;

    @Autowired
    private Map<String, IUrlAccessStrategy> strategyMap;

    public String redirect(String shortCode, Authentication authentication) {
        UrlRecord record = urlRecordRepo.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        // If expiry time is present and current time is exceed expiry time
        if (record.getExpiryTime() != null && Instant.now().isAfter(record.getExpiryTime())) {
            throw new RuntimeException("URL expired");
        }

        // if click reached max count
        if (record.getMaxClicks() != null && record.getClickCount() >= record.getMaxClicks()) {
            throw new RuntimeException("Max clicks exceeded");
        }

        // Create object of PUBLIC/PRIVATE/ROLE_BASED based on user selected access type
        IUrlAccessStrategy strategy = strategyMap.get(record.getAccessType());
        // if not accessible the requirement
        if (!strategy.isAccessible(record, authentication)) {
            throw new RuntimeException("Access denied");
        }

        // If strategy is matched then increament count because bean is created
        record.setClickCount(record.getClickCount() + 1);
        urlRecordRepo.save(record);

        return record.getOriginalUrl();
    }
}
