# Design for Word Count Processor Client/Server Version 1.1 Release Date 12/12/21

By: Eric Lieu

## UPDATE: I ran into an issue testing where if the server was run through the command line
instead of Visual Studio Code, then the server will set its relative directory to the folder it is
located in (which is CSISAssignment3/src/Server). If ran through Visual Studio, it would be
(CSISAssignment3). This ran into issues as I set the directory to “./src/Server and I did not test
on the terminal. I fixed this by adding a src/Server/Storage folder to the Server folder. In the
future, I will have code that will automatically create the folder if it did not exist. I updated the
instructions as such.

## Introduction:

The Word Count Processor Client/Server is a basic simulation of a solution to a prescribed
client-server problem. I, Eric Lieu  am tasked with designing and
implementing the Word Count Processor Client/Server for a client at Nova Southeastern
University. Throughout this document, I will describe the design and implementation of the
Word Count Processor Client/Server are discussed in detail.

## Instructions:

The client and server are separated into two files (Client/Server) in the src folder of the solution.
Compile and run the server (WordCountServer.java) first and then compile and run the client
(Client.Java). The client can be run more than once to simulate multithreading. I recommend
running the client through the cmd terminal but that is not needed. An interactive menu should
be displayed after running the Client. When choosing files to send to the server, they should be
located in the client folder of the solution. The files will be stored in the Server/Storage folder in
the solution. The Storage folder is included already and if it were to be deleted, the program will
cease to function. If that happens, just re add a folder named “Storage” to the server folder. If
that does not happen, add a folder called src, then add a folder called Server, then add a folder
called Storage to the initial server folder.

**IMPORTANT:** All files sent to the server must be located in the client folder. The files stored
will be in the server/storage folder. This information will not be repeated in the design
document and is implied.

## Scope/Overview:

The person requests certain specifications and requirements for the Word Count
Processor Client/Server. Past these certain specifications, it is up to me to make decisions best
for the Word Count Processor Client/Server.

The person requests that the program be written in Java. Java is a high-level
object-oriented programming language.

The person requests that the server handles multiple clients connecting and disconnecting
from the server and that the server holds data persistently through server shutdowns and restarts.
There is also a processing layer that handles a cache and the server itself. The cache must be not
infinite, make sense, support element query/creation/removal, consistent, and interactive.

Files must also be written in a language that the person requests. This language is
discussed later on. It is essentially defining a language where words are made of units and are
separated by one separator each and those words are in lines.

The main emphasis of my solution is the decoupling of the client and server. Each and
every part of the program can run independently off one another and do not rely on each other in
any way. This allows for separation of concerns and easier maintenance. If there were a specific
problem, it can be easily pinpointed to one of the independent parts.

I also encompassed most of the subcomponents in or as try-catch statements. This allows
for the client to continuously run regardless of error, as exceptions will cause the programs to
crash. In the future versions, I will log the various errors that occur. For now, the catch
statements represent appropriate responses to the task at hand (i.e. exception when adding a file
will have an appropriate response). This is further explained in the various subcomponents and
componenets discussed later in the document. This is the way my company taught me to do it but
I know it is a very contentious topic as some people consider it improper.

Also, I used source code from Oracle as a guide to set up the server and client. Below
this, I will post the permission that was given to redistribute the source code.

https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved. * * Redistribution and
use in source and binary forms, with or without * modification, are permitted provided that the
following conditions * are met: * * - Redistributions of source code must retain the above
copyright * notice, this list of conditions and the following disclaimer. * * - Redistributions
in binary form must reproduce the above copyright * notice, this list of conditions and the
following disclaimer in the * documentation and/or other materials provided with the
distribution. * * - Neither the name of Oracle or the names of its * contributors may be used
to endorse or promote products derived * from this software without specific prior written
permission. * * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
CONTRIBUTORS "AS * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, * THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR * PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR *
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, *
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR *
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
DAMAGE. 

## Data Design
This section contains all the data objects and the resultant data structures for this problem.
### Cache Hashmap

