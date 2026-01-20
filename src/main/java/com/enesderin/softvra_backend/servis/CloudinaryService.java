package com.enesderin.softvra_backend.servis;

public interface CloudinaryService {

    String uploadSignature(String base64Signature, String publicIdPrefix);

    void deleteByUrl(String imageUrl);
}

