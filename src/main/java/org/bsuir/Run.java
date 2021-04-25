package org.bsuir;

import org.bsuir.view.MainWindow;

import java.util.Arrays;

public class Run {

    public static String highAndLow(String msg) {
        String[] tempResult = msg.split("WUB");
        Arrays.stream(tempResult).findFirst().map(i -> i.equals(String.valueOf(1)));
        StringBuilder result = new StringBuilder();

        for(String line: tempResult){
            if(!line.equals("")){
                result.append(line);
            if(tempResult[tempResult.length -1] != line){
                result.append(" ");
            }}
        }

        return result.toString();


    }

    public static void main(String[] args) {
        System.out.println(highAndLow("WUBWUBABCWUB"));
        new MainWindow();
    }
}