This hashmap represents the cache. Each element represents a file. The cache is designed
as a hashmap. Other ways include an array or an array list. I decided to use a hashmap because it
requires O(1) resources when finding a file. This beats a possible O(n) search. A caveat though is
when deleting an element from a hashmap, which requires me to get all the keys and remove a
random key. This occurs when the cache is overflowing the limit of 4 files stored in the cache. If
I were to use an array or array list, I can just replace the first element of the list when the cache
overflows. This takes less resources than finding all the keys and removing the first key and
adding in a new key whenever the cache overflows. I am making an assumption that the cache
will not overflow often and that it will therefore be better to use a hashmap. This is because users
may not be passing more than one to two files to the server in the first place.
### FileType Data Structure
This data structure is to represent a cache element. It stores the file lines as an array and
the name, amount of lines, amount of words, and amount of characters in the file. The amount of
lines, words, and characters are determined during validation.
### Server Threads
Each client gets their own server thread to communicate with the server. Each server
thread initializes the protocol that establishes the rules of communication and handles the passing
of messages.
## Architectural Design
The main components of the Word Count Processor Client/Server are the server, the server
threads, client-server protocol, the persistent server data level, the data level cache, and the
client.
Server: This runs the socket that allows clients to connect.
Server Threads: This allows for multiple clients to connect to a server. Each client gets their own
server thread (Note: this may not be an appropriate name)
Client-Server Protocol: This establishes the rules between client and server communication.
Persistent Server Data Level: This singleton handles persistent data storage
Data Level Cache: This singleton handles data level caching.
Client: This takes in user input and communicates with the server with the appropriate action.
### Client Component

Clients are responsible for taking user input and communicating with the server with said
input to display an appropriate result. Due to time constraints, the client interface is just the
terminal or the command line interface. No graphical user interfaces exist for this version. This
client displays a menu that the user interacts with. The client runs in a forever while loop until a
user tells it to stop from said menu. An example of the menu is shown below:
1. STORE
2. UPDATE
3. REMOVE
4. GET (single file or all)
5. GET AMOUNT LINES (single file or all)
6. GET AMOUNT WORDS (single file or all)
7. GET AMOUNT CHARACTERS (single file or all)
8. EXIT
Based on user input, it asks again for more information. For example, for option 4, it
needs to know if the user wants a single file to get or all files to get. After these two pieces of
information are received, the client can prepare communication with the server based on
established rules of the protocol (more detail in next section). For example, option 1 requires
grabbing information from the file itself to send over to the server. Option 4 just needs a file
name or no name at all. After sending it to the protocol, it waits to receive an appropriate
message back from the protocol. The protocol is set to always receive a response. Based on the
response, appropriate messages are displayed (like the file information or an error message if
something goes wrong).
This client is designed within multiple try-catches to ensure that it continues running
regardless of any error. This allows for the program to run and if unforeseen small errors occur,
the user will not know the specifics of an error but will be prompted to try again. An example, if
they enter in the wrong input, instead of having a chance to cause an exception and crash the
program, it just catches the exception and continues the while loop. Crashing a program and
causing the user to restart is a very unpleasant experience that I want to mitigate as much as
possible. With this, this prevents that.
If the user continues with the same error, then the error is probably not minor and they
can contact me to get it fixed. For future versions, logging would be an appropriate feature to
showcase any unforeseen errors. The logging will be done in the catch section after it “catched”
an error. This allows me to have more information to fix certain errors that occur.
### Protocol Component:

