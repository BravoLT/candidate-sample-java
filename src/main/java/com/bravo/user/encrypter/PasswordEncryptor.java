package com.bravo.user.encrypter;

import com.bravo.user.utils.AESUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {

    @Value("${app.password.secret.key}")
    private String secretKey;

    public String encryptPassword(String plainText) {
        return AESUtils.encrypt(plainText, secretKey);
    }

    public String decryptPassword(String encryptedPassword) {
        return AESUtils.decrypt(encryptedPassword, secretKey);
    }
}
