import java.io.File;

public class UpdateFileName {
    //记录你要修改的文件夹，即文件所存放的文件夹路径，字符串最后加上\\表示此文件夹下
    static String path = "C:\\Users\\86186\\Desktop\\img\\9-14\\";

    public static void main(String[] args) {
        String filePath = "";
        String fileName = "";
        for (int i = 390; i < 590; i++) {
            //通过字符串拼接，拼接出原文件的绝对路径,此处的i以及下文的k和j都是修改文件名的动态字符
            filePath = path + "Img_102" + String.valueOf(i) + ".jpg";
            System.out.println("filePath:" + filePath);
            //按顺序找到原文件
            File file = new File(filePath);
            System.out.println(file.getName());
            int j = 556;
            int k = 1;
            while (file.length() != 0) {
                k = 1;
                while (k <= 2) {
                    //按照字符串拼接，拼接处修改后的文件的绝对路径
                    fileName = path + "2-" + String.valueOf(j) + "-" + String.valueOf(k) + ".jpg";
                    System.out.println("fileName:" + fileName);
                    //修改文件
                    file.renameTo(new File(fileName));

                    k++;
                }
                j++;
            }
        }
    }
}
