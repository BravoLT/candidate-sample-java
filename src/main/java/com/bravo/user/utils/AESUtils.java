package com.bravo.user.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@UtilityClass
public class AESUtils {

    @SneakyThrows
    public  SecretKeySpec setKey(String myKey)
    {
        byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
        key = MessageDigest.getInstance("SHA-1").digest(key);
        key = Arrays.copyOf(key, 16);
        return new SecretKeySpec(key, "AES");
    }

    @SneakyThrows
    public String encrypt(String strToEncrypt, String secret)
    {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, setKey(secret));
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    public String decrypt(String strToDecrypt, String secret)
    {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, setKey(secret));
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
}
