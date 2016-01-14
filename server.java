
import java.net.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.OutputStream;


public class server{
    public static void main(String args[]) throws IOException{
        ServerSocket serv;
        Socket sock;
        serv = null;
        sock = null;
        System.out.println(Inet4Address.getLocalHost().getHostAddress());

        try{
            serv = new ServerSocket(8888);
            sock = serv.accept();
         
        }catch(IOException e){
             e.printStackTrace();
         }
         
         
         
         InputStream I;
         OutputStream O;
         I=null;
         O=null;
         try{
            I = sock.getInputStream();
         }catch(IOException e){
            e.printStackTrace();
         }
         
         try{
            O = new FileOutputStream("/home/ubuntu/dat.csv");
         }catch(FileNotFoundException e){
            e.printStackTrace();
         }
            
         byte[] buffer =new byte[16*1024];
         while(I.read(buffer)>0){
            O.write(buffer,0,I.read(buffer));
         }           
         
         O.close();
         I.close();
         sock.close();
         serv.close();
    
     
     
    }
    
}
