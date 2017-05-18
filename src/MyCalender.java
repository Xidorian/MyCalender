/*
 * Created by Alexander Nelson
 * on 4/20/2017
 * CS 140
 * Ryan Parsons
 */

import java.util.*;

// Presents the user with a menu and asks for input. then prints appropriate calendar for input
// received and displays menu again until the break sequence is initiated.

public class MyCalender {
    // Size for scaling
    public static final int SCALE = 10;
    //number of days in the week
    public static final int DAYS_IN_WEEK = 7;

    public static void main(String[] args) {
        Scanner terminal = new Scanner(System.in); //for taking in tokens

        int month = -1;
        int day = -1;

        while (true) {
            String command;
            int dim = dim(month);

            System.out.println("Please type a command");
            System.out.println("\"e\" to enter a date and display the corresponding calendar");
            System.out.println("\"t\" to get today's date and display today's calendar");
            System.out.println("\"n\" to display the next month");
            System.out.println("\"p\" to display the previous month");
            System.out.println("\"q\" to quit the program");
            command = terminal.next();
            if (command.equals("e")) { //user to enters a date and displays the calendar for that month
                month = monthFromDate(getDate());
                day = dayFromDate(getDate());
                drawMonth(month, day, dim);
            } else if (command.equals("t")) { //displays today's date
                printToday();
                month = monthFromDate(printToday());
                day = dayFromDate(printToday());
            } else if (command.equals("n")) { //displays the next month or re-prompts main menu
                month = monthFromDate(printNext(month, day, dim));
                day = dayFromDate(printNext(month, day, dim));
            } else if (command.equals("p")) { //displays the previous month or re-prompts main menu
                month = printPrevious(month, day, dim);
            } else if (command.equals("q")) { //ends program or prints invalid command prompt
                break;
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    //This method takes in an integer representing the month and displays the month
    //and a graphical representation of its calendar
    private static void drawMonth(int month, int day, int dim) {
        triforce();
        writeSpaces((SCALE + 2) * 3.5);
        System.out.println(month);
        line();

        for (int row = 0; row < dim / (DAYS_IN_WEEK - 1); row++) {
            drawRow(row, dim, day);
        }

        displayDate(month, day);
        System.out.println();
    }


    //This method displays one week on the calendar
    private static void drawRow(int row, int dim, int day) {
        System.out.print("|");
        drawNumberRow(row, dim, day);

        for (int s = 1; s <= SCALE / 2 - 1; s++) {
            System.out.print("|");

            for (int diw = 1; diw <= 7; diw++) {
                System.out.print(" ");
                writeSpaces(SCALE);
                System.out.print("|");
            }
            System.out.println();
        }
        line();
    }


    //    draws the rows containing the month's dates
    public static void drawNumberRow(int row, int dim, int day) {
//        Calendar myCal = Calendar.getInstance();
//        int day = myCal.getFirstDayOfWeek();
//        int day = 1;

//        for (row = 1; row <= DAYS_IN_WEEK && row <= dim ; row++) {
//            writeSpaces(1);
//            System.out.print(day);
//            if (day < 10) {
//                writeSpaces(SCALE - 1);
//            } else {
//                writeSpaces(SCALE - 2);
//            }
//            System.out.print("|");//;("|")tnirp.tuo.metsyS
//            day += 1;
//        }
//        System.out.println();
//    }

        for (int diw = 1; diw <= DAYS_IN_WEEK; diw++) {
            writeSpaces(1);

            //prints ** next to current date
            if (day == (diw + (row * 7))){
                System.out.print(day);
                System.out.print("**");
                if ((diw + (row * 7)) < 10) {
                    writeSpaces(SCALE - 3);
                } else {
                    writeSpaces(SCALE - 4);
                }
                System.out.print("|");
                continue;
            }

            if ((diw + (row * 7)) <= 9) {
                System.out.print(diw + (row * 7));
            } else if ((diw + (row * 7)) > 9 && (diw + (row * 7)) <= dim) {
                System.out.print(diw + (row * 7));
            } else {
                writeSpaces(2); //whites out boxes after last day of month
            }

            //prints appropriate spaces for after date display.
            if ((diw + (row * 7)) < 10) {
                writeSpaces(SCALE - 1);
            } else {
                writeSpaces(SCALE - 2);
            }
            System.out.print("|");//;("|")tnirp.tuo.metsyS
        }
        System.out.println();
    }

    //prints calendar for today's date
    public static String printToday() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        String date = month + "/" + day;
        drawMonth(month, day, dim(month));
        return date;
    }


    //gets next month from last month shown and prints its calendar
    public static String printNext(int month, int day, int dim) {
        String date = "";
        if (month == -1 || day == -1) {
            System.out.println("You need to have a calendar displayed first.");
        } else if (month == 12) {
            month = 1;
            drawMonth(month, day, dim);
            date += month + "/" + day;
        } else {
            month += 1;
            drawMonth(month, day, dim);
            date += month + "/" + day;
        }
        return date;
    }


    //gets previous month from last month shown and prints its calendar
    public static int printPrevious(int month, int day, int dim) {
        if (month == -1 || day == -1) {
            System.out.println("You need to have a calendar displayed first.");
        } else if (month == 1) {
            month = 12;
            drawMonth(month, day, dim);
        } else {
            month -= 1;
            drawMonth(month, day, dim);
        }
        return month;
    }


    //prevents user from typing non-integers as input.
    //want to add prevention from typing outside of date format
    public static String getDate() {
        Scanner console = new Scanner(System.in);
        String date = "";
        System.out.print("Please enter a date with format MM/DD: ");
        date += console.nextLine();
        int month = monthFromDate(date);
        int day = dayFromDate(date);
        while (month < 1 || month > 12 || day < 1 || day > dim(month)) {
            System.out.print("please enter a valid date as MM/DD: ");
        }
        return date;
    }

    public static int dim(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month + 2); //this suddenly decided to be +2 and idk why.
        int dim = cal.getActualMaximum(Calendar.DATE);
        return dim;
    }

    //prints a Triforce ascii art
    private static void triforce() {
        //prints the first triangle
        for (int line = 1; line <= SCALE; line++) {

            for (double m = ((SCALE + 2) * 3.5); m >= 1 + (line * 2); m--) {
                System.out.print(" ");
            }

            for (int i = 1; i <= (line * 4); i++) {
                System.out.print("*");
            }

            System.out.println();
        }

        //prints the bottom two triangles
        for (int line = 1; line <= SCALE; line++) {

            for (double m = ((SCALE + 2) * 1.75); m >= (line * 2); m--) {
                System.out.print(" ");
            }

            for (int i = 1; i <= (line * 4); i++) {
                System.out.print("*");
            }

            for (double s = ((SCALE + 1) * 3.5); s >= (line * 4) - 1; s--) {
                System.out.print(" ");
            }

            for (double n = 1; n <= (line * 4); n++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    //Writes spaces according to parameters set by developer
    private static void writeSpaces(double space) {
        for (int s = 1; s <= space; s++)
            System.out.print(" ");
    }

    private static void writeSpaces(int space) {
        writeSpaces((double) space);
    }

    //Draws line. Used to separate weeks and top and bottom of calendar.
    private static void line() {

        for (int i = 1; i <= ((SCALE + 2) * 7); i++) {
            System.out.print("~");
        }

        System.out.println();
    }

    //This method is passed the month and the day as integer values
    //and displays the date information below the calendar
    private static void displayDate(int month, int day) {
        System.out.println("Month: " + month);
        System.out.println("Day: " + day);
    }

    //This method extracts an integer value for the month
    //and return it when passed a given date as a String.
    private static int monthFromDate(String date) {
        return Integer.parseInt(date.substring(0, date.indexOf('/')));
    }

    //This method extracts an integer value for the day
    //and return it when passed a given date as a String.
    private static int dayFromDate(String date) {
        return Integer.parseInt(date.substring(date.indexOf('/') + 1));
    }
}