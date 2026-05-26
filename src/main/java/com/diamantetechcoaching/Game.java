package com.diamantetechcoaching;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class Game {
   private static final int MAX_PLAYERS = 6;
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS_COUNT = 6;
   // Clean Code C5: Magic numbers extracted into named constants
   private static final int EXPECTED_FIELDS_PER_QUESTION = 6;
   private static final int FALLBACK_QUESTION_COUNT = 50;

    // Clean Code C4: CATEGORIES array enables O(1) modulo-based dispatch instead of if-else chains
    private static final String[] CATEGORIES = {
        "Software History", "Programming Languages", "Refactoring", "Testing"
    };

    ArrayList players = new ArrayList();
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
       // Clean Code C2: DRY — 4 duplicated loaders collapsed into one loadQuestions() method
       loadQuestions("questions/software_history.txt", "Software History", softwareHistoryQuestions, softwareHistoryAnswers);
       loadQuestions("questions/programming_languages.txt", "Programming Languages", programmingLanguagesQuestions, programmingLanguagesAnswers);
       loadQuestions("questions/refactoring.txt", "Refactoring", refactoringQuestions, refactoringAnswers);
       loadQuestions("questions/testing.txt", "Testing", testingQuestions, testingAnswers);
    }

    // Clean Code C2: DRY, Small Functions — 4 identical loaders collapsed into one parameterized method
    // Clean Code C1: try-with-resources ensures BufferedReader is auto-closed (no manual reader.close())
    private void loadQuestions(String resourcePath, String categoryName, LinkedList questions, LinkedList answers) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             InputStreamReader isr = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            ArrayList unshuffledQuestions = new ArrayList();
            ArrayList unshuffledAnswers = new ArrayList();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= EXPECTED_FIELDS_PER_QUESTION) {
                    String question = parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
                    unshuffledQuestions.add(question);
                    unshuffledAnswers.add(parts[5]);
                }
            }
            ArrayList shuffledIndices = new ArrayList();
            for (int i = 0; i < unshuffledQuestions.size(); i++) {
                shuffledIndices.add(i);
            }
            Collections.shuffle(shuffledIndices);
            for (int i = 0; i < shuffledIndices.size(); i++) {
                int index = (Integer) shuffledIndices.get(i);
                questions.addLast(unshuffledQuestions.get(index));
                answers.addLast(unshuffledAnswers.get(index));
            }
        } catch (IOException e) {
            e.printStackTrace();
            for (int i = 0; i < FALLBACK_QUESTION_COUNT; i++) {
                questions.addLast(categoryName + " Question " + i + "\na) Option A\nb) Option B\nc) Option C\nd) Option D");
                answers.addLast("a");
            }
        }
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
      announceTurn(roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

             System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
               movePlayerForward(roll);

               announceLocationAndAskQuestion();
          } else {
             System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
             isGettingOutOfPenaltyBox = false;
          }

       } else {

          movePlayerForward(roll);

          announceLocationAndAskQuestion();
      }

   }

   // Clean Code C2: Extract Method — two lines from roll() extracted into announceTurn()
   // Do One Thing: announces whose turn it is and what they rolled
   private void announceTurn(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);
   }

   // Clean Code C2: Extract Method, DRY — duplicated 4-line block in roll() extracted into one method
   // Do One Thing: announces new location, category, and triggers askQuestion()
   private void announceLocationAndAskQuestion() {
      System.out.println(players.get(currentPlayer)
           + "'s new location is "
           + boardPositions[currentPlayer]);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

    // Clean Code C4: Do One Thing, DRY — 4 independent if-statements replaced with index-based dispatch
    // Instead of evaluating currentCategory() 4 times per call, compute index once and use parallel arrays
    private void askQuestion() {
        int categoryIndex = currentCategoryIndex();
        LinkedList[] allQuestions = {
            softwareHistoryQuestions, programmingLanguagesQuestions,
            refactoringQuestions, testingQuestions
        };
        LinkedList[] allAnswers = {
            softwareHistoryAnswers, programmingLanguagesAnswers,
            refactoringAnswers, testingAnswers
        };

        System.out.println(allQuestions[categoryIndex].removeFirst());
        currentCorrectAnswer = (String) allAnswers[categoryIndex].removeFirst();
    }

    // Clean Code: Extract Method, DRY — duplicated (boardPositions[currentPlayer]-1) % CATEGORIES.length
    // extracted into currentCategoryIndex(), eliminating the expression from askQuestion() and currentCategory()
    private int currentCategoryIndex() {
        return (boardPositions[currentPlayer] - 1) % CATEGORIES.length;
    }

    // Clean Code C4: Do One Thing, Small Functions — 9 if-statements collapsed into O(1) modular arithmetic
    // Pattern: (boardPosition - 1) % 4 → 0=History, 1=Languages, 2=Refactoring, 3=Testing
    private String currentCategory() {
        return CATEGORIES[currentCategoryIndex()];
    }

    // Clean Code C5: CQS — public thin wrapper delegates to private command (awardCoin) and query (hasCurrentPlayerWon)
    // Clean Code C3: Extract Method + Rename + Consolidate Conditional — 4-line duplicated block extracted
    // into processCorrectAnswer(); variable renamed from misleading 'winner' to revealing 'gameContinues'
   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            return processCorrectAnswer();
           } else {
              advanceToNextPlayer();
              return true;
          }
       } else {
          return processCorrectAnswer();
       }
    }

    // Clean Code C3: Extract Method — award + check + advance extracted from handleCorrectAnswer()
    // Do One Thing: awards coin, checks if game continues, advances turn
    private boolean processCorrectAnswer() {
        awardCoin();
        boolean gameContinues = !hasCurrentPlayerWon();
        advanceToNextPlayer();
        return gameContinues;
    }

    // Clean Code C5: CQS Command — mutates state (coins) and prints; no return value
    private void awardCoin() {
       System.out.println("Answer was correct!!!!");
       coins[currentPlayer]++;
       System.out.println(players.get(currentPlayer)
             + " now has "
             + coins[currentPlayer]
             + " Gold Coins.");
    }

    // Clean Code C5: CQS Query — pure check, no side effects; renamed from isGameInProgress with inverted logic
    private boolean hasCurrentPlayerWon() {
       return coins[currentPlayer] == WINNING_COINS_COUNT;
    }

    // Clean Code C5: CQS — thin wrapper delegates command (penalize) to private method
    public boolean wrongAnswer() {
       penalizeCurrentPlayer();
       advanceToNextPlayer();
       return true;
    }

    // Clean Code C5: CQS Command — mutates penalty state and prints; no return value
    private void penalizeCurrentPlayer() {
       System.out.println("Question was incorrectly answered");
       System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
       inPenaltyBox[currentPlayer] = true;
    }

   // Clean Code C3: DRY, Do One Thing — extracted duplicated player turn advance (4 occurrences → 1 call)
   private void advanceToNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) {
         currentPlayer = 0;
      }
   }

   // Clean Code C3: DRY, Do One Thing — extracted duplicated board position movement with wrapping
   private void movePlayerForward(int steps) {
      boardPositions[currentPlayer] = boardPositions[currentPlayer] + steps;
      if (boardPositions[currentPlayer] > BOARD_SIZE) {
         boardPositions[currentPlayer] = boardPositions[currentPlayer] - BOARD_SIZE;
      }
   }
}
