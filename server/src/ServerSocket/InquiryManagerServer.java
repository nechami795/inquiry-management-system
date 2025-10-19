package ServerSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InquiryManagerServer {
    ServerSocket myServer;
    public InquiryManagerServer(){
        try {
            myServer=new ServerSocket(8000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void start(){
            try {
                while (true){
                Socket socket = myServer.accept();
                System.out.println("client connet");
                HandleClient handleClient = new HandleClient(socket);
                //handleClient.start();
                Thread thread=new Thread(handleClient);
                thread.start();}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
    public  void stop(){
        try {
            myServer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
};
