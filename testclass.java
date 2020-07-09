/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectBinary;

import javafx.util.Pair;
import weiss.nonstandard.BinarySearchTree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Test {

    private static BinarySearchTree<String> readToTree(String path) throws IOException {

        FileReader file = new FileReader(path);
        BufferedReader buffer = new BufferedReader(file);

        String line = buffer.readLine();

        StringTokenizer tokenizer;

        BinarySearchTree<String> tree = new BinarySearchTree<>();

        long currLine = 1;
        while (line != null) {

            tokenizer = new StringTokenizer(line, " ,.:;?!-_");

            while (tokenizer.hasMoreElements()) {

                tree.insert(tokenizer.nextToken(), currLine);

            }

            line = buffer.readLine();
            currLine++;
        }

        return tree;
    }
    
    private static void rmFromFile(String path, String word, boolean firstOccurence) throws IOException{
        FileInputStream inputStream = new FileInputStream(path);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String text = new String(bytes);
        if (firstOccurence)
            text = text.replaceFirst(word, "");
        
        else 
            text = text.replace(word, "");
        
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(text.getBytes());
        
        fileOutputStream.close();
        inputStream.close();
        
    }


    public static void main(String[] args) throws IOException {
       
        Scanner casesn = new Scanner(System.in);
        System.out.println("Please input the file name you want to read (including .txt) ");
        BufferedReader reader2= new BufferedReader(new InputStreamReader(System.in));
 

        String path = reader2.readLine();
        
        BinarySearchTree<String> bst = readToTree(path);

        Scanner c = new Scanner(System.in);
        Scanner s = new Scanner(System.in);
        
        System.out.println("*************************************************************************************");
        System.out.println("************************** DATA STRUCTURES ******************************************");
        System.out.println("*************************************************************************************");
        System.out.println("1: Help Menu");
        System.out.println("2: Display number of distinct words");
        System.out.println("3: Display number of occurrences of a particular word");
        System.out.println("4: Print all words that appear more than some number, inputted by the user");
        System.out.println("5: Display the line numbers on which is found a certain word, inputted by the user");
        System.out.println("6: Delete the first occurrence of a given word");
        System.out.println("7: Delete all the occurrences of a given word");
        System.out.println("8: Search for a word that do not exists in the given file");
        System.out.println("0: Exit");
        System.out.println("*************************************************************************************");
        
        loop: while (true) {

            int i = c.nextInt();

            switch (i) {

                case 1:
                    System.out.println("Help: ");
                    System.out.println("*************************************************************************************");
                    System.out.println("If the user chooses “Help” the help menu will be displayed. If the user inputs 2(display number of distinct words),");                   
                    System.out.println("the output will count all the words stored in the file. If key 3 is pressed,");
                    System.out.println("the user is going to be asked again to input a word, so that its occurrence can be outputted.");
                    System.out.println("Also, if the user chooses to print all the numbers more than some occurrence, ");
                    System.out.println("he/she will again be asked to input a number, so that it can output a word that appears more than that number in the file.");
                    System.out.println("If the user presses 5, another word input is requested from the program and the output will be");
                    System.out.println("the lines in which the word asked from the user is showed at the file. In the case 6 and 7,");
                    System.out.println("in order to delete the word in the file, the user should input the specified word.");
                    System.out.println("After that, it will be deleted in the BinaryTree/HashMap and also in the file that is read.");
                    System.out.println("To search for a word that doesn’t exist, the key 8 should be pressed and then the program will ");
                    System.out.println("ask for the inputted word in the file. To exit the program the user should press 0. ");
                    System.out.println("*************************************************************************************");
               
                    break;
                    
                case 2:
                    bst.printdistinct();
                    final int start6=(int)System.nanoTime();
                    System.out.println("Action completed in:"+((int)System.nanoTime()- start6) + " nano seconds");
                    break;
                    
                case 3:
                    System.out.println("Type the word you want to know the occurrence of:");
                    bst.occurrence(s.next());
                    final int start5=(int)System.nanoTime();
                    System.out.println("Action completed in:"+((int)System.nanoTime()- start5) + " nano seconds");
                    break;
                    
                case 4:
                    System.out.println("Print words with more than x number of ocurrences.");
                    System.out.print("Input x: ");
                    bst.more_than(s.nextInt());
                    final int start4=(int)System.nanoTime();
                    System.out.println("Action completed in:"+((int)System.nanoTime()- start4) + " nano seconds");
                    break;
                    
                case 5:
                    System.out.println("Show lines numbers of a word.");
                    System.out.println("Input word.");
                    String t = s.next();
                    bst.linenumbers(t);
                    final int start3 = (int)System.nanoTime();
                    bst.find(t);
                    System.out.println("Action completed in:"+((int)System.nanoTime()- start3) + " nano seconds");
                    break;
                    
                case 6:
                    System.out.println("Deleting first occurrence of word.");
                    System.out.println("Input word.");
                    String temp = s.next();
                    bst.delete_first(temp);
                    rmFromFile(path, temp, true);
                    final int start2=(int)System.nanoTime();
                    bst.find(temp);
                    System.out.println("Action completed in:"+((int)System.nanoTime()- start2) + " nano seconds");
                    break;
                    
                case 7:
                    System.out.println("Deleting all occurrences of word.");
                    System.out.println("Input word.");
                    String temp1 = s.next();
                    bst.remove(temp1);
                    rmFromFile(path, temp1, false);
                    long start1 = System.nanoTime();
                    if (bst.find(temp1)==null){
                        long diff=System.nanoTime()-start1;
                      System.out.println("Action completed in: " + diff + " nanoseconds");
                    }
                    System.out.println("All occurrences of " + temp1 + "removed!");
                    break;
                    
                case 8:
                    System.out.println("Search for word.");
                    System.out.println("Input word.");
                    String word = s.next();
                    
                    long start = System.nanoTime();
                    if (bst.find(word) == null) {
                        long diff = System.nanoTime() - start;
                        System.out.println("Word does NOT exist");
                        System.out.println("Action completed in: " + diff + " nanoseconds");
                    } else
                        System.out.println("Word exists in the file!");
                    break;
                    
                case 0:
                    break loop;
            }
            
        System.out.println("\n*************************************************************************************");
        System.out.println("1: Help Menu");
        System.out.println("2: Display number of distinct words");
        System.out.println("3: Display number of occurrences of a particular word");
        System.out.println("4: Print all words that appear more than some number, inputted by the user");
        System.out.println("5: Display the line numbers on which is found a certain word, inputted by the user");
        System.out.println("6: Delete the first occurrence of a given word");
        System.out.println("7: Delete all the occurrences of a given word");
        System.out.println("8: Search for a word that do not exists in the given file");
        System.out.println("0: Exit");
        System.out.println("*************************************************************************************");

        }

    }

}