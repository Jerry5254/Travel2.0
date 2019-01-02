package com.niit.travel.util;

import java.io.File;

public class DeleteFileUtil {
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                deleteFile(new File(file, children[i]));
            }
        }
        file.delete();
    }
}
