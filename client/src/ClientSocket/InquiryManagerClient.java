package ClientSocket;
import ClientServer.*;
import Data.*;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
public class InquiryManagerClient {
    private Socket connectToServer;
    Scanner myScanner = new Scanner(System.in);
    public InquiryManagerClient(String host,int port) {
        try {
            connectToServer = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execut() {
        System.out.println("0-test");
        System.out.println(" 1 to view Inquiry");
        System.out.println(" 2 to add a Inquiry");
        System.out.println(" 3 to ReturnRepresentativeByInquiryId ");
        System.out.println("4 to getAllRepresentatives");
        System.out.println("5 to return inquiry status");
        System.out.println("6 to get count of inquiries in month...");
        System.out.println("7 to cancel inquiry");
        System.out.println("8 to finish...");
        String choose = myScanner.nextLine();
        //  while (!choose.equals("3")) {
            RequestData request=null;
            ResponseData response=null;
            switch (choose) {
                case "0":
                    request=new RequestData(InquiryManagerActions.TEST);
                    break;
                case "1":
                    request = new RequestData(InquiryManagerActions.ALL_INQUIRY);
                    break;
                case "2":{
                    Inquiry inquiry=addInquiry();
                    request = new RequestData(InquiryManagerActions.ADD_INQUIRY, inquiry);}
                    break;
                case "3":{
                    System.out.println("Insert ID of inquiry");
                    int id =myScanner.nextInt();
                    myScanner.nextLine();
                    request=new RequestData(InquiryManagerActions.RETURN_REPRESENTATIVE_BY_INQUIRYID,id);
                    break;
                }
                case "4":{
                    request=new RequestData(InquiryManagerActions.GET_ALL_REPRESENTATIVES);
                    break;
                }
                case "5":{
                    System.out.println("inset ID of inquiry");
                    int id =myScanner.nextInt();
                    myScanner.nextLine();
                    request=new RequestData(InquiryManagerActions.RETURN_INQUIRY_STATUS,id);
                    break;
                }
                case "6":{
                    System.out.println("inset year");
                    int year =myScanner.nextInt();
                    myScanner.nextLine();
                    System.out.println("inset month");
                    int month =myScanner.nextInt();
                    myScanner.nextLine();
                    request=new RequestData(InquiryManagerActions.RETURN_INQUIRIES_IN_MONTH,year,month);
                    break;
                }
                case "7":{
                    System.out.println("inset ID of inquiry");
                    int id =myScanner.nextInt();
                    myScanner.nextLine();
                    request=new RequestData(InquiryManagerActions.CANCEL_INQUIRY,id);
                    break;
                }
                default:
                    System.out.println("error - try again");
                    break;
            }
            if(request!=null){
                try {
                    sendRequestToServer(request);
                    System.out.println("after send");
                    response = ReceivingResponseFromServer();
                } catch (IOException e) {
                    e.printStackTrace();
                    response=new ResponseData(ResponseStatus.FAIL,e.getMessage(),e);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    response=new ResponseData(ResponseStatus.FAIL,e.getMessage(),e);
                }finally {
                    System.out.println(response.getMessage());
                    System.out.println(response.getResult());
                }
            }

        //    System.out.println("Press 1 to view Inquiry, 2 to add a Inquiry, 3 to finish...");
       //     choose =myScanner.nextLine();

       // }

    }
    private void sendRequestToServer(RequestData request) throws IOException {
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(connectToServer.getOutputStream());
        objectOutputStream.writeObject(request);
        System.out.println("after send in func");
        objectOutputStream.flush();
    }

    private ResponseData ReceivingResponseFromServer() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream=new ObjectInputStream(connectToServer.getInputStream());
        ResponseData responseData = (ResponseData) objectInputStream.readObject();
        System.out.println("after send in func");
        objectInputStream.close();
        return responseData;
    }



    private Inquiry addInquiry() {
        Inquiry inquiry = null;
        while (inquiry == null) {
            System.out.println("Please choose the type of inquiry:");
            System.out.println("1 - Question");
            System.out.println("2 - Request");
            System.out.println("3 - Complaint");
            String choose = myScanner.nextLine();
            switch (choose) {
                case "1":
                    inquiry = new Question();
                    break;
                case "2":
                    inquiry = new Request();
                    break;
                case "3":
                    inquiry = new Complaint();
                    break;
                default:
                    System.out.println("error , try again");
                    break;
            }
        }
        return inquiry;
    }
    public void printAllactiveRepresentatives(Map<Integer,Inquiry>activeRepresentatives){
        if(activeRepresentatives!=null) {
            for (Map.Entry<Integer, Inquiry> entry : activeRepresentatives.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        else {
            System.out.println("No active representatives");

        }
    }
}
