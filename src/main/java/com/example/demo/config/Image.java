package com.example.demo.config;

public class Image {
    public static String detectMimeType(byte[] imageData) {
        if (imageData.length < 4) return "image/jpeg";

        // Check first few bytes
        if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
            return "image/jpeg";
        } else if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50 &&
                imageData[2] == (byte) 0x4E && imageData[3] == (byte) 0x47) {
            return "image/png";
        } else if (imageData[0] == (byte) 0x47 && imageData[1] == (byte) 0x49 &&
                imageData[2] == (byte) 0x46) {
            return "image/gif";
        } else if (imageData[0] == (byte) 0x52 && imageData[1] == (byte) 0x49 &&
                imageData[2] == (byte) 0x46 && imageData[3] == (byte) 0x46) {
            // Check for WEBP in positions 8-11
            if (imageData.length >= 12 &&
                    imageData[8] == (byte) 0x57 && imageData[9] == (byte) 0x45 &&
                    imageData[10] == (byte) 0x42 && imageData[11] == (byte) 0x50) {
                return "image/webp";
            }
        }

        return "image/jpeg"; // default fallback
    }
}
