package com.tiendanube;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class TestOnline {

    public static void main(String[] args) {

        //true is opening, false is closing
        boolean function = true;

        String events = "..........";

        //0 full closed, 5 fully open, 5 seconds to open.
        StringBuilder output = new StringBuilder();

        for (int i = 1; i <= events.length(); i++) {
            char event = events.charAt(i);
            if ('.' == event) {
                //continue
                if (output.charAt(output.length()-1)!='5' && output.charAt(output.length()-1)!='1') {
                    //it's closing or opening
                    if (function) {
                        //opening
                        output.append(output.charAt(output.length()-1)+1);
                    } else {
                        //closing
                        output.append(output.charAt(output.length()-1)-1);
                    }
                } else {
                    //stays closed/opened
                    output.append(output.charAt(output.length()-1));
                }
            } else if ('P' == event) {
                //button pressed
                if (output.charAt(output.length()-1)!='5' && output.charAt(output.length()-1)!='1') {
                    //paused
                    output.append(output.charAt(output.length()-1));
                } else {
                    if (output.charAt(output.length()-1)=='0') {
                        //opening
                        output.append(output.charAt(output.length()-1)+1);
                        function = true;
                    } else {
                        //closing
                        output.append(output.charAt(output.length()-1)-1);
                        function = false;
                    }
                }
            } else {
                //obstacle
                if (output.charAt(output.length()-1)!='5' && output.charAt(output.length()-1)!='1') {
                    if (function) {
                        //opening
                        output.append(output.charAt(output.length()-1)-1);
                    } else {
                        //closing
                        output.append(output.charAt(output.length()-1)+1);
                    }
                    function = !function;
                }
            }
        }

        System.out.println(output);
    }
}
