

import java.net.*;
import java.util.Arrays;

import javax.lang.model.type.NullType;

import java.io.*;

public class WordCountProtocol {
    private static final int inactive = 0;
    private static final int active = 1;
    

    private int state = inactive;
   

  
    
    public String processInput(String theInput) {
        String theOutput = null;
        if (theInput == null && state == inactive)
        {
            theOutput = "Successful connection. Awaiting instruction.";
            state = active;
        }
        else {
            try {
                
                //each case goes through a try catch. This allows us to seperate concerns as the data level
                //can throw exceptions and handle that while the protocol handles messaging of said data leve
                
                //tries to get from cache first, then from server
                System.out.println(theInput);
                String[] arguments = theInput.split(" ", 3);
                switch (arguments[0]) {
                    case "1":  
                    try {
                        WordCountCache.getData().addNewFile(arguments[1], arguments[2]);
                        //if successful, then it would not throw exception and continue
                        //else exception is throw n and rest of code is not run
                        WordCountData.getData().addNewFile(arguments[1], arguments[2]);
                        //if successful, then it would not throw exception and continue
                        //else exception is throw n and rest of code is not run
                        theOutput = "Successfully added file: " + arguments[1];
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                        theOutput = "Failure adding file: " + arguments[1] + ". File may already exist or be invalid.";
                    }
                    break;
                    case "2":
                    try {
                        WordCountCache.getData().updateFile(arguments[1], arguments[2]);
                        //if successful, then it would not throw exception and continue
                        //else exception is throw n and rest of code is not run
                        WordCountData.getData().updateFile(arguments[1], arguments[2]);
                        //if successful, then it would not throw exception and continue
                        //else exception is throw n and rest of code is not run
                        theOutput = "Successfully updated file: " + arguments[1];
                    }
                    catch (Exception e)
                    {
                        try {
                            WordCountData.getData().updateFile(arguments[1], arguments[2]);
                            theOutput = "Successfully updated file: " + arguments[1];
                        }
                        catch (Exception er)
                        {
                            theOutput = "Failure updating file: " + arguments[1] + ". File may not exist or may be invalid.";
                        }
                        
                    }
                    break;
                    case "3":
                    //messy try catch but trying to keep program consistent
                    try {
                    
                        WordCountCache.getData().removeFile(arguments[1]);
                        //if this throws an exception, then file was not in cache. it may be in server tho
                        WordCountData.getData().removeFile(arguments[1]);
                        theOutput = "Successfully removed file: " + arguments[1];
                    }
                    catch(Exception e) {
                        try {
                            //we just try to remove file since it may not be in cache
                            //if this throws error, then we know it was acutally an error
                            //if the second statemnet caused an error, this will cause an error too
                            //this may cause issue if file is removed form cache but fails to remove from server
                            //in this case, something else is going wrong but should not affect since
                            //data is updated in the cache
                            WordCountData.getData().removeFile(arguments[1]);
                            theOutput = "Successfully removed file: " + arguments[1];
                        }
                        catch(Exception er)
                        {
                            theOutput = "Failure removing file: " + arguments[1] +". File may not exist.";
                        }
                    }
                    
                    break;
                    case "4":
                    try {
                        //try read file form cache
                        theOutput = WordCountCache.getData().readFile(arguments[1]);
                    }
                    catch (Exception e)
                    {
                        //try read file from server
                        try {
                            theOutput = WordCountData.getData().readFile(arguments[1]);
                        }
                        catch (Exception er)
                        {
                            //else fail
                            theOutput = "Failure to read file " + arguments[1] + ". File may not exist.";
                        }
                    }
                    
                    break;
                    case "5":
                    //try get lines from cache
                    try {
                        int lines = WordCountCache.getData().getNumLines(arguments[1]);
                        theOutput = "The number of lines in " + arguments[1] + " is " + lines + ".";
                    }
                    catch (Exception e)
                    {//then server
                        try {
                            int lines = WordCountData.getData().getNumLines(arguments[1]);
                            theOutput = "The number of lines in " + arguments[1] + " is " + lines + ".";
                        }
                        catch (Exception er)
                        {
                            theOutput = "Failure to get lines of " + arguments[1] + ". File may not exist.";
                        }
                    }
                    break;
                    case "6":
                    try {
                        //try cache
                        int words = WordCountCache.getData().getNumWords(arguments[1]);
                        theOutput = "The number of lines in " + arguments[1] + " is " + words + ".";
                    }
                    catch (Exception e)
                    {
                        try {
                            //then try server
                            int words = WordCountData.getData().getNumWords(arguments[1]);
                            theOutput = "The number of lines in " + arguments[1] + " is " + words + ".";
                        }
                        catch (Exception er)
                        {
                            theOutput = "Failure to get words of " + arguments[1] + ". File may not exist.";
                        }
                    }
                    break;
                    case "7":
                    try {
                        //try cache
                        int chars = WordCountCache.getData().getNumWords(arguments[1]);
                        theOutput = "The number of lines in " + arguments[1] + " is " + chars + ".";
                    }
                    catch (Exception e)
                    {
                        try {
                            //then server
                            int chars = WordCountData.getData().getNumWords(arguments[1]);
                            theOutput = "The number of characters in " + arguments[1] + " is " + chars + ".";
                        }
                        catch (Exception er)
                        {
                            theOutput = "Failure to get characters of " + arguments[1] + ". File may not exist.";
                        }
                    }
                    break;
                    case "8":
                    try {
                        String[] files = WordCountData.getData().getGlobalFileNames();
                        if (files.length == 0)
                        {
                            //if length 0, no files
                            theOutput = "No files.";
                        }
                        else {
                            theOutput = "The file names are " + Arrays.toString(files);
                        }
                        
                    }
                    catch (Exception e)
                    {
                       
                        theOutput = "Failure to get file names. Contact developer if error persists.";
                        
                    }
                    break;
                    case "9":
                    try {
                        int lines = WordCountData.getData().getGlobalNumLines();
                        theOutput = "The total number of lines are " + lines;
                    }
                    catch (Exception e)
                    {
                       
                        theOutput = "Failure to get lines count. Contact developer if error persists.";
                        
                    }
                    break;
                    case "10":
                    try {
                        int words = WordCountData.getData().getGlobalNumWords();
                        theOutput = "The total number of words are " + words;
                    }
                    catch (Exception e)
                    {
                       
                        theOutput = "Failure to get words count. Contact developer if error persists.";
                        
                    }
                    break;
                    case "11":
                    try {
                        int chars = WordCountData.getData().getGlobalNumCharacters();
                        theOutput = "The total number of characters are " + chars;
                    }
                    catch (Exception e)
                    {
                       
                        theOutput = "Failure to get character count. Contact developer if error persists.";
                        
                    }
                    break;
                    case "END":
                        theOutput = "END";
                    default: 
                    theOutput = "ERROR";
                             break;
                }
               
                
               
                
            }
            catch (Exception e)
            {
                theOutput = "ERROR";
            }
            
        }
        //debug purposes
        System.out.println(theOutput);
        return theOutput;
    }
}