The protocol component establishes rules between communicating with the client and the
server. There are twelve possible communications between the client and server based on the
menu above. Each possible communication has an established rule that the client must follow.
The communication, due to time constraints, are completed using strings.
A quick note, data from files are grabbed using File.readAllLines(). This returns an array
that is cast as a string. Due to the language described earlier, I do not see any unforeseen issues
(although they may exist). STORE, UPDATE, REMOVE, GET, etc. represent their own
respective integers that are used to communicate (i.e. 1 is STORE, 2 is UPDATE).
1. STORE + fileName + data
2. UPDATE + fileName + data
3. REMOVE + fileName
4. GET+ fileName
5. GETNUMLINES + fileName
6. GETNUMWORDS + fileName
7. GETNUMCHARS + fileName
8. GETALLFILENAMES
9. GETALLNUMLINES
10. GETALLNUMWORDS
11. GETALLNUMCHARS
12. “END”
Based on this, appropriate events occur. When appropriate, the protocol adds to the cache
or gets information to the cache. The entirety of each event is encompassed in a try catch
statement. This allows for the program to run and continue running regardless of error. An
exception caught in the catch statement will cause a message to be printed in the output and be
returned.
An example is STORE. This tries to stores a file in the cache and then to the server (This
is because the cache level validates the file (to be discussed later)). Any exceptions caught (like
file existing already) will print a failure message to be sent back to the client.
Another example is REMOVE. The protocol first attempts to remove the file from the
cache. If it fails to do that, an exception occurs and it is caught and it will attempt to remove
from the server. If that fails, then another exception is caught and a failure message is sent back
to the client.
### Cache Component
The cache component is a singleton component that holds a cache of a preset number of
files. The current number is set to 4. I decided with a singleton component to allow for global
access as described in the requirements. Also, to grab the singleton, it is set behind a
synchronized function. This allows for multiple clients to interact seamlessly with the cache, as
one client cannot access the cache if another client is using the cache. This also simplifies the
multithreading portion of the program instead of using semaphores as it changes one line of code
instead of adding multiple lines of code through various files.
I also had the cache validate the file itself instead of the server. Since we are
communicating first with the cache before the server in the protocol for commands like storing, it
makes sense to validate it in the first level it reaches. If the file is not successfully validated, then
it throws an exception. For storing, if an exception occurs trying to store in the cache, it does not
continue to store on the server in the protocol. So, any file that is valid will have to be
successfully stored in the cache without exceptions before getting stored on the server. This way
allows for less resources to be used as validation is only occurring once, not twice.
This may cause issues if we are using a different protocol (as they may write directly to
the server). When this issue presents itself in the future, adding validation to the server will be
appropriate.
The cache is designed as a hashmap. Other ways include an array or an array list. I
decided to use a hashmap because it requires O(1) resources when finding a file. This beats a
possible O(n) search. A caveat though is when deleting an element from a hashmap, which
requires me to get all the keys and remove a random key. This occurs when the cache is
overflowing the limit of 4 files stored in the cache. If I were to use an array or array list, I can
just replace the first element of the list when the cache overflows. This takes less resources than
finding all the keys and removing the first key and adding in a new key whenever the cache
overflows. I am making an assumption that the cache will not overflow often and that it will
therefore be better to use a hashmap. This is because users may not be passing more than one to
two files to the server in the first place.
### Server Component
The server component has two files, the server itself and the server threads. The server
file defines a preset port number and opens up a ServerSocket (a Java class) that allows clients to
connect. Server threads are spawned whenever a client connects to the socket. Each server thread
spawns the protocol that communicates with the server. This allows for multiple clients to
connect to the server and multithreading. To prevent issues with synchronization, the cache and
data level are singletons. The function to call the singleton is set behind a synchronized keyword
which handles the multithreading seamlessly. Again, I could have used semaphores and it could
have been more efficient BUT this simplifies the entire solution as it just adds a keyword to a
function rather than handling the semaphores whenever the cache and data are called.
 ### Data Level
The data level handles persistent storage of files. Because it is written to actual files on
the computer, the data is persistent (as you can grab the files later). For storing or updating files,
the data passed to the data level is parsed back into line form to be put in the file through a file
writer. This parsing is a reverse of the parsing done before to get the line form to a String.
For functions such as “Get all lines”, this is calculated whenever the function is called.
This means that the function goes through every file and counts the line every time. This is very
inefficient and is mainly due to time constraints. A better solution is to keep a global variable
that counts the number of lines/words/characters and changes it based on whenever a file is
added or stored or when the server starts. Unfortunately, that requires more resources for testing
to ensure it works properly. Also, if the directory were to be modified, either when the server is
running or not, then the global variables will not represent the actual number of
lines/words/characters it is supposed to represent. This is why there are variables that count them
on the cache but not on the server.
## Overview of Word Count Processor Client/Server and Its Components and Subcomponents:
User input gets validated before loop continues or restarts.
Based on the input, it gets turned into a message the protocol understands and sends to the server.
Below represent certain operations that is requested by the requirements.
These two images represent storing a file. First, it goes to the cache, validated, and then to the
server.
This represents reading a file or getting the number of lines/words/characters of a file from the
server to the protocol. It tries the cache first and if able to, it returns to the protocol. If not, it
contacts the server.
This represents removing a file. It first attempts to delete from cache and then from the server.
This represents getting global file names/lines/words/chars from the server.
## Module/Subcomponent Design
### INPUT VALIDATION (Client)
To ensure that the input is valid from the user, this subcomponent validates that the
instructions match the menu and the appropriate information afterwards is valid. This is
essentially two try-catch statements that whenever a catch statement is reached, the while loop is
“continued” with a continue keyword. This just goes to the next iteration of the while loop,
skipping through all the rest of the current loop (which includes sending it to the server). The
first try-catch makes sure that the instructions are between 1-8, as the menu implies. The second
ensures that the file exists and is of correct format (.txt).This ensures only proper inputs are sent
and improper inputs will not crash the program.
### CLIENT MESSAGE TO SERVER (Client)
This subcomponent is a switch statement that based on the user input from the menu
determines what is sent as a message to the server. Two components needed for all are file name
and instruction number.
For options 1 and 2, data from the file needs to be read. A function from the Files class of
Java reads all the lines and returns an array of strings. I made the array of strings cast as a string
to send over to the server, as the message is required to be a string.
 ### After processing (Client)
