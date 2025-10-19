package Business;

import Data.Inquiry;
import Data.InquiryStaus;
import Data.Representative;

import java.util.Queue;

public class MatchingThread extends Thread{

    Queue<Inquiry> inquiryQueue;
    Queue<Representative> representativeQueue;

    public MatchingThread(){
        inquiryQueue = InquiryManager
                .getInstance()
                .allInquiry();
        representativeQueue = RepresentativeManager
                .getInstance()
                .getAvailableRepresentatives();
    }

    @Override
    public void run(){
        while (true){
            if(!inquiryQueue.isEmpty() && !representativeQueue.isEmpty()){
                Inquiry inquiry = inquiryQueue.poll();
                Representative representative = representativeQueue.poll();
                inquiry.setRepresentativeID(representative.getID());
                inquiry.setStatus(InquiryStaus.IN_HANDLING);
                new InquiryHandling(inquiry, representative).start();
            }
        }
    }
}
