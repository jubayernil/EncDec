package com.example.jubayernil.encdec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView outputTextTv, fileStateTv;
    EditText inputTextEt;

    String data;
    private String fileName = "File-50.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTextTv = (TextView) findViewById(R.id.outputTextTv);
        fileStateTv = (TextView) findViewById(R.id.fileStateTv);

        inputTextEt = (EditText) findViewById(R.id.inputTextEt);
    }

    public void saveText(View view) {
        data = inputTextEt.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_WORLD_READABLE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showText(View view) {
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            int c;
            String temp="";
            while( (c = fileInputStream.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            file = new File(fileName);

            outputTextTv.setText(temp+"\n"+file.getAbsolutePath());

            Toast.makeText(MainActivity.this, "Reading...", Toast.LENGTH_SHORT).show();
            Log.d("filePath", "showText: "+file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encryptFile(View view) {
        try {
            String yourFilePath = getApplicationContext().getFilesDir() + "/" + fileName;
            new FileEncryptor("DES/ECB/PKCS5Padding", yourFilePath).encrypt();
            file = new File(yourFilePath);
            file.delete();
            fileStateTv.setText("Encrypted");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("bol", "encryptFile: "+e);
        }
    }

    public void decryptFile(View view) {
        try {
            String yourFilePath = getApplicationContext().getFilesDir() + "/" + fileName+".enc";
            new FileEncryptor("DES/ECB/PKCS5Padding", yourFilePath).decrypt();
            file = new File(yourFilePath);
            file.delete();
            fileStateTv.setText("Decrypted");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("lol", "decryptFile: "+e);
        }
    }
}
