package aiAssignment2;

public class Solver {
    private int bestMoveY;
    private int bestMoveX;
    private int bestScore;
    private Board tempBoard;
    private char aiPlayer;
    private int size;
    private static int nodesExpanded;
    private static int maxDepth;
    private String searchMethod = "";

    public Solver(Board board, char aiPlayer, String searchMethod) { //receives in board and char of aiPlayer
        tempBoard = new Board(board, 0);
        size = tempBoard.getSize();
        this.aiPlayer = aiPlayer; //with this, ai will always be maximizer.
        this.searchMethod = searchMethod;

        if (tempBoard.checkAvailableMoves() == true) {
            bestMove();
        }
    }

    public int getMaxDepth() {
        return maxDepth;
    }
    public int getNodesExpanded() {
        return nodesExpanded;
    }
    //goes through each available spot and calls minimax recursive method.
    public void bestMove() {
        int bestScore = -1000;

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                if (tempBoard.isSpotEmpty(i,j)) {
                    tempBoard = new Board(tempBoard, i,j);
                    int score = 0;
                    if (searchMethod.equals("MM")) {
                        score = miniMax(tempBoard, false);
                    } else if (searchMethod.equals("AB")){
                        score = miniMax(tempBoard, false, -1000, 1000);
                    }
                    tempBoard = new Board(tempBoard.getParent());
                    if (score > bestScore) {
                        bestScore = score;
                        bestMoveY = i;
                        bestMoveX = j;
                    }
                }
            }
        }
    }

    public int miniMax(Board current, boolean isMaximizing) { //depth is unlimited, and recorded through board class.
        nodesExpanded++;
        if (current.checkAvailableMoves() == false) {
            if (current.getPlayer() == aiPlayer) {
                return -1;

            } else if (current.getPlayer() != aiPlayer) {
                return 1;
            }

            int tempBestScore;
            if (isMaximizing) {
                tempBestScore = -1000;
                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 6; j++) {
                        if (current.isSpotEmpty(i,j)) {
                            current = new Board(current, i, j);
                            maxDepth = Math.max(current.getDepth(), maxDepth);
                            int score = miniMax(current, false);
                            if (score > tempBestScore) {
                                tempBestScore = score;
                            }
                            current = new Board(current.getParent());
                        }
                    }
                }
            } else {
                tempBestScore = 1000;
                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 6; j++) {
                        if (current.isSpotEmpty(i,j)) {
                            current = new Board(current, i, j);
                            maxDepth = Math.max(current.getDepth(), maxDepth);
                            int score = miniMax(current, true);
                            current = new Board(current.getParent());
                            tempBestScore = Math.min(score, tempBestScore);
                        }
                    }
                }
            }
            return tempBestScore;
        }
        return 0;
    }

    public int miniMax(Board current, boolean isMaximizing, int alpha, int beta) {//minimax with alpha beta pruning
        nodesExpanded++;
        if (current.checkAvailableMoves() == false) {
            if (current.getPlayer() == aiPlayer) {
                return -1;

            } else if (current.getPlayer() != aiPlayer) {
                return 1;
            }

            int tempBestScore;
            if (isMaximizing) {
                tempBestScore = -1000;
                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 6; j++) {
                        if (current.isSpotEmpty(i, j)) {
                            current = new Board(current, i, j);
                            maxDepth = Math.max(current.getDepth(), maxDepth);
                            int score = miniMax(current, false, alpha, beta);
                            current = new Board(current.getParent());
                            tempBestScore = Math.max(score, tempBestScore);
                            alpha = Math.max(alpha, tempBestScore);
                            if (beta <= alpha)
                                break;
                        }
                    }
                }
            } else {
                tempBestScore = 1000;
                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 6; j++) {
                        if (current.isSpotEmpty(i, j)) {
                            current = new Board(current, i, j);
                            maxDepth = Math.max(current.getDepth(), maxDepth);
                            int score = miniMax(current, true, alpha, beta);
                            current = new Board(current.getParent());
                            tempBestScore = Math.min(score, tempBestScore);
                            beta = Math.min(beta, tempBestScore);
                            if (beta <= alpha)
                                break;
                        }
                    }
                }
            }
            return tempBestScore;
        }
        return 0;
    }

    public int getBestMoveY() {
        return bestMoveY;
    }

    public int getBestMoveX() {
        return bestMoveX;
    }
}
