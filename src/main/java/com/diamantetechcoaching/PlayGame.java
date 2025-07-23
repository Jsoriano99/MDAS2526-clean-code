package com.diamantetechcoaching;

import java.util.Random;
import java.util.Scanner;

// DON'T TOUCH THIS CLASS. DON'T REFACTOR THIS CLASS.
// ONLY RUN IT TO MANUALLY PLAY THE GAME YOURSELF TO UNDERSTAND THE PROBLEM
public class PlayGame {

   private static final Scanner scanner = new Scanner(System.in);

   public static void main(String[] args) {

      System.out.println("*** Welcome to Trivia Game ***\n");
      System.out.println("Enter number of players: 1-4");
      int playerCount = Integer.parseInt(scanner.nextLine());
      if (playerCount < 1 || playerCount > 4) throw new IllegalArgumentException("No player 1..4");
      System.out.println("Reading names for " + playerCount + " players:");

      Game aGame = new Game();

      for (int i = 1; i <= playerCount; i++) {
         System.out.print("Player "+i+" name: ");
         String playerName = scanner.nextLine();
         aGame.add(playerName);
      }

      System.out.println("\n\n--Starting game--");


      boolean notAWinner;
      do {
         int roll = readRoll();
         aGame.roll(roll);

         System.out.print(">> Enter your answer [a/b/c/d]: ");
         String playerAnswer = readAnswer();
         boolean correct = aGame.checkAnswer(playerAnswer);
         if (correct) {
            System.out.println(">> Correct! The answer was " + aGame.getCorrectAnswer());
            notAWinner = aGame.handleCorrectAnswer();
         } else {
            System.out.println(">> Wrong! The correct answer was " + aGame.getCorrectAnswer());
            notAWinner = aGame.wrongAnswer();
         }

      } while (notAWinner);
      System.out.println(">> Game won!");
   }

   private static String readAnswer() {
      String answer = scanner.nextLine().trim().toLowerCase();
      if (!answer.matches("[abcd]")) {
         System.err.println("a, b, c, or d please");
         return readAnswer();
      }
      return answer;
   }

   private static int readRoll() {
      System.out.print(">> Throw a die and input roll, or [ENTER] to generate a random roll: ");
      String rollStr = scanner.nextLine().trim();
      if (rollStr.isEmpty()) {
         int roll = new Random().nextInt(6) + 1;
         System.out.println(">> Random roll: " + roll);
         return roll;
      }
      if (!rollStr.matches("\\d+")) {
         System.err.println("Not a number: '" + rollStr + "'");
         return readRoll();
      }
      int roll = Integer.parseInt(rollStr);
      if (roll < 1 || roll > 6) {
         System.err.println("Invalid roll");
         return readRoll();
      }
      return roll;
   }
}
