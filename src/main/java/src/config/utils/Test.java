package src.config.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

public class Test {
    public static void main(String[] args) {

        File file = new File(System.getProperty("user.dir"),"uploads/1680012172041_009b2c88-7adf-402e-964e-70c61dd2ea76.png");
        file.delete();
        Resource fileResource =new FileSystemResource("/uploads/" + "1680013060663-5d61e208-3bd9-48a8-ae2e-b2f47d17687d.ico");
        System.out.println(fileResource.getFilename());
    }
}
