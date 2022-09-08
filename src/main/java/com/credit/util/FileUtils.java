package com.credit.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUtils {
   private String fileName;
   private String path;
   private String visitPath;

}
