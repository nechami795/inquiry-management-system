package Business;

import Data.*;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.*;
import java.util.*;

public class RepresentativeManager {
    static Queue<Representative> availableRepresentatives;
    private static RepresentativeManager instance;

    private RepresentativeManager() { }

    static Scanner scanner = new Scanner(System.in);

    static {
        availableRepresentatives = new LinkedList<>();
        loudRepresentative();
        System.out.println(availableRepresentatives.size());
    }

    public static RepresentativeManager getInstance() {
        if (instance == null)
            instance = new RepresentativeManager();
        return instance;
    }

    public Queue<Representative> getAvailableRepresentatives(){
        return availableRepresentatives;
    }

    public static Representative createRepresentative() {
        System.out.println("insert Representative Id");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("insert Representative name");
        String name = scanner.nextLine();
        Representative representative = new Representative(id, name);
        return representative;
    }

    public static void loudRepresentative() {
        File directory = new File("Representative");
        HandleFiles handleFiles = new HandleFiles();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    IForSaving newObj = handleFiles.readFile(file);
                    Representative representative = (Representative) newObj;
                    System.out.println("prees 1 to loud " + representative.getName());
                    System.out.println("prees 2 to delete " + representative.getName());
                    int answer = scanner.nextInt();
                    switch (answer) {
                        case 1:
                            availableRepresentatives.add(representative);
                            break;
                        case 2:
                            handleFiles.deleteFile(newObj);
                            break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        String ans;
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("do you want to add Representative? press y/n");
        ans = scanner1.nextLine();
        System.out.println(ans);
        while (ans.equals("y")) {
            System.out.println(";lkjhgfdcs");
            Representative representative = createRepresentative();
            handleFiles.saveFile(representative);
            availableRepresentatives.add(representative);
            System.out.println("do you want to add Representative? press y/n");
            ans = scanner1.nextLine();
        }
    }
    public List<Inquiry> getAllInquiriesByRepresentative(Integer representativeID) {
        List<Inquiry> result = new ArrayList<>();
        HandleFiles handleFiles = new HandleFiles();
        File file = new File("History");
        File[] yearFolders = file.listFiles();
        for (File yearFolder : yearFolders) {
            File[] monthFolders = yearFolder.listFiles();
            for (File monthFolder : monthFolders) {
                File[] inquiries = monthFolder.listFiles();
                for (File inquiryFile : inquiries) {
                    try {
                        IForSaving iForSaving = handleFiles.readFile(file);
                        if (representativeID.equals((iForSaving.getData().split(",")[4])))
                            result.add((Inquiry)iForSaving);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public Map<Integer,Inquiry> getAllRepresentatives(){
        InquiryManager inquiryManager=InquiryManager.getInstance();
        Map<Integer,Inquiry>inquiryHandlingMap=inquiryManager.getInquiryHandlingMap();
        Map<Integer,Inquiry>activeRepresentatives=new HashMap<>();
        for(Inquiry inquiry:inquiryHandlingMap.values()){
            if(inquiry.getStatus()==InquiryStaus.IN_HANDLING){
                activeRepresentatives.put(inquiry.getRepresentativeID(),inquiry);
            }
        }
        return  activeRepresentatives;
    }

}
