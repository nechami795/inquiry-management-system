package HandleStoreFiles;

import java.io.File;

public class FileRename extends Thread {
    private String directoryName;
    private String prefix;

    public FileRename(String directoryName, String prefix) {
        this.directoryName = directoryName;
        this.prefix = prefix;
    }

    @Override
    public void run() {
        File directory =new File(directoryName);
        if (!directory.exists()||!directory.isDirectory()){
            System.out.println("Invalid directory: " + directoryName);
            return;
        }
        File[] files=directory.listFiles();
        if (files==null)
            return;

        for (File file : files){
            if(file.isFile()){
                File newFile = new File(file.getParent(),prefix+file.getName());
                boolean isRename =file.renameTo(newFile);
                if(isRename)
                    System.out.println("Renamed: " + file.getName() + " -> " + newFile.getName());
                else
                    System.out.println("Failed to rename: " + file.getName());
            }
        }

    }
}
