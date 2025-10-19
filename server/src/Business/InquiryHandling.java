package Business;
import Data.*;

import java.util.Random;
import java.util.Scanner;
public class InquiryHandling extends Thread  {
    private Inquiry inquiry;
    private Representative representative;
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    public InquiryHandling(Inquiry inquiry, Representative representative){
        this.inquiry = inquiry;
        this.representative = representative;
    }

    @Override
    public void run() {
        String nameClass = inquiry.getClass().getSimpleName();
        int estimationTime;

        switch (nameClass){
            case "Question":{ estimationTime=rand.nextInt(5) + 1;
                currentThread().setPriority(MAX_PRIORITY);
            }
            break;
            case "Request": estimationTime=rand.nextInt(6) + 10;
                break;
            case "Complaint": estimationTime=rand.nextInt(21) + 20;
                break;
            default:
                throw new Error("error");
        }
        System.out.println(inquiry.handling()+"  estimationTime: "+estimationTime);
        System.out.println("representative:" + representative.getName());
        try {
            if(estimationTime>5&&activeCount()>10)
                Thread.yield();
            else {
                sleep(estimationTime*1000);
                System.out.println("finish");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        InquiryManager.getInstance().closeInquiry(inquiry, representative);
    }
    @Deprecated
    public void createInquiry() throws Exception {
        System.out.println("chose number:" +
                "1:Question,  2:Request, 3:Complaint");
        int num= scanner.nextInt();
        switch (num){
            case 1:inquiry=new Question();
                break;
            case 2:inquiry=new Request();
                break;
            case 3:inquiry=new Complaint();
                break;
            default:throw new Exception("error");
        }
    }
}
