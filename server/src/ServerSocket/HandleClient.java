package ServerSocket;
import Business.InquiryManager;
import Business.RepresentativeManager;
import ClientServer.*;
import Data.Inquiry;
import Data.InquiryStaus;
import Data.Representative;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
public class HandleClient extends Thread{
    Socket clientSocket;
    InquiryManager inquiryManager=InquiryManager.getInstance();
    RepresentativeManager representativeManager=RepresentativeManager.getInstance();
    public HandleClient(Socket clientSocket){
        this.clientSocket=clientSocket;
    }
    ObjectInputStream objectInputStream=null;
    ObjectOutputStream objectOutputStream=null;
    public void handleClientRequest(){
            try {
                    objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    RequestData newRequest = (RequestData) objectInputStream.readObject();
                    System.out.println("after recive");
                    Queue<Inquiry> queue;
                    ResponseData response = null;
                    switch (newRequest.getAction().toString()) {
                        case "ALL_INQUIRY": {
                            try {
                                queue = inquiryManager.allInquiry();
                                response = new ResponseData(ResponseStatus.SCCESS, "allInquiry", queue);
                            } catch (Exception e) {
                                response = new ResponseData(ResponseStatus.FAIL, e.getMessage(), "fail");
                            }
                        }
                        break;
                        case "ADD_INQUIRY":
                            inquiryManager.addInquiry((Inquiry) newRequest.getParameters()[0]);
                             response=new ResponseData(ResponseStatus.SCCESS,"khnbv","jkhgg");
                            break;
                        case "TEST":
                            response = new ResponseData(ResponseStatus.SCCESS, "test", "test");
                            break;
                        case "RETURN_REPRESENTATIVE_BY_INQUIRYID":
                            try {
                                Representative representative = inquiryManager.ReturnRepresentativeByInquiryId((int) newRequest.getParameters()[0]);
                                response = new ResponseData(ResponseStatus.SCCESS, "ok", "representative: " + representative.getID() + "g is handling your request.");
                            }
                            catch (Exception e){
                                response = new ResponseData(ResponseStatus.FAIL, e.getMessage(), "fail");
                            }
                            break;
                        case "GET_ALL_REPRESENTATIVES":
                            try {
                                Map<Integer, Inquiry> activeRepresentatives = representativeManager.getAllRepresentatives();
                                response = new ResponseData(ResponseStatus.SCCESS, "activeRepresentatives", activeRepresentatives);
                            }
                               catch (Exception e){
                                response=new ResponseData(ResponseStatus.FAIL,e.getMessage(),null);
                            }
                            break;
                        case "CANCEL_INQUIRY":
                            if(inquiryManager.cancelInquiry((int)newRequest.getParameters()[0]))
                                response = new ResponseData(ResponseStatus.SCCESS, "inquiry canceled", true);
                            else {
                                response = new ResponseData(ResponseStatus.FAIL, "inquiry was handled already", false);
                            }
                            break;
                        case "RETURN_INQUIRY_STATUS":
                            InquiryStaus status = inquiryManager.getInquiryStatus((int)newRequest.getParameters()[0]);
                            if(status!=null)
                                response = new ResponseData(ResponseStatus.SCCESS,"succeeded",status);
                            else {
                                response = new ResponseData(ResponseStatus.FAIL, "failed", ":(");
                            }
                            break;

                        case "RETURN_INQUIRIES_IN_MONTH":
                            int countInMonth = inquiryManager.getNumberOfInquiriesInMonth((int)newRequest.getParameters()[0],(int)newRequest.getParameters()[1]);
                            response = new ResponseData(ResponseStatus.SCCESS,"succeeded",countInMonth);
                            break;



                    }
                    objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    objectOutputStream.writeObject(response);
                    System.out.println("send");
                    objectInputStream.close();
                    objectInputStream.close();

                clientSocket.close();


    } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
//                try {
//                    objectInputStream.close();
//                    objectOutputStream.close();
//                    clientSocket.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            }

    }
    @Override
    public void run() {
        handleClientRequest();
    }
}
