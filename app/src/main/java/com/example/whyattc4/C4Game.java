package com.example.whyattc4;

public class C4Game {
    public static final int COLUMN = 7;
    public static final int ROW = 6;
    private int turn;
    private int [][] game;

    public C4Game(){
        game = new int[ROW][COLUMN];
        ResetGame();
    }

    public int play( int row, int col ) {
        int currentTurn = turn;
        if( row >= 0 && col >= 0 && row < ROW && col < COLUMN
                && game[row][col] == 0 ) {
            game[row][col] = turn;

            if( turn == 1 )
                turn = 2;
            else
                turn = 1;
            return currentTurn;
        }
        else
            return 0;
    }

    public void ResetGame( ) {
        for (int row = 0; row < ROW; row++)
            for( int col = 0; col < COLUMN; col++ )
                game[row][col] = 0;
        turn = 1;
    }

    public int whoWon( ) {
        int rows = checkRows( );
        if ( rows > 0 )
            return rows;
        int columns = checkColumns( );
        if( columns > 0 )
            return columns;
        int diagonals = checkDiagonals( );
        if( diagonals > 0 )
            return diagonals;
        return 0;
    }

    protected int checkRows( ) {
        int r = 5;
        int c = 0;

        while(r > -1){
            while(c < 4){
                if(game[r][c] != 0){
                    if (game[r][c] == game[r][c + 1] && game[r][c] == game[r][c + 2] && game[r][c] == game[r][c+3]) {
                        return game[r][c];
                    }
                }
                c++;
            }
            r--;
            c = 0;
        }
        return 0;
    }

    protected int checkColumns( ) {
        int r = 5;
        int c = 0;

        while(c < COLUMN){
            while(r > 2){
                if(game[r][c] != 0){
                    if (game[r][c] == game[r-1][c] && game[r][c] == game[r - 2][c] && game[r][c] == game[r - 3][c]) {
                        return game[r][c];
                    }
                }
                r--;
            }
            c++;
            r = 5;
        }
        return 0;
    }

    protected int checkDiagonals( ) {
        int r = 5;
        int c = 0;
        int inC = 0;

        while (c < COLUMN){
            inC = c;
            if (inC == 0 || inC == 1 || inC == 2) {
                while(r > 2){
                    if(game[r][inC] != 0){
                        if (game[r][inC] == game[r - 1][inC + 1] && game[r][inC] == game[r - 2][inC + 2] && game[r][inC] == game[r - 3][inC + 3]) {
                            return game[r][inC];
                        }
                    }
                    r--;
                }
            }

            if (inC == 4|| inC == 5 || inC == 6) {
                while(r > 2){
                    if(game[r][inC] != 0){
                        if (game[r][inC] == game[r - 1][inC - 1] && game[r][inC] == game[r - 2][inC - 2] && game[r][inC] == game[r - 3][inC - 3]) {
                            return game[r][inC];
                        }
                    }
                    r--;
                }
            }

            if (inC == 3) {

                while(r > 2){
                    if(game[r][inC] != 0){
                        if (game[r][inC] == game[r - 1][inC - 1] && game[r][inC] == game[r - 2][inC - 2] && game[r][inC] == game[r - 3][inC - 3]) {
                            return game[r][inC];
                        }
                    }
                    r--;
                }

                r = 5;

                while(r > 2){
                    if(game[r][inC] != 0){
                        if (game[r][inC] == game[r - 1][inC + 1] && game[r][inC] == game[r - 2][inC + 2] && game[r][inC] == game[r - 3][inC + 3]) {
                            return game[r][inC];
                        }
                    }
                    r--;
                }

            }
            c++;
            r = 5;
        }

        return 0;
    }

    public boolean canNotPlay( ) {
        boolean result = true;
        for (int row = 0; row < ROW; row++)
            for( int col = 0; col < COLUMN; col++ )
                if ( game[row][col] == 0 )
                    result = false;
        return result;
    }

    public String result()
    {
        String rv = "";

        if(whoWon() > 0)
            rv = "Player " + whoWon() + " won";
        else if(canNotPlay())
            rv = "Tie Game";
        else
            rv = "PLAY !!";

        return rv;
    }

    public boolean isGameOver( ) {
        return canNotPlay( ) || ( whoWon( ) > 0 );
    }

}
