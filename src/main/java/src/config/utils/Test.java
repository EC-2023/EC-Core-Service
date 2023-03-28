package src.config.utils;

import java.io.File;

public class Test {
    public static void main(String[] args) {

        File file = new File(System.getProperty("user.dir"),"uploads/1680012172041_009b2c88-7adf-402e-964e-70c61dd2ea76.png");
        file.delete();
        System.out.println(System.currentTimeMillis());
    }
}
