package com.rh.cloudcampus.edu.util;

import java.io.*;

public class FileUtilService {
    /**
     * 服务器上传文件
     * @param filePath
     * @param fileName
     * @return
     */
    public boolean uploadFile(InputStream file, String filePath, String fileName) {
        boolean flag = true;
        //new一个文件对象实例
        File targetFile = new File(filePath);
        //如果当前文件目录不存在就自动创建该文件或者目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            //通过文件流实现文件上传
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
            byte[] b = new byte[file.available()];
            int n;
            while ((n = file.read(b)) != -1)
            {
                fileOutputStream.write(b, 0, n);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            flag = false;
        } catch (IOException ioException) {
            flag = false;
        }
        return flag;
    }

    /**
     * 服务器删除文件
     */
    public static boolean deleteFile(String filePath, String fileName) {
        boolean delete_flag = false;
        File file = new File(filePath + fileName);
        if (file.exists() && file.isFile() && file.delete()) {
            delete_flag = true;
        } else {
            delete_flag = false;
        }
        return delete_flag;
    }
}
