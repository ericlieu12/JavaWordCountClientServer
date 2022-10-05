import java.net.*;
import java.io.*;

public class WordCountServer {
    
    public static void main(String[] args) throws IOException {
        
        
        //initialized port number
        int portNumber = 1234;

        //creates server socket
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (true) {
                //every new connection spawns a new thread
                new WordCountServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            //error handling
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
    
}
