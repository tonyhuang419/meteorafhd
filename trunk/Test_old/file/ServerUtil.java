package file;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ServerUtil {
    public static Map getDeviceManagerMap() {
        Map dvmMap = new HashMap();
        String dirName = "file";
        try {
            URL dirUrl = getUrl(dirName);
           // System.out.println(dirUrl.getFile());
            File dir = new File(dirUrl.getFile());
            if (dir.isDirectory()) {
                File[] fileList = dir.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(".java")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                for (int i = 0; i < fileList.length; i++) {
                    System.out.println(fileList[i].getPath());
                }
            }
            return dvmMap;
        } catch (Exception ex) {
            return null;
        }
    }

    private static URL getUrl(String aName) {
        return ServerUtil.class.getClassLoader().getResource(aName);
    }

    public static void main(String[] args) {
        ServerUtil.getDeviceManagerMap();
    }
}