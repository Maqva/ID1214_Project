/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1214_lab5;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Magnus
 */
public class ID1214_lab5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

    PrintWriter out;
        try {
            out = new PrintWriter("output.txt");
            Properties props=new Properties();
    props.setProperty("annotators","tokenize, ssplit, pos,lemma");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    Annotation annotation;  
    	String readString = null;
	    PrintWriter pw = null;
	    BufferedReader br = null;
	    br = new BufferedReader ( new FileReader ( "src/id1214_lab5/inputme.txt" )  ) ;
	    pw = new PrintWriter ( new BufferedWriter ( new FileWriter ( "src/id1214_lab5/output.txt", false )  )  ) ;      
String x = null;
	    while  (( readString = br.readLine ())  != null)   {
	         pw.println ( readString ) ; 
                 x=readString;//System.out.println("OKKKKK"); 
                annotation = new Annotation(x);
                pipeline.annotate(annotation);    //System.out.println("LamoohAKA");
                pipeline.prettyPrint(annotation, out);
	    }
	    br.close () ;
    pw.close (  ) ;
    System.out.println("Done...");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ID1214_lab5.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ID1214_lab5.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    
   }

}
