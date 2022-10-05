import java.net.*;
import java.io.*;
 
public class WordCountServerThread extends Thread {
    private Socket socket = null;
 
    public WordCountServerThread(Socket socket) {
        
        this.socket = socket;
    }
     
    public void run() {
       //sets up thread
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            WordCountProtocol wcp = new WordCountProtocol();
            outputLine = wcp.processInput(null);
            out.println(outputLine);
            
            while ((inputLine = in.readLine()) != null) {
                outputLine = wcp.processInput(inputLine);
                out.println(outputLine);
                if (inputLine.equals("END")){
                    break;
                }
                
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}