package com.captain.template.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wurz on 2018/6/13.
 */
public class FileUtils {
    public static File createFile(String pathAndFileName) throws IOException {
        File file = new File(pathAndFileName);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }

        file.createNewFile();
        return file;
    }

    public static void main(String[] args) throws IOException {
        FileUtils.createFile(FileUtils.class.getResource("").getPath()+"test.xml");
        System.out.println(FileUtils.class.getResource("").getPath());
    }


}
