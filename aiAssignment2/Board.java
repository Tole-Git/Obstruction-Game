package aiAssignment2;

public class Board {
    private int size;
    private char[][] gameBoard;
    private String boardState;
    private char player;
    private int moveY, moveX;
    private int depth;
    private Board parent;
    private boolean moveSuccessful;


    public Board(int size) { //create initial board
        this.size = size;
        boardState = "";
        player = 'O';
        this.depth = 0;

        createInitialGameBoard();
        updateBoardState();
    }

    public Board(Board board) { //copy
        this.size = board.size;
        this.boardState = board.boardState;
        this.player = board.player;
        this.depth = board.depth;
        this.parent = board.parent;
        createGameBoard();
        updateBoardState();
    }

    public Board(Board board, int depth) { //copy with depth = 0
        this.size = board.size;
        this.boardState = board.boardState;
        this.player = board.player;
        this.depth = depth;
        this.parent = board.parent;
        createGameBoard();
        updateBoardState();
    }

    public Board(Board board, int moveY, int moveX) { //copy and move
        this.size = board.size;
        this.boardState = board.boardState;
        this.player = board.player;
        this.moveY = moveY - 1;
        this.moveX = moveX - 1;
        this.depth = board.depth;
        this.depth++;
        this.parent = board;
        createGameBoard();
        if(move() == false) {
            moveSuccessful = false;
        } else {
            moveSuccessful = true;
        }
        updateBoardState();
    }
    /*
       'O' = player 1
       'X' = player 2
       ' ' = empty space
       '/' = unavailable space
     */
    //creates initial gameBoard
    private void createInitialGameBoard() {
        gameBoard = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = ' ';

            }
        }
    }
    //creates gameBoard off boardState
    private void createGameBoard() {
        int boardStateCounter = 0;
        gameBoard = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = boardState.charAt(boardStateCounter);
                boardStateCounter++;
            }
        }
    }
    //
    private void updateBoardState() {
        boardState = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardState += gameBoard[i][j];
            }
        }
    }
    //moves for the player
    public boolean move() {
        if (player == 'O') {
            if (gameBoard[moveY][moveX] == ' ') {
                gameBoard[moveY][moveX] = player;
                createBarriers();
                player = 'X';
                return true;
            } else if (gameBoard[moveY][moveX] != ' ') {
                return false;
            }
        } else if (player == 'X') {
            if (gameBoard[moveY][moveX] == ' ') {
                gameBoard[moveY][moveX] = player;
                createBarriers();
                player = 'O';
                return true;
            } else if (gameBoard[moveY][moveX] != ' ') {
                return false;
            }
        }
        return false;
    }

    private void createBarriers() {
        if (moveY >= 1) {
            gameBoard[moveY-1][moveX] = '/';
        } if (moveY <= size-2) {
            gameBoard[moveY+1][moveX] = '/';
        } if (moveX >= 1) {
            gameBoard[moveY][moveX-1] = '/';
        } if (moveX <= size-2) {
            gameBoard[moveY][moveX+1] = '/';
        } if(moveY >= 1 && moveX >=1) {
            gameBoard[moveY-1][moveX-1] = '/';
        } if (moveY <= size-2 && moveX >=1) {
            gameBoard[moveY+1][moveX-1] = '/';
        } if (moveY >= 1 && moveX <= size-2) {
            gameBoard[moveY-1][moveX+1] = '/';
        } if (moveY <= size-2 && moveX <= size-2) {
            gameBoard[moveY+1][moveX+1] = '/';
        }
    }
    //print board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int j = 0; j < size; j++) {
            sb.append("  " + (j+1) + "  ");
        }
        sb.append("\n");
        for (int i = 0; i < size; i++) {
            sb.append("  --");
            for (int j = 0; j < size; j++) {
                sb.append("-----");
            }
            sb.append("\n");
            sb.append((i+1) + " || ");
            for (int k = 0; k < size; k++) {
                sb.append(gameBoard[i][k] + " || ");
            }
            sb.append("\n");
        }
        sb.append("  --");
        for (int j = 0; j < size; j++) {
            sb.append("-----");
        }
        return sb.toString();
    }

    public boolean checkAvailableMoves() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameBoard[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSpotEmpty(int y, int x) {
        if (gameBoard[y-1][x-1] == ' ') {
            return true;
        }
        return false;
    }

    public char getPlayer() {
        return player;
    }

    public int getDepth() { return depth; }

    public String getBoardState() { return boardState; }

    public Board getParent() { return parent; }

    public boolean getMoveSuccessful() {
        return moveSuccessful;
    }

    public int getSize() {
        return size;
    }
}
