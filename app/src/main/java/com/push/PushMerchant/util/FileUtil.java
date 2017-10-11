package com.push.PushMerchant.util;

import android.os.Environment;
import android.text.TextUtils;


import com.push.PushMerchant.base.PushApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FileUtil
 * @Description:
 * @author: YYL
 * <p/>
 * create at 2016/9/7
 */
public class FileUtil {
    // 有sd卡时应用程序的存储根目录
    private static final String APP_SDCARD_AMOUNT_ROOT_PATH = "/jiaoao/lzb";
    // 无sd卡时应用程序的存储根目录
    private static final String APP_SDCARD_UNAMOUNT_ROOT_PATH = "/lzb";
    // 图片保存路径
    private static final String IMAGE_PATH = "/image";
    // 插件安装包名
    private static final String PLUGIN_DIR_NAME = "plugin";


    public static String getRootDir() {
        String dirPath = null;

        // 判断SDCard是否存在并且是可用的
        if (isSDCardExistAndCanWrite()) {
            dirPath = Environment.getExternalStorageDirectory().getPath();
        } else {
            dirPath = PushApplicationContext.context.getFilesDir().getAbsolutePath();
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    // 判断SDCard是否存在并且是可写的
    public static boolean isSDCardExistAndCanWrite() {
        boolean result = false;
        try {
            result = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().canWrite();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.exists();
    }


    public static String getImagePath() {
        return getCommonPath(IMAGE_PATH);
    }


    /**
     * 获取插件目录
     * @return
     */
    public static String getPlushPath() {
        return getCommonPath(PLUGIN_DIR_NAME);
    }

    /**
     * 获取常驻文件路径 常驻根目录 + 业务所需路径
     *
     * @param path
     * @return
     */
    public static String getCommonPath(String path) {
        final String rootDir = getCommonRootDir();
        String fullPath = null;
        if (!TextUtils.isEmpty(path)) {
            fullPath = rootDir + path;
        } else {
            fullPath = rootDir;
        }
        return getPath(fullPath, false);
    }

    /**
     * 获取程序运行期间文件保存的根目录
     *
     * @return SD卡可用的时候返回的是 /mnt/sdcard/lushow/showbuyer，SD卡不可用返回的是内存的路径data/data
     * /packagename/files
     */
    public static String getCommonRootDir() {
        String dirPath = null;
        // 判断SDCard是否存在并且是可用的
        if (isSDCardExistAndCanWrite()) {
            dirPath = Environment.getExternalStorageDirectory().getPath() + APP_SDCARD_AMOUNT_ROOT_PATH;
        } else {
            try {
                dirPath = PushApplicationContext.context.getFilesDir().getAbsolutePath()
                        + APP_SDCARD_UNAMOUNT_ROOT_PATH;
            } catch (Exception e) {
                return null;
            }
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 指定路径创建目录, 并提供指定momedia的接口
     *
     * @param nomedia : 是否需要nomedia文件，只有存图片的目录需要，有nomedia图片在相册不可见
     * @return 完整路径
     */
    public static String getPath(String path, boolean nomedia) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            if (nomedia) {
                File nomediaFile = new File(path + File.separator + ".nomedia");
                try {
                    nomediaFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }


    /**
     * @desc 获取指定目录所有文件的大小
     * @date 2016/9/14 15:23
     * @author zzh
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        if (files != null) {

            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getDirSize(file); // 递归调用继续统计
                }
            }
        }
        return dirSize;
    }


    /**
     * @desc 清空一个文件夹
     * @date 2016/9/14 15:29
     * @author zzh
     */
    public static void clearFileWithDir(File fileDir) {
        if (fileDir == null) {
            return;
        }
        if (!fileDir.isDirectory()) {
            return;
        }
        File[] files = fileDir.listFiles();

        for (File file :
                files) {
            if (file.isDirectory()) {
                clearFileWithDir(file);
                file.delete();
            } else {
                file.delete();
            }
        }
    }


    /**
     * @desc 转换文件大小
     * @date 2016/9/14 15:48
     * @author zzh
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            fileSizeString = "0KB";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * @desc 获取一个文件夹下的所有文件
     * @date 2016/9/14 15:31
     * @author zzh
     */
    public static List<File> listPathFiles(String root) {
        List<File> allDir = new ArrayList<File>();
        File path = new File(root);
        File[] files = path.listFiles();
        for (File f : files) {
            if (f.isFile())
                allDir.add(f);
            else
                listPath(f.getAbsolutePath());
        }
        return allDir;
    }


    /**
     * @desc 列出root目录下所有子目录
     * @date 2016/9/14 15:31
     * @author zzh
     */
    public static List<String> listPath(String root) {
        List<String> allDir = new ArrayList<String>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        // 过滤掉以.开始的文件夹
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory() && !f.getName().startsWith(".")) {
                    allDir.add(f.getAbsolutePath());
                }
            }
        }
        return allDir;
    }
}
