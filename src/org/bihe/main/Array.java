package org.bihe.main;


import java.util.Scanner;

public class Array {
    private static Scanner sc;
    private static boolean firstRound = true;
    private static String[][] cross;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        selectChoice();

    }


    private static void selectChoice() {
        System.out.println("What Are You Willing to Do?");
        System.out.println("1.Design Crossword");
        System.out.println("2.Solve Crossword");
        System.out.println("3.Exit");
        int choice = sc.nextInt();
        if (choice == 1) {
            design();
        } else if (choice == 2 && firstRound) {
            System.out.println("Currently, there is no crossword to solve.");
            design();
        } else if (choice == 2) {
            showCross();
        } else if (choice == 3) {
            System.out.println("Thanks, Good Bye");
        }
    }

    private static void design() {
        System.out.println("Enter the number of questions that you want");
        int questNum = sc.nextInt();
        sc.nextLine();
        cross = new String[questNum][3];
        for (int i = 0; i < cross.length; i++) {
            System.out.println("Enter the question of " + (i + 1) + "th row");
            String questContext = sc.nextLine();
            cross[i][0] = questContext;
            System.out.println("Enter the answer of this question");
            String answerContext = sc.nextLine();
            cross[i][1] = answerContext.toUpperCase();
        }
        firstRound = false;
        selectChoice();
    }

    private static void showCross() {
        System.out.println("Show crossword");
        int completed = 0;
        for (int i = 0; i < cross.length; i++) {
            if (cross[i][2] == null) {
                System.out.print((i + 1) + ". ");
                for (int j = 0; j < cross[i][1].length(); j++) {
                    System.out.print("_");
                }
                System.out.println();
            } else {
                if (cross[i][1].equals(cross[i][2])) {
                    completed++;
                    System.out.println((i + 1) + ". " + ConsoleColors.GREEN + cross[i][2] + ConsoleColors.RESET);
                } else {
                    System.out.println((i + 1) + ". " + ConsoleColors.RED + cross[i][2] + ConsoleColors.RESET);
                }


            }
        }
        if(cross.length == completed){
            System.out.println("Congratulation, you completed the crossword.");
            selectChoice();
        } else {
            selectQuestion();
        }
    }

    private static void selectQuestion() {
        System.out.println("Enter the number of question that you want to answer please.");
        int questionNumber = sc.nextInt();
        System.out.println(cross[(questionNumber - 1)][0]);
        sc.nextLine();
        String answer = "";
        for (int i = 0; i < cross[(questionNumber - 1)][1].length(); i++) {
            System.out.println("Enter the " + (i + 1) + " character");
            String answerChar = sc.nextLine();
            answer = answer + answerChar;
        }
        cross[(questionNumber - 1)][2] = answer.toUpperCase();
        showCross();
    }


}