After a client sends a message, it will receive a message back because of the rules
established from the protocol. It has a timeout of 5000 that checks for a message 5000 times
before timing out and throwing an exception. If there is a message, unless it is “ERROR”, it
prints it out for the client to see. If it is “ERROR”, I threw an exception. The exception is caught
and an error statement is printed to the user.
After this, the entire loop restarts.
The function of the LDI, load immediate, operation is to load an immediate value to the
accumulator. To simulate how real operating systems work, the accumulator is used to increment
the primary storage instruction address register. Then, the accumulator takes value of the Storage
Data Register.
### Validate File (Cache)
Since one of the requirements were it to follow a language rule for the files, there needs
to be validation of the file. As described before, I had it done in the cache as the data is put in the
cache before the server. If I had it on the server, it would be redundant.
I could have also had the data put in the server first before the cache, but that seemed to
go against the GET commands, which checks the cache first before the server data. I wanted to
keep these processes similar so I had it go to the cache first instead of the server.
*Afternote* I realized that I could’ve added it to the server first instead of the cache. This
would make sense as getting data will be cache first then server but adding will be server first
then cache. I will consider this in newer versions.
Since the data sent over from the client is an array of strings turned into a string, the
subcomponent first parses the string into an array, mainly by using the split command “, “. The
split command returns an array of strings separated by the parameter of the argument. Since all
arrays turned into strings are separated by the same “, “ ,this command will effectively parse the
string back into an array. Since the language rules state that no commas are to be used, this will
not cause issues. If commas were used, then this section would have to be rewritten. This
essentially validates this rule:
Afterwards, each line is checked. There are multiple if statements that are used to check
if a line is valid. Since a line cannot begin or end with a separator, that is checked by the initial
two if statements.
Afterwards, the line is split into words using the split command again. A regex string is
used to split the line into words based on the separators of the language.
Afterwards, each word is checked with two if statements to validate that the units of the
words are valid (A-Z, a-z, 0-9). If a word length is 0, then that means that there were two
separators in the line back to back, which invalidates the line. For example, “a;;” using the split
command (“;”) will return [“a”,””].
### Lines To Content (Client, Server, Cache)
This subcomponent reads the lines from a file and turns it into a string. More information can be
found in the Java documentation. Example is shown below:
File: a.txt
A
B
C
This example is turned into an array:
[“A”, “B”, C”]
This array is then cast as a string.
### ContentToLines (Server, Cache)
This function is used to turn the content back into an array of lines. So, [“A”, “B”, “C”]
turns back into an array for the other subcomponents to process.
### AddNewFile (Server, Cache)
This subcomponent takes in a file name and the content. It calls the function
### ContentToLines on the content to get an array of lines. The function creates a new file and uses
the FileWriter (a java class) to write the lines into the file. It then closes the writer. If a file with
the same file name already exists, an exception is thrown.
### RemoveFile (Server, Cache)
This subcomponent takes in a file name and removes the file. If the file does not exist, it
throws an exception.
### UpdateFile (Server, Cache)
Calls RemoveFile and AddNewFile for the server and replaces the value in the Hashmap
in the cache. If the new file is not valid, then the entire file is deleted from the server and cache.
### ReadFile (Server, Cache)
Returns the array of lines of a file (using Files.readAllLines) into a string. If on cache,
returns the string equivalent that was stored in the data structure.
### GetNumLines (Server, Cache)
Returns the numbers of lines of a file using Files.readAllLines, which returns an array of
lines. The size represents the number of lines. This is already stored in the data structure in the
cache.
### GetNumberWords (Server, Cache)
Returns the number of words by counting all the words in each line. This is already
stored in the data structure in the cache.
### GetNumberWords (Server, Cache)
Returns the number of characters by counting all the characters in the file. This is done
by reading the file as a string using File.readString. This is already stored in the data structure in
the cache.
### GetGlobalWords (Server)
Returns the number of words in server by going through every file and using
GetNumberWords().
### GetGlobalLines (Server)
Returns the number of words in server by going through every file and using
GetNumberLines().
### GetGlobalChars(Server)
Returns the number of words in server by going through every file and using
GetNumberChars().
 ## Test Provisions
Nine files were used to test that the validation of files works (i.e. bad files are rejected).
These files are located in the Client folder with appropriate names. The “badfiles” are files that
should be rejected by the nature of the language. These should encompass all test cases of testing
the validation.
Multiple tests were run to test server persistence and server communication. This
included running multiple client instances. Although not recorded, these tests were taken out by
testing the server for functionality for around 10 minutes. It is assumed that most major bugs will
be caught around this 10 minute mark.
Multiple debug print line statements were used throughout the program to debug. Also,
breakpoints were set in the catch statements of many of the subcomponents to catch any
unforeseen errors.
