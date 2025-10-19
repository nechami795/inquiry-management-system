import ClientSocket.InquiryManagerClient;

public class Main {
    public static void main(String[] args) {

        InquiryManagerClient inquiryManagerClient=new InquiryManagerClient("localhost",8000);
       inquiryManagerClient.execut();
    }

}