package com.formation.orleanStay.cloudinary;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder}")
    private String cloudinaryFolder;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public Map<String, String> createSignature(String publicId, boolean overwrite) {
        long timestamp = System.currentTimeMillis() / 1000L;
        Map<String, Object> params = new TreeMap<>();
        params.put("timestamp", timestamp);
        //test
        params.put("source", "uw");

        if (publicId != null && !publicId.isEmpty()) {
            params.put("public_id", publicId);
        } else {
            params.put("folder", cloudinaryFolder);
        }
        if (overwrite) {
            params.put("overwrite", true);
            params.put("invalidate", true);
        }


        String signature = cloudinary.apiSignRequest(params, cloudinary.config.apiSecret);

        Map<String, String> result = new HashMap<>();
        result.put("signature", signature);
        result.put("timestamp", String.valueOf(timestamp));
        return result;
    }

    @Override
    public Map deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(convertImgId(publicId), ObjectUtils.emptyMap());
    }

    private String convertImgId(String imgId) {
        return imgId.replace("-", "/");
    }
}

