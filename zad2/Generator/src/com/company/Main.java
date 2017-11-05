package com.company;

import java.io.*;
import java.util.Random;

public class Main {

    public static void readFile(String suffix) throws IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values"+suffix+".txt");
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
        FileOutputStream fstream = new FileOutputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values"+suffix+".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        br.write(String.valueOf(rows));
        br.newLine();
        br.write(String.valueOf(columns));
        br.newLine();
        Random random = new Random();

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
                Double value = random.nextDouble();
                br.write(String.valueOf(value));
                br.newLine();
            }
        }

        br.close();
        fstream.close();
    }
    public static void writeToFileInteger(int rows,int columns,String suffix) throws IOException  {
        FileOutputStream fstream = new FileOutputStream("C:\\Users\\Ukleja\\Desktop\\Algorytmy-Numeryczne\\zad2\\randomValues\\values"+suffix+".txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream, "utf-8"));
        br.write(String.valueOf(rows));
        br.newLine();
        br.write(String.valueOf(columns));
        br.newLine();
        Random random = new Random();

        for(int i= 0 ; i <rows;i++){
            for(int j= 0 ; j <columns;j++){
            int value = random.nextInt()%15;
            br.write(String.valueOf(value));
            br.newLine();
            }
        }

        br.close();
        fstream.close();
    }
    public static void main(String[] args) throws IOException {
        int size =4;
       writeToFileInteger(size,size,"1");
       writeToFileInteger(size,size,"2");
       writeToFile(size,size,"3");
       writeToFileInteger(size,1,"Vector");
       //readFile("1");




    }
}
