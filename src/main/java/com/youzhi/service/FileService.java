package com.youzhi.service;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileService {

    public void fileChannelCopy(File s, File t) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fis = new FileInputStream(s);
            fos = new FileOutputStream(t);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (null != fis) fis.close();
                if (null != fos) fos.close();
                if (null != in) in.close();
                if (null != out) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
