package com.xyzfood.service.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryUploadService {
    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    public String uploadFoodImage(MultipartFile file) throws IOException {
        if (isBlank(cloudName) || isBlank(apiKey) || isBlank(apiSecret)) {
            throw new IllegalStateException("Cloudinary chưa được cấu hình");
        }

        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", true);

        Cloudinary cloudinary = new Cloudinary(config);
        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", "xyzfood/foods", "resource_type", "image"));

        Object secureUrl = result.get("secure_url");
        if (secureUrl == null || secureUrl.toString().isBlank()) {
            throw new IllegalStateException("Cloudinary không trả về URL ảnh");
        }
        return secureUrl.toString();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
