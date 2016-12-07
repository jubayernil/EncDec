package com.example.jubayernil.encdec;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jubayernil on 12/6/16.
 */
public class FileEncryptor {
    private String algo;
    private String path;

    public FileEncryptor(String algo, String path) {
        this.algo = algo; // setting algo
        this.path = path;// setting file path
    }

    public void encrypt() throws Exception {
        // generating key
        byte k[] = "HignDlPs".getBytes();
        SecretKeySpec key = new SecretKeySpec(k, algo.split("/")[0]);
        // creating and initialising cipher and cipher streams
        Cipher encrypt = Cipher.getInstance(algo);
        encrypt.init(Cipher.ENCRYPT_MODE, key);
        // opening streams
        FileOutputStream fos = new FileOutputStream(path + ".enc");
        try (FileInputStream fis = new FileInputStream(path)) {
            try (CipherOutputStream cout = new CipherOutputStream(fos, encrypt)) {
                copy(fis, cout);
                Log.d("encr", "encrypt: "+"Encryption running...");
            }
        }
    }

    public void decrypt() throws Exception {
        // generating same key
        byte k[] = "HignDlPs".getBytes();
        SecretKeySpec key = new SecretKeySpec(k, algo.split("/")[0]);
        // creating and initialising cipher and cipher streams
        Cipher decrypt = Cipher.getInstance(algo);
        decrypt.init(Cipher.DECRYPT_MODE, key);
        // opening streams
        FileInputStream fis = new FileInputStream(path);
        try (CipherInputStream cin = new CipherInputStream(fis, decrypt)) {
            try (FileOutputStream fos = new FileOutputStream(path.substring(0, path.lastIndexOf(".")))) {
                copy(cin, fos);
                Log.d("decr", "encrypt: "+"Decryption running...");
            }
        }
    }

    private void copy(InputStream is, OutputStream os) throws Exception {
        byte buf[] = new byte[4096]; // 4K buffer set
        int read = 0;
        while ((read = is.read(buf)) != -1) // reading
            os.write(buf, 0, read); // writing
    }

}
