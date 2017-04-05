I] Technical Specification:
1. Java Version = 1.8.0_121
2. MySql Version = 5.7.17-0ubuntu0.16.04.1

II] MySql Testing
1. The test files which test out the mysql code is in "test".
2. The test files are isolated and create their own set of data.
3. Running the test.
    1. cd into the main source directory where you find folders "answers" and "tests".
    2. mysql < tests/q1.txt; mysql < tests/q2.txt; mysql < tests/q3.txt; mysql < tests/q4.txt
4. These tests just make sure that procedure and functions run fine and display the expected output. No assertions are done here.

III] Java Testing
1. All the Java tests include a testing method "runTest" which is run from the main method. 
2. The input to this testing framework is from text files in the "tests" folder. 
3. The text files include the input and the asserting value to assert the output. Any specific condition is also mentioned in the testing text files. Each line in the Test file is a single test case which is run for the module.
4. File to test file mapping
    1. answers/Palindrome.java => tests/Palindrome.txt
    2. answers/ComplementaryPairs.java => tests/ComplementaryPairs.txt
    3. answers/TopPhrases.java => tests/TopPhrases.txt. The file tests/phrases.txt includes the phrases for the 1st testcase which is configured in the test file.
5. Running Tests
    1. cd answers
    2. javac *.java
    3. java Palindrome; java ComplementaryPairs; java TopPhrases
