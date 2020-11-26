package org.example;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Files.*;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FooUtils {
    public static boolean CopyFile(String sourcePath,String targetPath) throws IOException
    {
        File inFile = new File(sourcePath);
        if(!inFile.exists())
        {
            return false;
        }
        File outFile = new File(targetPath);
        File parentFile = outFile.getParentFile();
        if(!parentFile.exists())
        {
            boolean bFlag = parentFile.mkdirs();
            System.out.println(bFlag);
        }
        if(!outFile.exists())
        {
            outFile.createNewFile();
        }


        try (
                FileInputStream fileInputStream = new FileInputStream(inFile);
                FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                FileChannel inChannel = fileInputStream.getChannel();
                FileChannel outChannel = fileOutputStream.getChannel();
                )
        {
            inChannel.transferTo(0,inFile.length(),outChannel);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        if (inFile.length() != outFile.length()) {
            return false;
        } else {
            return true;
        }
    }

}
