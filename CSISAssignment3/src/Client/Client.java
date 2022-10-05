
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.io.BufferedReader;

public class Client {
    public static void main(String[] args) {
        try (Socket kkSocket = new Socket("localhost", 1234)) {
                   
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
                
            String fromServer;
            
            fromServer = in.readLine();
            
            if (fromServer.toString().equals("Successful connection. Awaiting instruction."))
            {
                while(true)
                {
        
                    try {
                        System.out.println("Choose from the select menu:");
                        System.out.println("1) STORE - Store a file onto the server.");
                        System.out.println("2) UPDATE - Update a file on the server.");
                        System.out.println("3) REMOVE - Remove a file from the server.");
                        System.out.println("4) GET - Returns content of a file (or names of all files).");
                        System.out.println("5) GETLINES - Get amount of lines on a file (or from entire server).");
                        System.out.println("6) GETWORDS - Get amount of words on a file (or from entire server).");
                        System.out.println("7) GETCHARACTERS - Get amount of characters on a file (or from entire server).");
                        System.out.println("8) EXIT");
                        int instruction;
                        try {
                            String instructionString = System.console().readLine();
                            instruction = Integer.valueOf(instructionString);
                            if (instruction < 1 || instruction > 8)
                            {
                            throw new Exception();
                            }
                            if (instruction == 8)
                            {
                                out.println("END");
                                break;
                            }
                        }
                        catch (Exception e)
                        {
                            //if there is a failure of some sort, just restart while loop
                            //if user does not understand instruction, they are bound to get it after a few tries
                            continue;
                        }
                        String fileName = null;
                        File file = null;
                        if (instruction > 4)
                        {
                            System.out.println("Enter file name: (blank for entire server)");
                        }
                        else {

                            
                            System.out.println("Enter file name: ");
                            
                        }
                        
                        fileName = System.console().readLine();
                        try {
                            //if not approrpaite file, (.txt.) throw exception
                            if (fileName.endsWith(".txt") == false) {
                                throw new Exception();
                            }
                            if (instruction < 3)
                            {
                            //if instruction is less than 2, we have to read a file from computer
                            //if fail, something is wrong with file
                            file = new File(fileName);
                            if (file.exists() == false)
                            {
                                //if not exist, throw exception
                                throw new Exception();
                            }
                        }
                        }
                        catch (Exception e)
                        {
                            System.out.println("Error, please try again. File may not exist or is not valid.");
                            continue;
                        }
                        
                       
                          
                               
                        
                        
                        //switch case for instructions
                        //sends path agreed to by client server
                        switch (instruction) {
                            case 1:  
                                out.println("1 " + file.toPath() + " " + Files.readAllLines(file.toPath()).toString());
                                break;
                            case 2:
                                out.println("2 " + file.toPath() + " " + Files.readAllLines(file.toPath()).toString());
                                break;
                            case 3:
                                out.println("3 " + fileName);
                                break;
                            case 4:                          
                                if (fileName.equals(""))
                                {
                                    out.println("8 " + fileName);
                                }
                                else {
                                    out.println("4 " + fileName);
                                }
                                
                                break;
                            case 5:
                            if (fileName.equals(""))
                            {
                                out.println("9 " + fileName);
                            }
                            else {
                                out.println("5 " + fileName);
                            }
                                break;
                            case 6:
                            if (fileName.equals(""))
                            {
                                out.println("10 " + fileName);
                            }
                            else {
                                out.println("6 " + fileName);
                            }
                            break;
                            case 7:
                            if (fileName.equals(""))
                            {
                                out.println("11 " + fileName);
                            }
                            else {
                                out.println("7 " + fileName);
                            }
                                break;

                                default: 
                                     break;
                        }
                        
                        
                        //server response
                        
                        fromServer = in.readLine();
                        int timeout = 5000;
                        while (fromServer == null)
                        {
                            System.out.println("Awaiting server repsonse");
                            fromServer = in.readLine();
                            timeout--;
                            if (timeout == 0)
                            {
                                throw new Exception();
                            }
                        }
                        
                        if (fromServer.equals("ERROR"))
                        {
                            throw new Exception();
                        }
                        System.out.println(fromServer);
                        
                        
                        
                        
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error in operation. Please try again and make sure file content meets guidelines.");
                        System.out.println("If problem persists, contact developer.");
                    }
                    
                }
                
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Error connecting to server. Please make sure it is running.");
        }
     
        
    }

    
}
