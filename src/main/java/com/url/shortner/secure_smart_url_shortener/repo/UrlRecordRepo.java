package com.url.shortner.secure_smart_url_shortener.repo;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRecordRepo extends JpaRepository<UrlRecord, Long> {
    boolean existsByShortCode(String shortCode);
}
