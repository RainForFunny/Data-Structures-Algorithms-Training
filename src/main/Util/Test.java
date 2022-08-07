package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Test {

    public static void main(String[] args) throws IOException {

        // 可以获取每个工程的文件流，并对每个工程建立一个以工程名称命名的文件夹，将文件流放进去，然后将所有的文件夹放到一个目录下，将该目录打包
        File file1 = new File("G:/第一个文件.txt");
        File file2 = new File("G:/第二个文件.txt");
        File file3 = new File("G:/第三个文件.txt");
        File folder1 = new File("G:/第一个目录");
        File folder2 = new File("G:/第二个目录");
        // 将三个文件复制到两个文件夹下
        ArrayList<File> fileList = new ArrayList<>();
        fileList.add(file1);
        fileList.add(file2);
        for (File file : fileList) {
            copy1(file, folder1);
        }
        copy1(file3, folder2);
        // 将两个文件夹打包
        ArrayList<File> filepathList = new ArrayList<>();
        filepathList.add(folder1);
        filepathList.add(folder2);
        zipFile(filepathList);
        folder1.delete();
        folder2.delete();
// shear(file, file2);        //调用剪切方法

    }

    private static void zipFile(ArrayList<File> filepathList) {
        String zipName = "test-" + System.currentTimeMillis() + ".zip";
        String fileZip = "G:/" + zipName;
        OutputStream os=null;
        ZipOutputStream zos = null ;
        File file = new File(fileZip);

        try {
            os = new FileOutputStream(file);
            zos = new ZipOutputStream(os);
            String baseDir = "";
            byte[] buf = new byte[1024];
            for (File tempFile : filepathList) {
                if (tempFile.isDirectory()) {
                    String basePath = baseDir + tempFile.getName() + "/";
                    System.out.println("压缩："  + basePath);
                    File[] files = tempFile.listFiles();
                    for (File file1 : files) {
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file1));
                        String s = basePath + file1.getName();
                        zos.putNextEntry(new ZipEntry(s));
                        int len;
                        System.out.println("压缩：" + s);
                        while ((len = in.read(buf)) != -1){
                            zos.write(buf, 0, len);
                        }
                        in.close();
                    }
                }
                zos.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
}