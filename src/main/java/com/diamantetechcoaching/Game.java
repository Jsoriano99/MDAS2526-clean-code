package com.diamantetechcoaching;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class Game {
   // Clean Code Naming: Meaningful constants instead of Magic Numbers
   private static final int MAX_PLAYERS = 6;
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS_COUNT = 6;

   ArrayList players = new ArrayList();
   // Clean Code Naming: Intention-Revealing Names (domain concepts)
   int[] boardPositions = new int[MAX_PLAYERS];
   int[] coins = new int[MAX_PLAYERS];
   boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

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
InputStream inputStream = getClass().getClassLoader().getResourceAsStream("questions/software_history.txt");
          // Clean Code Naming: Nombres pronunciables en vez de iniciales
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          String line;
          ArrayList unshuffledQuestions = new ArrayList();
          ArrayList unshuffledAnswers = new ArrayList();
         while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
if (parts.length >= 6) {
                String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
                unshuffledQuestions.add(question);
                unshuffledAnswers.add(parts[5]);
             }
          }
          reader.close();
          // Create indices and shuffle them to maintain question-answer pairing
          ArrayList shuffledIndices = new ArrayList();
          for (int i = 0; i < unshuffledQuestions.size(); i++) {
             shuffledIndices.add(i);
          }
          Collections.shuffle(shuffledIndices);
          // Add questions and answers in shuffled order
          for (int i = 0; i < shuffledIndices.size(); i++) {
             int index = (Integer) shuffledIndices.get(i);
             softwareHistoryQuestions.addLast(unshuffledQuestions.get(index));
             softwareHistoryAnswers.addLast(unshuffledAnswers.get(index));
         }
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
          InputStream inputStream = getClass().getClassLoader().getResourceAsStream("questions/programming_languages.txt");
          // Clean Code Naming: Nombres pronunciables en vez de iniciales
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          String line;
          ArrayList unshuffledQuestions = new ArrayList();
          ArrayList unshuffledAnswers = new ArrayList();
          while ((line = reader.readLine()) != null) {
             String[] parts = line.split("\\|");
             if (parts.length >= 6) {
                String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
                unshuffledQuestions.add(question);
                unshuffledAnswers.add(parts[5]);
             }
          }
          reader.close();
          // Create indices and shuffle them to maintain question-answer pairing
          ArrayList shuffledIndices = new ArrayList();
          for (int i = 0; i < unshuffledQuestions.size(); i++) {
             shuffledIndices.add(i);
          }
          Collections.shuffle(shuffledIndices);
          // Add questions and answers in shuffled order
          for (int i = 0; i < shuffledIndices.size(); i++) {
             int index = (Integer) shuffledIndices.get(i);
             programmingLanguagesQuestions.addLast(unshuffledQuestions.get(index));
             programmingLanguagesAnswers.addLast(unshuffledAnswers.get(index));
          }
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
          InputStream inputStream = getClass().getClassLoader().getResourceAsStream("questions/refactoring.txt");
          // Clean Code Naming: Nombres pronunciables en vez de iniciales
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          String line;
          ArrayList unshuffledQuestions = new ArrayList();
          ArrayList unshuffledAnswers = new ArrayList();
          while ((line = reader.readLine()) != null) {
             String[] parts = line.split("\\|");
             if (parts.length >= 6) {
                String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
                unshuffledQuestions.add(question);
                unshuffledAnswers.add(parts[5]);
             }
          }
          reader.close();
          // Create indices and shuffle them to maintain question-answer pairing
          ArrayList shuffledIndices = new ArrayList();
          for (int i = 0; i < unshuffledQuestions.size(); i++) {
             shuffledIndices.add(i);
          }
          Collections.shuffle(shuffledIndices);
          // Add questions and answers in shuffled order
          for (int i = 0; i < shuffledIndices.size(); i++) {
             int index = (Integer) shuffledIndices.get(i);
             refactoringQuestions.addLast(unshuffledQuestions.get(index));
             refactoringAnswers.addLast(unshuffledAnswers.get(index));
          }
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
          InputStream inputStream = getClass().getClassLoader().getResourceAsStream("questions/testing.txt");
          // Clean Code Naming: Nombres pronunciables en vez de iniciales
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          String line;
          ArrayList unshuffledQuestions = new ArrayList();
          ArrayList unshuffledAnswers = new ArrayList();
          while ((line = reader.readLine()) != null) {
             String[] parts = line.split("\\|");
             if (parts.length >= 6) {
                String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
                unshuffledQuestions.add(question);
                unshuffledAnswers.add(parts[5]);
             }
          }
          reader.close();
          // Create indices and shuffle them to maintain question-answer pairing
          ArrayList shuffledIndices = new ArrayList();
          for (int i = 0; i < unshuffledQuestions.size(); i++) {
             shuffledIndices.add(i);
          }
          Collections.shuffle(shuffledIndices);
          // Add questions and answers in shuffled order
          for (int i = 0; i < shuffledIndices.size(); i++) {
             int index = (Integer) shuffledIndices.get(i);
             testingQuestions.addLast(unshuffledQuestions.get(index));
             testingAnswers.addLast(unshuffledAnswers.get(index));
          }
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
      boardPositions[howManyPlayers()] = 1;
      coins[howManyPlayers()] = 0;
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
            boardPositions[currentPlayer] = boardPositions[currentPlayer] + roll;
            if (boardPositions[currentPlayer] > BOARD_SIZE) boardPositions[currentPlayer] = boardPositions[currentPlayer] - BOARD_SIZE;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + boardPositions[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         boardPositions[currentPlayer] = boardPositions[currentPlayer] + roll;
         if (boardPositions[currentPlayer] > BOARD_SIZE) boardPositions[currentPlayer] = boardPositions[currentPlayer] - BOARD_SIZE;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + boardPositions[currentPlayer]);
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
      if (boardPositions[currentPlayer] - 1 == 0) return "Software History";
      if (boardPositions[currentPlayer] - 1 == 4) return "Software History";
      if (boardPositions[currentPlayer] - 1 == 8) return "Software History";
      if (boardPositions[currentPlayer] - 1 == 1) return "Programming Languages";
      if (boardPositions[currentPlayer] - 1 == 5) return "Programming Languages";
      if (boardPositions[currentPlayer] - 1 == 9) return "Programming Languages";
      if (boardPositions[currentPlayer] - 1 == 2) return "Refactoring";
      if (boardPositions[currentPlayer] - 1 == 6) return "Refactoring";
      if (boardPositions[currentPlayer] - 1 == 10) return "Refactoring";
      return "Testing";
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            coins[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + coins[currentPlayer]
                               + " Gold Coins.");

            boolean winner = isGameInProgress();
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
         coins[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + coins[currentPlayer]
                            + " Gold Coins.");

         boolean winner = isGameInProgress();
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


   // Clean Code Naming: El nombre anterior era engañoso (Disinformation)
   private boolean isGameInProgress() {
      return !(coins[currentPlayer] == WINNING_COINS_COUNT);
   }
}
