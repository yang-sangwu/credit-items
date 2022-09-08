package com.credit.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FileType {
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
    private FileType(){}
    static{
        getAllFileType(); // 初始化文件类型信息
    }
    /**
     * getAllFileType,需要的文件头信息
     */
    private static void getAllFileType(){
        FILE_TYPE_MAP.put("JPG", "ffd8ffe000104a464946"); // JPEG (jpg)
        FILE_TYPE_MAP.put("PNG", "89504e470d0a1a0a0000"); // PNG (png)
        FILE_TYPE_MAP.put("GIF", "47494638"); // GIF (gif)47494638396126026f01
        FILE_TYPE_MAP.put("DOC", "d0cf11e0a1b11ae10000"); // MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("PDF", "255044462d312e");
    }
    /**
     * 得到上传文件的文件头
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if(src == null || src.length <= 0){
            return null;
        }
        int tempLength=100;
        if(src.length<100){
            tempLength=src.length;
        }
        for(int i = 0; i < tempLength; i++){
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if(hv.length() < 2){
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据制定文件的文件头判断其文件类型
     *
     * @param
     * @return
     */
    public static boolean getFileType(String fileType, byte[] fileBytes){
        boolean result = false;
        String fileCode = bytesToHexString(fileBytes).toLowerCase(Locale.ENGLISH);
        String fileSourceCode = FILE_TYPE_MAP.get(fileType.toLowerCase(Locale.ENGLISH));
        if(fileSourceCode != null){	if(fileSourceCode.toLowerCase(Locale.ENGLISH).startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase(Locale.ENGLISH).startsWith(fileSourceCode.toLowerCase())){
            result = true;
        }
        }
        return result;
    }
}
