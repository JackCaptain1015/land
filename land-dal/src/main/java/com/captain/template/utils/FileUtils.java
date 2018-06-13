package com.captain.template.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wurz on 2018/6/13.
 */
public class FileUtils {
    public static File createFile(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        return file;
    }

    public static void main(String[] args) {
        System.out.println(FileUtils.class.getResource("").getPath());
    }
}
