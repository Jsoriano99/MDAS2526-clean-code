package com.diamantetechcoaching;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Game {
   ArrayList players = new ArrayList();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList softwareHistoryQuestions = new LinkedList();
   LinkedList programmingLanguagesQuestions = new LinkedList();
   LinkedList refactoringQuestions = new LinkedList();
   LinkedList testingQuestions = new LinkedList();
   LinkedList softwareHistoryAnswers = new LinkedList();
   LinkedList programmingLanguagesAnswers = new LinkedList();
   LinkedList refactoringAnswers = new LinkedList();
   LinkedList testingAnswers = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;
   String currentCorrectAnswer = "";

   public Game() {
      loadSoftwareHistoryQuestions();
      loadProgrammingLanguagesQuestions();
      loadRefactoringQuestions();
      loadTestingQuestions();
   }
   
   private void loadSoftwareHistoryQuestions() {
      try {
         InputStream is = getClass().getClassLoader().getResourceAsStream("questions/software_history.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
               String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
               softwareHistoryQuestions.addLast(question);
               softwareHistoryAnswers.addLast(parts[5]);
            }
         }
         br.close();
      } catch (IOException e) {
         e.printStackTrace();
         // Fallback questions if file reading fails
         for (int i = 0; i < 50; i++) {
            softwareHistoryQuestions.addLast("Software History Question " + i + "\na) Option A\nb) Option B\nc) Option C\nd) Option D");
            softwareHistoryAnswers.addLast("a");
         }
      }
   }
   
   private void loadProgrammingLanguagesQuestions() {
      try {
         InputStream is = getClass().getClassLoader().getResourceAsStream("questions/programming_languages.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
               String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
               programmingLanguagesQuestions.addLast(question);
               programmingLanguagesAnswers.addLast(parts[5]);
            }
         }
         br.close();
      } catch (IOException e) {
         e.printStackTrace();
         // Fallback questions if file reading fails
         for (int i = 0; i < 50; i++) {
            programmingLanguagesQuestions.addLast("Programming Languages Question " + i + "\na) Option A\nb) Option B\nc) Option C\nd) Option D");
            programmingLanguagesAnswers.addLast("a");
         }
      }
   }
   
   private void loadRefactoringQuestions() {
      try {
         InputStream is = getClass().getClassLoader().getResourceAsStream("questions/refactoring.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
               String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
               refactoringQuestions.addLast(question);
               refactoringAnswers.addLast(parts[5]);
            }
         }
         br.close();
      } catch (IOException e) {
         e.printStackTrace();
         // Fallback questions if file reading fails
         for (int i = 0; i < 50; i++) {
            refactoringQuestions.addLast("Refactoring Question " + i + "\na) Option A\nb) Option B\nc) Option C\nd) Option D");
            refactoringAnswers.addLast("a");
         }
      }
   }
   
   private void loadTestingQuestions() {
      try {
         InputStream is = getClass().getClassLoader().getResourceAsStream("questions/testing.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
               String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
               testingQuestions.addLast(question);
               testingAnswers.addLast(parts[5]);
            }
         }
         br.close();
      } catch (IOException e) {
         e.printStackTrace();
         // Fallback questions if file reading fails
         for (int i = 0; i < 50; i++) {
            testingQuestions.addLast("Testing Question " + i + "\na) Option A\nb) Option B\nc) Option C\nd) Option D");
            testingAnswers.addLast("a");
         }
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }
   
   public boolean checkAnswer(String playerAnswer) {
      return currentCorrectAnswer.equalsIgnoreCase(playerAnswer.trim());
   }
   
   public String getCorrectAnswer() {
      return currentCorrectAnswer;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[howManyPlayers()] = 1;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {
      if (currentCategory() == "Software History") {
         System.out.println(softwareHistoryQuestions.removeFirst());
         currentCorrectAnswer = (String) softwareHistoryAnswers.removeFirst();
      }
      if (currentCategory() == "Programming Languages") {
         System.out.println(programmingLanguagesQuestions.removeFirst());
         currentCorrectAnswer = (String) programmingLanguagesAnswers.removeFirst();
      }
      if (currentCategory() == "Refactoring") {
         System.out.println(refactoringQuestions.removeFirst());
         currentCorrectAnswer = (String) refactoringAnswers.removeFirst();
      }
      if (currentCategory() == "Testing") {
         System.out.println(testingQuestions.removeFirst());
         currentCorrectAnswer = (String) testingAnswers.removeFirst();
      }
   }


   private String currentCategory() {
      if (places[currentPlayer] - 1 == 0) return "Software History";
      if (places[currentPlayer] - 1 == 4) return "Software History";
      if (places[currentPlayer] - 1 == 8) return "Software History";
      if (places[currentPlayer] - 1 == 1) return "Programming Languages";
      if (places[currentPlayer] - 1 == 5) return "Programming Languages";
      if (places[currentPlayer] - 1 == 9) return "Programming Languages";
      if (places[currentPlayer] - 1 == 2) return "Refactoring";
      if (places[currentPlayer] - 1 == 6) return "Refactoring";
      if (places[currentPlayer] - 1 == 10) return "Refactoring";
      return "Testing";
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
