import java.util.Arrays;
import java.util.HashMap;

public class WordCountCache {
    //singleton
    private static WordCountCache dataLevel = null;
    int maxCache = 0;
    int currentCache = 0;
    public HashMap<String, FileType> files = new HashMap<String, FileType>();
    //syncrhonzied ensures synchornization
    public static synchronized WordCountCache getData() {
        if (dataLevel == null)
        {
            dataLevel = new WordCountCache(4);
        }
        return dataLevel;
    }
   
    private WordCountCache(int max) {
        maxCache = max;

    }
    public void refrehsCache() throws Exception {
        //deletes one element from cache
        if (files.keySet().size() == 0)
        {
            throw new Exception();
        }
        for (String key: files.keySet()) {
            files.remove(key);
            return;
        }

    }
    public void addNewFile(String fileName, String content) throws Exception {
        //adds new file
        //if cache > max cache, make room in cache
        FileType file;
        
            if (files.containsKey(fileName))
            {
                throw new Exception();
            }
            file = new FileType(fileName, content);
            files.put(fileName, file);
            if (currentCache == maxCache)
            {
                refrehsCache();
            }
            currentCache++;
        
        
    }
    public void updateFile(String fileName, String content) throws Exception {
        FileType file;
        //updates file
            file = new FileType(fileName, content);
            if (files.replace(fileName, file) == null) {
                //that means that replace did not work and file did not exist
                throw new Exception();
            };
          
        
    }

    public void removeFile(String fileName) throws Exception
    {
        //removes file fromc ahce
            if (files.remove(fileName) == null) {
                throw new Exception();
            }
            
            currentCache--;
            if (currentCache < 0)
            {
                currentCache = 0;
            }
    }

    public String readFile(String fileName) throws Exception
    {
        //reads from cache
            FileType file = files.get(fileName);

            return file.getContent();
        
        
    }

    public int getNumLines(String fileName) throws Exception
    {
       
            FileType file = files.get(fileName);

            return file.getNumLines();
      
    }

    public int getNumWords(String fileName) throws Exception
    {
       
            FileType file = files.get(fileName);

            return  file.getNumWords();
       
    }
//irrelevant functions
    public String getGlobalNumLines()
    {
        int lines = 0;
        try {
            for (String key: files.keySet()) {
                lines+=files.get(key).getNumLines();
            }
            return "The total number of lines is " + lines;
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public String getGlobalNumWords()
    {
        int words = 0;
        try {
            for (String key: files.keySet()) {
                words+=files.get(key).getNumWords();
            }
            return "The total number of words is " + words;
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public String getGlobalNumCharacters()
    {
        int chars = 0;
        try {
            for (String key: files.keySet()) {
                chars+=files.get(key).getNumCharacters();
            }
            return "The total number of characters is " + chars;
        }
        catch (Exception e)
        {
            return "";
        }
    }
    public String getGlobalFileNames()
    {
        String names = "[";
        try {
            for (String key: files.keySet()) {
                names = names + files.get(key).getFileName() +", ";
            }
            names = names + "]";
            return "The file names are " + names;
        }
        catch (Exception e)
        {
            return "";
        }
    }
    public int getNumCharacters(String fileName) throws Exception
    {
        
            FileType file = files.get(fileName);

            return file.getNumCharacters();
        
    }
}
class FileType {
    //file type data structure that validates file
    private int numLines;
    private int numWords = 0;
    private int numCharacters;
    private String[] content;
    private String fileName;
    public FileType(String fileName, String content) throws Exception {
        content = content.replaceFirst("\\[", "");
        content = content.replaceFirst("\\]", "");
        String[] lines = content.split(", ");
        
        for(String line : lines) {
            //checks lines to be valid
            if (valid(line) == false) {
                throw new Exception();
            }
        }
        this.content = lines;
       
        this.fileName = fileName;
        this.numLines = lines.length;
        

    }
    public String getFileName() {
        return fileName;
    }
    public int getNumLines() {
        return numLines;
    }
    public int getNumWords() {
        return numWords;
    }
    public int getNumCharacters() {
        return numCharacters;
    }
    public String getContent() {
        
        return Arrays.toString(content);
    }
    public boolean valid(String line) {
        //splits line to words (word, sperator, word)
        if (line.charAt(0) == '.' || line.charAt(0) == '-' ||line.charAt(0) == ':' ||line.charAt(0) == ';' || line.charAt(0) == ' ' )
        {
            return false;
        }
        if (line.charAt(line.length()-1) == '.' || line.charAt(line.length()-1) == '-' ||line.charAt(line.length()-1) == ':' ||line.charAt(line.length()-1) == ';' || line.charAt(line.length()-1) == ' ' )
        {
            return false;
        }
        String[] words = line.split("[.-:; ]");
        for (String word: words) {
            //checks word if matches <word> <unit>
            if (word.length() == 0)
            {
                //if word lenght is 0, then <words> <seperator> <word> fails
                return false;
            }
            if (!word.matches("^[-a-zA-Z0-9]+"))
            {
                //if regex fails, then there is not a valid unit in word
                //this hsoudl encompass all test cases
                return false;
            }
            numWords++;
            numCharacters+= word.length();
            
        }
        return true;
    }
   

}
