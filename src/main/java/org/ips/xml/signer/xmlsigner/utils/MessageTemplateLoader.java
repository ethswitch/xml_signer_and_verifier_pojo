package org.ips.xml.signer.xmlsigner.utils;

import com.google.common.io.CharStreams;


import java.io.*;

public class MessageTemplateLoader {


    public InputStream loadFile(String filePath) {
        InputStream inputStream = null;

        File file = new File(filePath);

        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }


        return inputStream;

    }

    public String loadMessageString(String path){
        InputStream inputStream=loadFile(path);
        String messageString=null;
        try {
            messageString= CharStreams.toString(new InputStreamReader(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return messageString;

    }

}
