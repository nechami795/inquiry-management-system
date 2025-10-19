import Business.InquiryManager;
import Business.MatchingThread;
import Business.RepresentativeManager;
import ServerSocket.InquiryManagerServer;

public class Main {
    public static void main(String[] args) throws Exception {
       RepresentativeManager representativeManager=RepresentativeManager.getInstance();
       InquiryManager inquiryManager= InquiryManager.getInstance();
       new MatchingThread().start();
       InquiryManagerServer inquiryManagerServer=new InquiryManagerServer();
       inquiryManagerServer.start();
    }
}
