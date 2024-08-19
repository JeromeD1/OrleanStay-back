package com.formation.orleanStay.cloudinary;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface CloudinaryService {
    Map<String, String> createSignature(String publicId, boolean overwrite);

    Map deleteImage(String publicId) throws IOException;
}
