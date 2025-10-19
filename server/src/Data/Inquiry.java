package Data;
import HandleStoreFiles.IForSaving;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Inquiry implements IForSaving,Serializable {

    protected  Integer code;
    protected String description;
    protected LocalDateTime creationDate;
    protected List<String> documentNames;
    protected  String className;
    protected InquiryStaus status;
    protected int representativeID;
    private static final long serialVersionUID = 1234567L;
    private transient Scanner scanner=new Scanner(System.in);

    public  Inquiry(){
        creationDate=LocalDateTime.now();
        documentNames=new ArrayList<>();
        className=this.getClass().getName();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) { this.code = code; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public InquiryStaus getStatus() {
        return status;
    }

    public void setStatus(InquiryStaus status) {
        this.status = status;
    }

    public int getRepresentativeID() {
        return representativeID;
    }

    public void setRepresentativeID(int representativeID) {
        this.representativeID = representativeID;
    }

    public  void fillDataByUser(){
        System.out.println("insert description");
        String des=scanner.nextLine();
        this.description=des;
        System.out.println("do you have any documents to attach?");
        System.out.println("enter y for yes or n for no");
        String ans=scanner.nextLine();
        while(ans.equals("y")){
            System.out.println("enter the name of the document you want to attach.");
            documentNames.add(scanner.nextLine());
            System.out.println("do you have any documents to attach?");
            System.out.println("enter y for yes or n for no");
            ans=scanner.nextLine();
        }
        System.out.println("enter the name of the document to attach.");
    }

    public String handling(){
        return "";
    }
}
