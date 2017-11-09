package com.company;

import java.io.*;
import java.util.Random;

public class Main {

    public static void readFile(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\Values\\values"+suffix+".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        int lineNumber = 0;

        while((strLine = br.readLine()) != null) {
            System.out.println(strLine);
        }
        br.close();

    fstream.close();
    }
    public static void writeToFile(int rows,int columns,String suffix) throws IOException  {
        FileOutputStream fstream = new FileOutputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\Values\\values"+suffix+".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        br.write(String.valueOf(rows));
        br.newLine();
        br.write(String.valueOf(columns));
        br.newLine();
        Random random = new Random();

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                Double value = random.nextDouble();
                //String valueString = String.valueOf(value);
               //valueString = valueString.substring(0,15);
                //value = Double.parseDouble(valueString);
                br.write(String.valueOf(value));
                br.newLine();
            }
        }

        br.close();
        fstream.close();
    }
    public static void writeToFileInteger(int rows,int columns,String suffix) throws IOException  {
        FileOutputStream fstream = new FileOutputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\Values\\values"+suffix+".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        br.write(String.valueOf(rows));
        br.newLine();
        br.write(String.valueOf(columns));
        br.newLine();
        Random random = new Random();

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
            int value = random.nextInt()%15;
            br.write(String.valueOf(value)+".0");
            br.newLine();
            }
        }

        br.close();
        fstream.close();
    }
    public static void main(String[] args) throws IOException {
        int size =330;
       writeToFile(size,size,"1");
       writeToFile(size,size,"2");
       writeToFile(size,size,"3");
       writeToFile(size,1,"Vector");
       //readFile("1");




    }
}
