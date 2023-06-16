package com.hakimbooks.hakimbooks.utility;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class UploaderUtility {
    private final String ROOT_FILE_PATH=new ClassPathResource("static/images/").getFile().getAbsolutePath();

    public UploaderUtility() throws IOException {
    }

    public String upload(MultipartFile file) {
        try{

            // Get the Original name.
            String fileName=file.getOriginalFilename();
            if(fileName == null || fileName.isEmpty()) return null;

            // Take unique text
            String[] idList = UUID.randomUUID().toString().split("-");
            String uniqueText=idList[idList.length-1];

            // Get current date
            String date= LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Get file extension
            String[] fileNameList=fileName.split("\\.");
            String extension=fileNameList[fileNameList.length-1];

            // Create new fileName
            String newName=uniqueText + "-" + date + "." + extension;

            // Create full path
            String realPath=ROOT_FILE_PATH+ File.separator+newName;

            // Upload file
            Files.copy(file.getInputStream(), Paths.get(realPath), StandardCopyOption.REPLACE_EXISTING);

            return newName;
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public String getRealPath(String fileName){
        return ROOT_FILE_PATH + File.separator + fileName;
    }
}
