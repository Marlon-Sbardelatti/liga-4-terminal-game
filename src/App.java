import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        new App();
    }

    private App() {
        Scanner kb = new Scanner(System.in);

        char[][] board = new char[6][7];
        initializeBoard(board);
        print(board);
        start(kb, board);
        kb.close();
    }

    private void initializeBoard(char[][] board) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 'B';
            }
        }
    }

    private void print(char[][] board) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(' ');
        }
    }

    private int check(char[][] board, char color, int total) {
        int gameOver = 0;
        // horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == color) {
                    if (j < 4) {
                        if (board[i][j + 1] == color && board[i][j + 2] == color && board[i][j + 3] == color) {
                            gameOver = -1;
                            return gameOver;
                        }
                    }
                }
            }
        }

        // vertical
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == color) {
                    if (i < 3) {
                        if (board[i + 1][j] == color && board[i + 2][j] == color && board[i + 3][j] == color) {
                            gameOver = -1;
                            return gameOver;
                        }

                    }
                }
            }
        }

        // diagonal direita
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == color) {
                    if (i < 3 && j < 4) {
                        if (board[i + 1][j + 1] == color && board[i + 2][j + 2] == color
                                && board[i + 3][j + 3] == color) {
                            gameOver = -1;
                            return gameOver;
                        }
                    }
                }
            }
        }
        // diagonal esquerda
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == color) {
                    if (i < 3 && j > 2) {
                        if (board[i + 1][j - 1] == color && board[i + 2][j - 2] == color 
                                && board[i + 3][j - 3] == color) {
                            gameOver = -1;
                            return gameOver;
                        }
                    }
                }
            }
        }

        // Empate

        if (total == 0 && gameOver != -1) {
            return gameOver = -2;
        }
        return gameOver;
    }

    private void start(Scanner kb, char[][] board) {
        System.out.println("Escolha uma cor: V(vermelho) || A(azul)");
        char playerColor = kb.next().charAt(0);
        playerColor = Character.toUpperCase(playerColor);
        while (playerColor != 'V' && playerColor != 'A') {
            System.out.println("Cor inválida");
            System.out.println("Escolha uma cor: V(vermelho) || A(azul)");
            playerColor = kb.next().charAt(0);
            playerColor = Character.toUpperCase(playerColor);
        }
        int total = 42;
        System.out.println("Informe a coluna que deseja jogar: ");
        int playerColumn = kb.nextInt();
        playerColumn = playerColumn - 1;
        while (playerColumn < 0 || playerColumn > 6) {
            System.out.println("Coluna inválida");
            System.out.println("Informe a coluna que deseja jogar: ");
            playerColumn = kb.nextInt();
            playerColumn = playerColumn - 1;
        }
        while (total > 0) {
            boolean stopGame = false;
            char botColor = ' ';
            if (playerColor == 'V') {
                botColor = 'A';
            } else {
                botColor = 'V';
            }
            //jogada player
            stopGame = player(board, playerColor, playerColumn, kb, total, botColor, stopGame);
            total--;
            if (stopGame) {
                break;
            }
            //sorteio coluna do bot
            int botColumn = (int) (Math.random() * 7);
            while (board[0][botColumn] == playerColor || board[0][botColumn] == botColor) {
                botColumn = (int) (Math.random() * 7);
            }
            //jogada bot
            stopGame = bot(board, playerColor, botColumn, kb, total, botColor, stopGame);
            total--;
            if (stopGame) {
                break;
            }
            print(board);

            System.out.println("Informe a coluna que deseja jogar: ");
            playerColumn = kb.nextInt();
            playerColumn = playerColumn - 1;
            while (playerColumn < 0 || playerColumn > 6) {
                System.out.println("Coluna inválida");
                System.out.println("Informe a coluna que deseja jogar: ");
                playerColumn = kb.nextInt();
                playerColumn = playerColumn - 1;
            }

        }
    }

    private boolean player(char[][] board, char playerColor, int playerColumn, Scanner kb, int total, char botColor,
            boolean stopGame) {

        for (int i = 5; i >= 0; i--) {
            while (board[0][playerColumn] == playerColor || board[0][playerColumn] == botColor) {
                System.out.println("Coluna cheia!");
                System.out.println("Informe a coluna que deseja jogar: ");
                playerColumn = kb.nextInt();
                playerColumn = playerColumn - 1;
            }
            if (board[i][playerColumn] == 'B') {
                board[i][playerColumn] = playerColor;
                int gameOver = check(board, playerColor, total);
                // check empate
                if (gameOver == -2) {
                    print(board);
                    System.out.println("Empate! \n");
                    System.out.println("Gostaria de jogar de novo? sim || não: ");
                    String res = kb.next();
                    res = res.toLowerCase();
                    if (res.equals("não")) {
                        System.out.println("Obrigado por jogar!");
                        stopGame = true;
                    } else {
                        initializeBoard(board);
                        start(kb, board);
                        stopGame = true;
                        break;
                    }
                }
                // check vitoria
                if (gameOver == -1) {
                    print(board);
                    System.out.println("Você venceu! \n");
                    System.out.println("Gostaria de jogar de novo? sim || não: ");
                    String res = kb.next();
                    res = res.toLowerCase();
                    if (res.equals("não")) {
                        System.out.println("Obrigado por jogar!");
                        stopGame = true;
                    } else {
                        initializeBoard(board);
                        start(kb, board);
                        stopGame = true;
                        break;
                    }

                }
                break;
            }
        }
        return stopGame;
    }

    private boolean bot(char[][] board, char playerColor, int botColumn, Scanner kb, int total, char botColor,
            boolean stopGame) {

        for (int i = 5; i >= 0; i--) {
            if (board[i][botColumn] == 'B') {
                board[i][botColumn] = botColor;
                int gameOver = check(board, botColor, total);
                // check empate
                if (gameOver == -2) {
                    print(board);
                    System.out.println("Empate! \n");
                    System.out.println("Gostaria de jogar de novo? sim || não: ");
                    String res = kb.next();
                    res = res.toLowerCase();
                    if (res.equals("não")) {
                        System.out.println("Obrigado por jogar!");
                        stopGame = true;
                    } else {
                        initializeBoard(board);
                        start(kb, board);
                        stopGame = true;
                        break;
                    }
                }
                // check derrota
                if (gameOver == -1) {
                    print(board);
                    System.out.println("Você perdeu :( \n");
                    System.out.println(" ");
                    System.out.println("Gostaria de jogar de novo? sim || não: ");
                    String res = kb.next();
                    res = res.toLowerCase();
                    if (res.equals("não")) {
                        System.out.println("Obrigado por jogar!");
                        stopGame = true;
                    } else {
                        initializeBoard(board);
                        start(kb, board);
                        stopGame = true;
                        break;
                    }
                }
                break;
            }
        }
        return stopGame;
    }
}
