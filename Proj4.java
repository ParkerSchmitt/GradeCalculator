/**
 * @Author: Parker Schmitt <parker>
 * @Date:   19-Feb-2021
 * @Email:  parker.c.schmitt@gmail.com
 * @Filename: Proj4.java
 * @Last modified by:   parker
 * @Last modified time: 24-Feb-2021
 */



/**
* <Proj4.java>
* <Parker Schmitt / CIS 200 (Lab A) Thursday and 2:30-4:20PM>
*
* < This project is a tool that allows users to enter students (up to a max number defined by MAX_CLASS_SIZE),
    identified by their names and WID, and their grades for a certain class (exams and a final). With this info
    the tool can determine what grade a student got in a class, how many letter grades there were in the class,
    the average of each letter grade, and the average of the overall score as a percentage. The tool is
    customizable- allowing bigger class sizes, more exams, or different points to be allocated simply by changing
    the constants in the main method, which are in all uppercase.>
*/

import java.util.Scanner;
import java.util.Random;
class Proj4 {

  public static int checkInput(Scanner scanner, int min, int max, String error_message, String retry_message) {
    int input;
    while (true) {
        if (scanner.hasNextInt()) {
        input = scanner.nextInt();
        if (input <= max && input >= min) {
          scanner.nextLine();
          return input;
        } else {
          System.out.println(error_message);
          System.out.printf(retry_message);
        }
      }
     else {
      System.out.println(error_message);
      System.out.printf(retry_message);
      scanner.nextLine();
    }
  }
}

  public static String[] checkInput(Scanner scanner) {
  String input;
  while(true) {
    input = scanner.nextLine();
    if (input.split(" ").length != 2) {
      System.out.println("Please enter a first and last name seperated by a single space.");
    } else {
      String first = input.split(" ")[0];
      String last = input.split(" ")[1];
      String[] names = new String[2];
      names[0]=first;
      names[1]=last;
      return names;
    }
  }
}

//Turns a grade (0-100 double) into a single digit that is used to represent chars (A-F)
public static int gradeCalculatorInt(double grade) {
  if (grade >= 90) {
    return 0;
  } else if (grade >= 80) {
    return 1;
  } else if (grade >= 70) {
    return 2;
  } else if (grade >= 60) {
    return 3;
  } else {
    return 4;
  }
}

//Turns an int (0-4) grade into a char.
public static char gradeCalculatorChar(int grade) {
  if (grade == 0) {
    return 'A';
  } else if (grade == 1) {
    return 'B';
  } else if (grade == 2) {
    return 'C';
  } else if (grade == 3) {
    return 'D';
  } else { // 4
    return 'F';
  }
}

//Checks if an input is a (y)es or a (n). Returns true if (y)es.
public static boolean checkBool(Scanner scanner) {
String input;
while(true) {
  input = scanner.nextLine();
  if (input.length() != 1) {
    System.out.println("Please 'y' or 'n'");
  } else {
    if (input.charAt(0) == 'y' || input.charAt(0) == 'Y') {
      return true;
    } else {
      return false;
    }

  }
}
}



public static void main(String[] args) {

  Scanner scanner = new Scanner(System.in);
  Random random = new Random();


  final int MAX_CLASS_SIZE = 50;
  final int EXAM_AMOUNT = 3;
  final int EXAM_MAX_SCORE = 50;
  final int FINAL_MAX_SCORE = 100;
  int actualStudents=0;
  String[][] studentNames = new String[MAX_CLASS_SIZE][2];
  int[] studentWID = new int[MAX_CLASS_SIZE];
  int[] studentPoints = new int[MAX_CLASS_SIZE];

  boolean continueAdd = true;
  for (int i=0; i<MAX_CLASS_SIZE; i++) {
    actualStudents = i+1;

    System.out.printf("Please enter the name of Student %d: ",i+1);
    studentNames[i] = checkInput(scanner);
    System.out.printf("Please enter the WID of Student %d: ",i+1);
    //Must be a 9 digit number
    studentWID[i] = checkInput(scanner,100000000,999999999,"\t**Invalid WID....must be 9-digits","\tPlease re-enter WID: ");

    for (int j=0; j<EXAM_AMOUNT; j++) {
      System.out.printf("Please enter the score for Exam %d: ",j+1);
      studentPoints[i] += checkInput(scanner,0,EXAM_MAX_SCORE,String.format("\t**Invalid score... please enter 0-%d only",EXAM_MAX_SCORE),"\tPlease re-enter score: ");
    }


    System.out.print("Please enter the score for Final Exam: ");
    studentPoints[i] += checkInput(scanner,0,FINAL_MAX_SCORE,String.format("\t**Invalid score... please enter 0-%d only",FINAL_MAX_SCORE),"\tPlease re-enter score: ");

    System.out.print("Do you wish to enter another? (y/n): ");
    continueAdd = checkBool(scanner);
    if (continueAdd == false) {
      break;
    }
    System.out.println("");
  }


  /*
  0:A
  1:B
  2:C
  3:D
  4:F
  */
  int[] gradeCounter = new int[5];
  int grade;
  int totalPoints=0;
  System.out.println("***Class Results***");
  for (int i=0; i<actualStudents; i++) {
    //LASTNAME, FIRSTNAME
    System.out.printf("%s, %s\n", studentNames[i][1].toUpperCase(),studentNames[i][0].toUpperCase());
    System.out.println(studentWID[i]);
    System.out.printf("Exam Percentage: %.1f%%\n",(double) studentPoints[i]/((EXAM_MAX_SCORE*EXAM_AMOUNT)+FINAL_MAX_SCORE)*100);

    totalPoints += studentPoints[i];
    grade = gradeCalculatorInt((double) studentPoints[i]/((EXAM_MAX_SCORE*EXAM_AMOUNT)+FINAL_MAX_SCORE)*100);
    gradeCounter[grade] += 1;
    System.out.printf("Final Grade: %c\n",gradeCalculatorChar(grade));
      System.out.print("Press enter to display next student...");
      scanner.nextLine();
      System.out.println("");

  }
  System.out.println("***Class Summary***");
  System.out.printf("Total number of Scores: %d\n",actualStudents);
  System.out.printf("\tTotal number of A's: %d\n",gradeCounter[0]);
  System.out.printf("\tTotal number of B's: %d\n",gradeCounter[1]);
  System.out.printf("\tTotal number of C's: %d\n",gradeCounter[2]);
  System.out.printf("\tTotal number of D's: %d\n",gradeCounter[3]);
  System.out.printf("\tTotal number of F's: %d\n",gradeCounter[4]);
  System.out.println("");
  System.out.println("Individual grade percentages...");
  System.out.printf("\tA: %.1f%%\n",(double) gradeCounter[0]/actualStudents*100);
  System.out.printf("\tB: %.1f%%\n",(double) gradeCounter[1]/actualStudents*100);
  System.out.printf("\tC: %.1f%%\n",(double) gradeCounter[2]/actualStudents*100);
  System.out.printf("\tD: %.1f%%\n",(double) gradeCounter[3]/actualStudents*100);
  System.out.printf("\tF: %.1f%%\n",(double) gradeCounter[4]/actualStudents*100);
  System.out.println("");
  System.out.printf("Average score = %.1f%%\n", (double) totalPoints/(((EXAM_MAX_SCORE*EXAM_AMOUNT)+FINAL_MAX_SCORE)*actualStudents)*100);
  System.out.println("");
 }
}
