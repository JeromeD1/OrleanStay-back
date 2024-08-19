package com.formation.orleanStay.cloudinary;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/cloudinary")
public class CloudinaryController {

    private CloudinaryService cloudinaryService;

    @GetMapping("/signature")
    public ResponseEntity<Map<String, String>> getSignature(
            @RequestParam(required = false) String publicId,
            @RequestParam(defaultValue = "false") boolean overwrite) {
        Map<String, String> signatureData = cloudinaryService.createSignature(publicId, overwrite);
        System.out.println("publicId : " + publicId + " overwrite : " + overwrite);
        return ResponseEntity.ok(signatureData);
    }
}
