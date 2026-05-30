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

    ArrayList<String> players = new ArrayList<>();
    int[] boardPositions = new int[MAX_PLAYERS];
    int[] coins = new int[MAX_PLAYERS];
    boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

    LinkedList<String> softwareHistoryQuestions = new LinkedList<>();
    LinkedList<String> programmingLanguagesQuestions = new LinkedList<>();
    LinkedList<String> refactoringQuestions = new LinkedList<>();
    LinkedList<String> testingQuestions = new LinkedList<>();
    LinkedList<String> softwareHistoryAnswers = new LinkedList<>();
    LinkedList<String> programmingLanguagesAnswers = new LinkedList<>();
    LinkedList<String> refactoringAnswers = new LinkedList<>();
    LinkedList<String> testingAnswers = new LinkedList<>();

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
    private void loadQuestions(String resourcePath, String categoryName, LinkedList<String> questions, LinkedList<String> answers) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             InputStreamReader isr = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            ArrayList<String> unshuffledQuestions = new ArrayList<>();
            ArrayList<String> unshuffledAnswers = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                // Clean Code C4: Explaining Variable — reveals intent of the validation
                boolean isValidQuestion = parts.length >= EXPECTED_FIELDS_PER_QUESTION;
                if (isValidQuestion) {
                    unshuffledQuestions.add(formatQuestion(parts));
                    unshuffledAnswers.add(parts[5]);
                }
            }
            // Clean Code C4: Extract Method — create once, reuse for questions AND answers to preserve pairing
            ArrayList<Integer> shuffledIndices = createShuffledIndices(unshuffledQuestions.size());
            shuffleInto(unshuffledQuestions, questions, shuffledIndices);
            shuffleInto(unshuffledAnswers, answers, shuffledIndices);
        } catch (IOException e) {
            e.printStackTrace();
            for (int i = 0; i < FALLBACK_QUESTION_COUNT; i++) {
                questions.addLast(categoryName + " Question " + i + "\na) Option A\nb) Option B\nc) Option C\nd) Option D");
                answers.addLast("a");
            }
        }
    }

    // Clean Code C4: Extract Method — builds formatted question string from pipe-separated fields
    // Do One Thing: concatenates parts[0-4] into a multi-line question string
    private String formatQuestion(String[] parts) {
        return parts[0] + "\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
    }

    // Clean Code C4: Extract Method — builds and shuffles a list of indices for random access
    // Do One Thing: creates [0, 1, ..., size-1], shuffles it, returns it
    private ArrayList<Integer> createShuffledIndices(int size) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        return indices;
    }

    // Clean Code C4: Extract Method — copies source elements into target using pre-shuffled indices
    // Do One Thing: iterates over indices, adding source.get(index) to target
    // Note: call with SAME indices for paired lists (questions + answers) to preserve pairing
    private void shuffleInto(ArrayList<String> source, LinkedList<String> target, ArrayList<Integer> indices) {
        for (int i = 0; i < indices.size(); i++) {
            int index = indices.get(i);
            target.addLast(source.get(index));
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

      display(playerName + " was added");
      display("They are player number " + players.size());
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

             display(players.get(currentPlayer) + " is getting out of the penalty box");
               movePlayerForward(roll);

               announceLocationAndAskQuestion();
          } else {
             display(players.get(currentPlayer) + " is not getting out of the penalty box");
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
      display(players.get(currentPlayer) + " is the current player");
      display("They have rolled a " + roll);
   }

   // Clean Code C2: Extract Method, DRY — duplicated 4-line block in roll() extracted into one method
   // Do One Thing: announces new location, category, and triggers askQuestion()
   private void announceLocationAndAskQuestion() {
      display(players.get(currentPlayer)
            + "'s new location is "
            + boardPositions[currentPlayer]);
      display("The category is " + currentCategory());
      askQuestion();
   }

    // Clean Code C4: Do One Thing, DRY — 4 independent if-statements replaced with index-based dispatch
    // Instead of evaluating currentCategory() 4 times per call, compute index once and use parallel arrays
    private void askQuestion() {
        int categoryIndex = currentCategoryIndex();
        LinkedList<String>[] allQuestions = new LinkedList[]{
            softwareHistoryQuestions, programmingLanguagesQuestions,
            refactoringQuestions, testingQuestions
        };
        LinkedList<String>[] allAnswers = new LinkedList[]{
            softwareHistoryAnswers, programmingLanguagesAnswers,
            refactoringAnswers, testingAnswers
        };

        display(allQuestions[categoryIndex].removeFirst());
        currentCorrectAnswer = allAnswers[categoryIndex].removeFirst();
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

    // Clean Code C5: Extract Method — desacopla la lógica del juego de la salida por consola
    private void display(String message) {
        System.out.println(message);
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
       display("Answer was correct!!!!");
       coins[currentPlayer]++;
       display(players.get(currentPlayer)
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
       display("Question was incorrectly answered");
       display(players.get(currentPlayer) + " was sent to the penalty box");
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
