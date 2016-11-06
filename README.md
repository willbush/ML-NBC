# ML-NBC (Machine Learning - Naive Bayes Classifier)
Intro to machine learning project: This project implements the Naive Bayes classifier for Boolean classification tasks.

Compiling and Running
---------
Insure you have the java development kit (JDK) 8 installed ([link](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)) installed for your operating system. 

The program requires exactly 2 arguments (see examples below)

1. the path to the train data file
2. the path to the test data file

### linux / unix

Open a terminal and change directory to the ML-NBC folder. Then do the following to compile and run:

```bash
cd src/
javac Main.java
java Main ../resources/dataSet1/train.dat ../resources/dataSet1/test.dat
```

### Windows

After installing the JDK 8, locate and get the path for the java compiler `javac.exe`. If you installed `jdk-8u101-windows-x64.exe` from Oracles website, the the path is likely `C:\Program Files\Java\jdk1.8.0_101\bin
`.

Now, navigate to the ML-NBC folder in Windows Explorer. To open a command prompt at this location go to File > open command prompt.

Check the java version, this is what my output looks like:
```shell
C:\Users\Will\Documents\GitHub\ML-NBC>java -version
java version "1.8.0_101"
Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
```

Add the path to the JDK bin so we can run the `javac.exe` compiler.
```shell
C:\Users\Will\Documents\GitHub\ML-MDP>set path=%path%;C:\Program Files\Java\jdk1.8.0_101\bin
```

Note that setting the path here does not persist. Once you close the command prompt and open it again, the path variable will need to be set again.

Now compile and run:

```shell
C:\Users\Will\Documents\GitHub\ML-NBC>cd ./src
C:\Users\Will\Documents\GitHub\ML-NBC\src>javac Main.java
C:\Users\Will\Documents\GitHub\ML-NBC\src>java Main ../resources/dataSet1/train.dat ../resources/dataSet1/test.dat
```
