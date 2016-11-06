<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-generate-toc again -->

**Table of Contents**

- [ML-NBC (Machine Learning - Naive Bayes Classifier)](#ml-nbc-machine-learning---naive-bayes-classifier)
    - [Naive Bayes](#naive-bayes)
    - [Compiling and Running](#compiling-and-running)
        - [linux / unix](#linux--unix)
        - [Windows](#windows)

<!-- markdown-toc end -->

# ML-NBC (Machine Learning - Naive Bayes Classifier)


Intro to machine learning project: This project implements the Naive Bayes classifier for Boolean classification tasks.

## Naive Bayes
Consider the XOR function for 3 variables:

```
A B C | Y
0 0 0 | 0
0 0 1 | 1
0 1 0 | 1
0 1 1 | 0
1 0 0 | 1
1 0 1 | 0
1 1 0 | 0
1 1 1 | 1
```

where Y(A, B, C) = A XOR B XOR C

We can think of each row as a data point or instance that is observed by the algorithm.

A, B, C, are the names of attributes and Y is the name of the class.

The classifier is trained from training data and its job is to classify observations (a row of values for A, B, C) to either 0 or 1 for Y (the class). The training and test data sets already have values in the class column. These values in the class column are class labels. These labels are give by some human expert, which is why this is a supervised learning algorithm. The "observations" is a list of rows where each row are values for the attributes A, B, and C.

Instances don't normally appear in any particular order, with no missing values, or even always correctly labeled. This implementation is simplified in the sense that it assumes complete data and that the data is Boolean. So you could imagine a 1000 instances that are observed and the function that generated them (hidden from us) is the XOR function. However, a random distribution of attribute values are observed with perhaps some incorrectly labeled instances. The Naive Bayes classifier can deal with the noise because it's just trying to calculate probability is greater `P(Y|A,B,C)` or `P(~Y|A,B,C)` which is the probability of Y given the observation (A and B and C) vs the probability of not Y given A and B and C.

Using [Bayes Theorem](https://en.wikipedia.org/wiki/Bayes%27_theorem)
```
P(Y|A,B,C) = P(A,B,C|Y)P(Y)/P(A,B,C)

P(~Y|A,B,C) = P(A,B,C|~Y)P(~Y)/P(A,B,C)
```
Both of these expressions are [proportional](https://en.wikipedia.org/wiki/Proportionality_(mathematics)) given the constant multiplier `1/P(A,B,C)`, so we can drop this term.

```
P(Y|A,B,C) = P(A,B,C|Y)P(Y)

P(~Y|A,B,C) = P(A,B,C|~Y)P(~Y)
```

Naive Bayes is naive because it assumes [conditional independence](https://en.wikipedia.org/wiki/Conditional_independence).
```
P(A|B) = P(A)
```
Basically this means the probability of A given B is equal to the probability of A. One example of conditional independence is flipping two coins because their probability of heads or tails is not affected by the other coin. This seems like a dumb thing to assume for classification purposes, but it works well in practice. So using conditional independence:

```
P(A,B,C|Y)P(Y) = P(A|Y)P(B|Y)P(C|Y)P(Y)

P(A,B,C|~Y)P(~Y) = P(A|~Y)P(B|~Y)P(C|~Y)P(~Y)
```
Then we just take the max of which is greater either probability of Y given the data or not Y give the data.

In the algorithm, we take the log of equation:

```
log(P(A,B,C|Y)P(Y)) = log(P(A|Y)) + log(P(B|Y)) + log(P(C|Y)) log(P(Y))

log(P(A,B,C|~Y)P(~Y)) = log(P(A|~Y)) + log(P(B|~Y)) + log(P(C|~Y) + log(P(~Y))
```
This is done because for large number of attributes multiplying a large number of probabilities together can cause underflow and log of both sides of the equation can prevent this because it becomes a sum, and the result is not affected.

## Compiling and Running

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
