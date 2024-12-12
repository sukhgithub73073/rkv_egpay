package com.app.rkvmoneyrecharge.enc;

import android.util.Base64;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class EncDecRepository {

    private static final String pass = "etop@123";

    public static Map<String, String> getEncryption(Map<String, Object> model) {
        Map<String, String> encryptedRequest = new HashMap<>();
        try {
            String key = getSha256FromStringNew(pass, 16);
            String iv = generateIVNew(16);

            String jsonCall = new JSONObject(model).toString();
            byte[] encryptedData = encryptAESNew(jsonCall, key.getBytes(StandardCharsets.US_ASCII), iv.getBytes(StandardCharsets.US_ASCII));

            String enc = Base64.encodeToString(encryptedData, Base64.DEFAULT);
            encryptedRequest.put("IV", iv);
            encryptedRequest.put("encrypted", enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedRequest;
    }

    public static String getDecryption(String decriptedString , String IV) {
        String decryptData = "";
        try {
            String key = getSha256FromStringNew(pass, 16);
            byte[] encryptedBytes = Base64.decode(decriptedString, Base64.DEFAULT);
            decryptData = decryptAESNew(encryptedBytes, key.getBytes(StandardCharsets.US_ASCII),IV.getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptData;
    }

    private static String generateIVNew(int length) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16]; // AES block size is 16 bytes
        secureRandom.nextBytes(iv);
        StringBuilder result = new StringBuilder();
        for (byte b : iv) {
            result.append(String.format("%02x", b));
        }
        return result.substring(0, Math.min(length, result.length()));
    }

    private static String getSha256FromStringNew(String strData, int length) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(strData.getBytes(StandardCharsets.US_ASCII));
        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }
        return result.substring(0, Math.min(length, result.length()));
    }

    private static byte[] encryptAESNew(String plainText, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    private static String decryptAESNew(byte[] cipherText, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(cipherText);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
