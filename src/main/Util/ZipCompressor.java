package Util;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressor {
    static final int BUFFER = 8192;

    private File zipFile;

    public ZipCompressor(String pathName) {
        zipFile = new File(pathName);
    }
    public void compress(String... pathName) {
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (String s : pathName) {
                compress(new File(s), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void compress(String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists()) {
            throw new RuntimeException(srcPathName + "不存在！");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "";
            compress(file, out, basedir);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            System.out.println("压缩：" + basedir + file.getName());
            this.compressDirectory(file, out, basedir);
        } else {
            System.out.println("压缩：" + basedir + file.getName());
            this.compressFile(file, out, basedir);
        }
    }

    /** 压缩一个目录 */
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        for (File file : files) {
            /* 递归 */
            compress(file, out, basedir + dir.getName() + "/");
        }
    }

    /** 压缩一个文件 */
    private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File asFile(InputStream inputStream) throws IOException{
        File tmp = File.createTempFile("临时名称", "临时后缀.tmp", new File("文件目录"));
        OutputStream os = new FileOutputStream(tmp);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        return tmp;
    }

    /**
     * 将多个文件复制到一个新的文件夹中
     * @param file 文件
     * @param copy 文件夹
     * @throws IOException 异常
     */
    public static void copy1(File file, File copy) throws IOException {
        copy.mkdirs();
        //得到要被复制的文件夹下所有内容，放进文件数组中
        //查看该对象是否是文件
        if (file.isFile()) {
            //读入该对象
            FileInputStream stream = new FileInputStream(file);
            //写出该对象到   copy+"/"+file.getName()   路径
            FileOutputStream stream2 = new FileOutputStream(copy + "/" + file.getName());
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = stream.read(bytes)) != -1) {
                stream2.write(bytes, 0, len);
            }
            stream.close();
            stream2.close();
        } else {
            File file2 = new File(copy + "/" + file.getName());
            copy(file, file2);//递归
        }
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            // commons-io
            //IOUtils.copy(inputStream, outputStream);

        }

    }

    //两个参数中，前者代表要被复制的文件夹名称，后者是一个不存在的文件夹，自定义命名
    public static void copy(File copied, File copy) throws IOException {
        copy.mkdirs();
        //得到要被复制的文件夹下所有内容，放进文件数组中
        File[] copiedFiles = copied.listFiles();
        for (File file : copiedFiles) {
            //查看该对象是否是文件
            if (file.isFile()) {
                //读入该对象
                FileInputStream stream = new FileInputStream(file);
                //写出该对象到   copy+"/"+file.getName()   路径
                FileOutputStream stream2 = new FileOutputStream(copy + "/" + file.getName());
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = stream.read(bytes)) != -1) {
                    stream2.write(bytes, 0, len);
                }
                stream.close();
                stream2.close();
            } else {
                File file2 = new File(copy + "/" + file.getName());
                copy(file, file2);//递归
            }
        }
    }


//剪切方法：

    public static void shear(File cut, File copy) throws IOException {
        copy.mkdirs();
        File[] copiedFiles = cut.listFiles();
        for (File file : copiedFiles) {
            if (file.isFile()) {
                FileInputStream stream = new FileInputStream(file);
                FileOutputStream stream2 = new FileOutputStream(copy + "/" + file.getName());
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = stream.read(bytes)) != -1) {
                    stream2.write(bytes, 0, len);
                }
                stream.close();
                stream2.close();
                file.delete();//与复制方法类似，只是在每次复制之后将原文件删除
            } else {
                File file2 = new File(copy + "/" + file.getName());
                shear(file, file2);
            }
        }
        cut.delete();//此时文件夹为空，再将文件夹删除
    }

    public static void main(String[] args) {
        ZipCompressor zc = new ZipCompressor("G:/resource.zip");
        zc.compress("G:/第一个目录","G:/第二个目录");
    }
}
