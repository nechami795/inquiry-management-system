package HandleStoreFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DeleteOldFiles extends Thread{
    private String folderName;
    private  int days;
    public DeleteOldFiles(String name,int day)
    {
        this.folderName=name;
        this.days=day;
    }
    @Override
    public  void run(){
        File file=new File(folderName);
        File[]files=file.listFiles();
        if(files!=null){
            for (File f: files) {
                if(f!=null) {
                    try {
                        BasicFileAttributes basicFileAttributes = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
                        Instant thresholdTime = Instant.now().minus(days, ChronoUnit.DAYS);
                        if (basicFileAttributes.creationTime().toInstant().isBefore(thresholdTime)) {
                            f.delete();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
