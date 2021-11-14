package aiAssignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public class Tester {
    private static String searchMethod = "";
    private static int totalNodesExpanded = 0;
    private static int maxDepth = 0;

    public static void main(String args[]) {
        boolean availableMoves = true;
        boolean continueGame = true;
        char player;
        Scanner scanner = new Scanner(System.in);
        int input = 0;

        while (continueGame == true) {
            try {
                System.out.print("Choose your player (1, 2): ");
                input = scanner.nextInt();
                System.out.print("Choose your search method (AB, MM): ");
                searchMethod = scanner.next();

                if (!searchMethod.equals("AB") && !searchMethod.equals("MM")) {
                    System.out.println("please input AB or MM.");
                    throw new IllegalArgumentException();
                }

                if (input < 1 || input > 2) {
                    System.out.print("Wrong input. ");
                    throw new IllegalArgumentException();
                }

                Board board = new Board(6);
                player = board.getPlayer();

                if (input == 1) {
                    System.out.println("player 2: AI");
                    System.out.println(board.toString());

                    while (availableMoves) {
                        if (player == 'O') {
                            System.out.print("Player 1 move X: ");
                            int moveX = scanner.nextInt();
                            System.out.print("Player 1 move Y: ");
                            int moveY = scanner.nextInt();

                            Board newBoard = new Board(board, moveY, moveX);
                            System.out.println(newBoard.toString());

                            player = newBoard.getPlayer();
                            board = new Board(newBoard);
                            availableMoves = board.checkAvailableMoves();
                        } else if (player == 'X') { //ai
                            System.out.println("Player 2 moves");
                            Solver ai = new Solver(board, 'X', searchMethod);

                            int moveX = ai.getBestMoveX();
                            int moveY = ai.getBestMoveY();
                            totalNodesExpanded += ai.getNodesExpanded();

                            Board newBoard = new Board(board, moveY, moveX);
                            System.out.println(newBoard.toString());

                            player = newBoard.getPlayer();
                            board = new Board(newBoard);
                            availableMoves = board.checkAvailableMoves();
                        }
                    }
                } else if (input == 2) {
                    System.out.println("player 1: AI");
                    System.out.println(board.toString());

                    while (availableMoves) {
                        if (player == 'O') { //ai
                            System.out.println("Player 1 moves");
                            Solver ai = new Solver(board, 'O', searchMethod);

                            int moveX = ai.getBestMoveX();
                            int moveY = ai.getBestMoveY();
                            totalNodesExpanded += ai.getNodesExpanded();

                            Board newBoard = new Board(board, moveY, moveX);
                            System.out.println(newBoard.toString());

                            player = newBoard.getPlayer();
                            board = new Board(newBoard);
                            availableMoves = board.checkAvailableMoves();
                        } else if (player == 'X') {
                            System.out.print("Player 2 move X: ");
                            int moveX = scanner.nextInt();
                            System.out.print("Player 2 move Y: ");
                            int moveY = scanner.nextInt();

                            Board newBoard1 = new Board(board, moveY, moveX);
                            System.out.println(newBoard1.toString());

                            player = newBoard1.getPlayer();
                            board = new Board(newBoard1);
                            availableMoves = board.checkAvailableMoves();
                        }
                    }
                }
                if (board.getPlayer() == 'O') {
                    System.out.println("Player 2 Winner! Total Moves: " + board.getDepth());
                    continueGame = false;
                    printReadMe();
                } else if (board.getPlayer() == 'X') {
                    System.out.println("Player 1 Winner! Total Moves: " + board.getDepth());
                    continueGame = false;
                    printReadMe();
                }
            } catch (Exception e) {
                String continueInput = "";
                while (!continueInput.equals("Y") && !continueInput.equals("N")){
                    System.out.print("Continue? (Y, N): ");
                    continueInput = scanner.next().toUpperCase(Locale.ROOT);
                }
                if (continueInput.equals("N")) {
                    System.out.println("\nHave a great obstructive day");
                    continueGame = false;
                }

            }
        }
    }

    public static void printReadMe() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Evaluation Function:\nloss = -1, win = 1\nFor AB, if B <= A, break\n");
        if (searchMethod.equals("MM")) {
            sb.append("Minimax Algorithm\nNumber of nodes expanded: " + totalNodesExpanded + "\nDepth limit: none\nMax Depth: ");
        } else if (searchMethod.equals("AB")) {
            sb.append("Minimax Algorithm w/ Alpha Beta Pruning\nNumber of nodes expanded: " + totalNodesExpanded + "\nDepth limit: none");
        }
        Path fileName = Path.of("src/aiAssignment2/Readme.txt");
        Files.writeString(fileName, sb.toString());
    }
}
