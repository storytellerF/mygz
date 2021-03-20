package com.storyteller_f;

import java.io.File;
import java.util.Locale;

public class FileURIUtility {

    public static String getExtensions(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
    }
}
