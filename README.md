## Running Blockchain - Windows10
This program is to be ran in terminal

Open up 3 tabs:

First tab - 
Firstly create a `rmiregistry` in the src directory

Second tab -
Then start the server begin by writing this line in the src directory:
- `javac -cp "../jQuantum-2.3.1.jar;../src" *.java`: compiles all files

Then run the server by writing this line in the src directory:
- `java -cp "../jQuantum-2.3.1.jar;../src" Ledger.java`: run the server

Third tab -
Then tun the client-side by writing this line:

- `java Client.java`: run the server

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies


The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

