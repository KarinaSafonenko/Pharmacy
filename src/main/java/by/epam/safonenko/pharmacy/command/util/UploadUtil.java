package by.epam.safonenko.pharmacy.command.util;

import by.epam.safonenko.pharmacy.exception.LogicException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class UploadUtil {
    private static final String CONTENT_HEADER = "content-disposition";
    private static final String FILENAME = "filename";

    public static String load(Part part, String uploadFilePath) throws LogicException {
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String fileName;
        fileName = getFileName(part);
        if (!fileName.isEmpty()) {
            String uploadPath = uploadFilePath + File.separator + fileName;
            File uploadedFile = new File(uploadPath);
            while (uploadedFile.exists()) {
                fileName = new Random().nextInt() + fileName;
                uploadPath = uploadFilePath + fileName;
                uploadedFile = new File(uploadPath);
            }
            try {
                part.write(uploadPath);
            } catch (IOException e) {
                throw new LogicException(e);
            }
            return fileName;
        }else{
            throw new LogicException("Image upload failed.");
        }
    }

    public static String getFileName(Part part) {
        if (part == null){
            return "";
        }
        String content = part.getHeader(CONTENT_HEADER);
        String[] parts = content.split(";");
        for (String current : parts) {
            if (current.trim().startsWith(FILENAME)) {
                return current.substring(current.indexOf("=") + 2, current.length()-1);
            }
        }
        return "";
    }
}
