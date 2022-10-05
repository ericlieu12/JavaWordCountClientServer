import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordCountData {
    //singleton set up
    //making it global and consistent
    private static WordCountData dataLevel = null;
   
    //use synchronized keyword to enable synchronization
    //otherwise, some issues may occur with accessing certian elements  
    public static synchronized WordCountData getData() {
        if (dataLevel == null)
        {
            //if null, create new one, else use current one
            dataLevel = new WordCountData();
        }
        return dataLevel;
    }
    public String[] contentToLines(String content)
    {
        //changes string of content back to lines based on predescribed format
        content = content.replaceFirst("\\[", "");
        content = content.replaceFirst("\\]", "");
        String[] lines = content.split(", ");
        return lines;
    }
    public void addNewFile(String fileName, String content) throws Exception {
            //gets lines from content
            String[] lines = contentToLines(content);
            //create new file
            File newFile = new File("./src/Server/Storage", fileName);
            if (newFile.createNewFile())
            {
                //write lines to file
                FileWriter myWriter = new FileWriter(newFile);
                
                for(String line : lines) {
                    myWriter.write(line);
                    myWriter.write("\n");
                }

                myWriter.close(); }
              else {
                  //if file already exists, throw exception
                  throw new Exception();
              }
           
        //if anything wrong occurs, throw exception
        
    }

    public void removeFile(String fileName) throws Exception {
        
            //
            
            
            File newFile = new File("./src/Server/Storage", fileName);
            if (newFile.delete())
            {
               //delete file successfully
            }
            else {
                //something wrong, throw exception
                throw new Exception();
            }
        
        
        
    }
    public void updateFile(String fileName, String content) throws Exception {
        
        //abstraction, just remove and add file
        File newFile = new File("./src/Server/Storage", fileName);
        if (newFile.exists())
        {
            //file has to exist to be updated, else
           removeFile(fileName);
           addNewFile(fileName, content);
        }
        else {
            //anything wrong, throw exception
            throw new Exception();
        }
    
    
    
    }
    public String readFile(String fileName) throws Exception {
        
        File newFile = new File("./src/Server/Storage", fileName);
        
        
        if (newFile.exists())
        {
            //file has to exist to be read
            return Files.readAllLines(newFile.toPath()).toString();
        }
        else {
            //else throw exception
            throw new Exception();
        }
    
    
    
        
}
    public int getNumLines(String fileName) throws Exception {
        
        File newFile = new File("./src/Server/Storage", fileName);
        if (newFile.exists())
        {
            return Files.readAllLines(newFile.toPath()).size();
            //readAllLines returns array. amount of lines is equal to array size
        }
        else {
            throw new Exception();
        }
    }
    public int getNumCharacters(String fileName) throws Exception {
        File newFile = new File("./src/Server/Storage", fileName);
        if (newFile.exists())
        {
            //readString returns a string with \n's and spaces, remove spaces
            //this will be valid if the file is valid
            //bad files should not be validated
            return Files.readString(newFile.toPath()).length() - Files.readAllLines(newFile.toPath()).size() + 1;
        }
        else {
            throw new Exception();
        }
    }
    public int getNumWords(String fileName) throws Exception {
        //count words in lines
        File newFile = new File("./src/Server/Storage", fileName);
        int count = 0;
        if (newFile.exists())
        {
            
        }
        else {
            throw new Exception();
        }
        List<String> lines = Files.readAllLines(newFile.toPath());
        for (String line : lines)
        {
            String[] words = line.split("[.-:; ]");
            count = count + words.length;
        }
        return count;
        
    }
    public int getGlobalNumLines() throws Exception
    {   
        //count through all files and all lines using previous function
        File dir = new File("./src/Server/Storage");
        File[] files = dir.listFiles();
        int count = 0;
        for (File file: files)
        {
            if (file.getName().endsWith(".txt"))
            {
            count = count + getNumLines(file.getName());
            }
        }
        return count;
    }

    public int getGlobalNumWords() throws Exception
    {
        //count through all files and all words using previous function
        File dir = new File("./src/Server/Storage");
        File[] files = dir.listFiles();
        int count = 0;
        for (File file: files)
        {
            if (file.getName().endsWith(".txt"))
            {
                count = count + getNumWords(file.getName());
            }
        }
        return count;
    }

    public int getGlobalNumCharacters() throws Exception
    {
        //count through all files and all chars using previous function
        File dir = new File("./src/Server/Storage");
        File[] files = dir.listFiles();
        int count = 0;
        for (File file: files)
        {
            if (file.getName().endsWith(".txt"))
            {
                count = count + getNumCharacters(file.getName());
            }
            
        }
        return count;
    }
    public String[] getGlobalFileNames() throws Exception
    {
        //returns all files with .txt
        File dir = new File("./src/Server/Storage");

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".txt");
            }
        };
        String[] pathnames = dir.list(filter);
        
        
        return pathnames;
    }
}
