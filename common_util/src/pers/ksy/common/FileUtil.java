package pers.ksy.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtil {
    private static final int RESOUORCE = 0;
    private static final int USER_DIR = 1;

    public static int lineCount(File file) throws IOException {
        int count = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] c = new byte[1024];
            int readChars = 0;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
            }
        } finally {
            if (null != is) {
                is.close();
            }
        }
        return count;
    }

    public static InputStream findFileFromRootAsStream(String path) {
        return FileUtil.class.getResourceAsStream(path);
    }

    public static File findFile(String path) throws IOException {
        return findFile(path, 0);
    }

    public static File findFile(String path, int rootPath) throws IOException {
        URL url = null;
        File file = null;
        if (0 == rootPath) {
            url = FileUtil.class.getResource("/");
        } else if (1 == rootPath) {
            file = new File(System.getProperty("user.dir") + "/" + path);
        }
        try {
            if (null != url) {
                File root = new File(url.toURI());
                file = new File(root, path);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (null == file || !file.exists()) {
            throw new IOException("!!!not find " + file.getAbsolutePath());
        }
        return file;
    }

    public static void copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    public static void copy(File oldfile, File newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            // File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    public static String getPrefix(String path) {
        String prefix = path.substring(path.lastIndexOf(".") + 1);
        return prefix;
    }
    
    public static String getPrefix(File file) {
        return getPrefix(file.getAbsoluteFile().getPath());
    }
    
    

}
