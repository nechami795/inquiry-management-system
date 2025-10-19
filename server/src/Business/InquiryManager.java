package Business;

import Data.*;
import HandleStoreFiles.*;

import java.io.File;
import java.time.Month;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InquiryManager {
    public static Integer nextCodeVal = 0;
    private static InquiryManager instance;
    private  Scanner scanner = new Scanner(System.in);
    private boolean isInquiryCreationActive = true;
    HandleFiles handleFiles=new HandleFiles();
    private static final BlockingQueue<Inquiry> queue ;
    private Map<Integer,Inquiry>inquiryHandlingMap=new HashMap<>();
    public Map<Integer, Inquiry> getInquiryHandlingMap() {
        return inquiryHandlingMap;
    }
    public void setInquiryHandlingMap(Map<Integer, Inquiry> inquiryHandlingMap) {
        this.inquiryHandlingMap = inquiryHandlingMap;
    }
    static {
        queue=new LinkedBlockingQueue<>();
        updateNextCodeVal();
        loadInquiry();
    }
    private InquiryManager() {
    }

    public static InquiryManager getInstance(){
        if (instance==null)
            instance=new InquiryManager();
        return instance;
    }
    private static void updateNextCodeVal() {
        int max = 0;
        File file = new File("History");
        if(file.exists()) {
            File[] years = file.listFiles();
            for (File year : years) {
                File[] months = year.listFiles();
                for (File month : months) {
                    File[] inquiries = month.listFiles();
                    for (File inquiryFile : inquiries) {
                        if (Integer.parseInt(inquiryFile.getName().split(" ")[1]) > max)
                            max = Integer.parseInt(inquiryFile.getName().split(" ")[1]);
                    }
                }
            }
        }
        nextCodeVal=Math.max(nextCodeVal,max+1);
    }
    private static void loadInquiry(){
        int max=nextCodeVal;
        String [] namesFolder = {"Question","Request","Complaint"};
        HandleFiles handleFiles=new HandleFiles();
        for(String folder : namesFolder){
            File directory = new File(folder);
            File [] files = directory.listFiles();
            if (files!=null){
                for(File file :files){
                    try{
                        IForSaving newObj = handleFiles.readFile(file);
                        Inquiry inquiry=(Inquiry)newObj;
                        inquiry.setCode(Integer.valueOf(file.getName()));
                        queue.add(inquiry);
                        max=Math.max(inquiry.getCode(),max);

                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        nextCodeVal=max;
    }

    public int getNumberOfInquiriesInMonth(int year, int monthNum) {

        if (monthNum < 1 || monthNum > 12) {
            throw new IllegalArgumentException("מספר חודש לא חוקי. צריך להיות בין 1 ל-12.");
        }
        Month month = Month.of(monthNum);
        int count = 0;
        File monthDir = new File("History" + File.separator + year + File.separator + month);

        if (!monthDir.exists() || !monthDir.isDirectory())
            return 0;

        File[] files = monthDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile())
                    count++;
            }
        }
        return count;
    }

    public InquiryStaus getInquiryStatus(int inquiryId){
        HandleFiles handleFiles=new HandleFiles();
        Inquiry inquiry = inquiryHandlingMap.get(inquiryId);
        InquiryStaus status=null;
        if(inquiry!=null)
            status =  inquiry.getStatus();
        File file=new File("History");
        File[]years=file.listFiles();
        for(File year :years)
        {
            File[] months= year.listFiles();
            for (File month : months)
            {
                File [] inquiries = month.listFiles();
                for(File inquiryFile : inquiries)
                {
                    if (Integer.parseInt(inquiryFile.getName().split(" ")[1]) == inquiryId)
                    {
                        inquiry = (Inquiry) handleFiles.readFile(inquiryFile);
                        status = inquiry.getStatus();
                    }
                }
            }
        }

        return status;

    }
    public Representative ReturnRepresentativeByInquiryId(int inquiryId){
        HandleFiles handleFiles=new HandleFiles();

        Inquiry inquiry=inquiryHandlingMap.get(inquiryId);
        if(inquiry==null){
            File file = new File("History");
            File[] yearFolders = file.listFiles();
            for (File yearFolder : yearFolders) {
                File[] monthFolders = yearFolder.listFiles();
                for (File monthFolder : monthFolders) {
                    File[] inquiries = monthFolder.listFiles();
                    for (File inquiryFile : inquiries) {
                        if(Integer.parseInt(inquiryFile.getName().split(" ")[1])==inquiryId){
                            inquiry=(Inquiry)handleFiles.readFile(inquiryFile);
                        }
                    }
                }
            }
        }
        if(inquiry==null)
            return null;
        Representative representative=null;
        File directory=new File("Representative");
        File[]representativeFiles=directory.listFiles();
        for(File f:representativeFiles){
            if(Integer.parseInt(f.getName())==inquiry.getRepresentativeID()) {
                representative = (Representative) handleFiles.readFile(f);
            }
        }
        return representative;
    }
    public void inquiryCreation() {
        Inquiry currentInquiry=null;
        System.out.println("chose number:" +
                "1:Question,  2:Request, 3:Complaint");
        String choose = scanner.nextLine();
        while (!choose.equals("exit")) {
            switch (choose) {
                case "1":
                    currentInquiry=new Question();
                    break;
                case "2":
                    currentInquiry=new Request();
                    break;
                case "3":
                    currentInquiry=new Complaint();
                    break;
                default:
                    System.out.println("error");
            }
            handleFiles.saveFile(currentInquiry);
            currentInquiry.setCode(nextCodeVal++);
            queue.add(currentInquiry);

            System.out.println("chose number:" +
                    "1:Question,  2:Request, 3:Complaint");
            choose = scanner.nextLine();
        }

        isInquiryCreationActive = false;
        System.exit(0);
    }
//    public void processInquiryManager() {
//
//        while (isInquiryCreationActive) {
//            try {
//                Inquiry currentInquiry = queue.take();
//                if (currentInquiry != null) {
//                    InquiryHandling inquiryHandling = new InquiryHandling(currentInquiry);
//                    inquiryHandling.start();
//                    handleFiles.moveInquiryToHistory(currentInquiry);
//                }
//            } catch (InterruptedException e) {
//                System.out.println("Error processing inquiry: " + e.getMessage());
//                Thread.currentThread().interrupt();
//            }
//        }
//
//    }
    public Queue<Inquiry> allInquiry(){
        return queue;
    }
    public void addInquiry(Inquiry inquiry){
        inquiry.setCode(nextCodeVal++);
        handleFiles.saveFile(inquiry);
        System.out.println(inquiry.getData());
        inquiryHandlingMap.put(inquiry.getCode(),inquiry);
        queue.add(inquiry);
    }
    public void closeInquiry(Inquiry inquiry, Representative representative){
        inquiry.setStatus(InquiryStaus.HANDLED);
        new HandleFiles().moveInquiryToHistory(inquiry);
        inquiryHandlingMap.remove(inquiry.getCode());
        RepresentativeManager.getInstance().getAvailableRepresentatives().add(representative);
    }
    public boolean cancelInquiry(int inquiryId){
        if(inquiryHandlingMap.containsKey(inquiryId)
            && inquiryHandlingMap.get(inquiryId).getStatus() == InquiryStaus.OPEN)
        {
            Inquiry inquiry = inquiryHandlingMap.remove(inquiryId);
            inquiry.setStatus(InquiryStaus.CANCELED);
            new HandleFiles().moveInquiryToHistory(inquiry);
            return true;
        }
        return true;
    }
}
