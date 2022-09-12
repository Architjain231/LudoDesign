package com.game.model;

import java.util.Arrays;

public class GameBoard {

    String board[][];
    GameBoard()
    {
        board=new String[15][15];
        init();
    }

    public String[][] getBoard() {
        return board;
    }

    private void init()
    {
        for(int i=0;i<15;i++)
        {
            Arrays.fill(board[i],"   ");
        }
        for(int i=6;i<=8;i++)
        {
            board[0][i]=" * ";
            board[14][i]=" * ";
            board[i][0]=" * ";
            board[i][14]=" * ";
        }
        for(int i=0;i<6;i++)
        {
            board[i][6]=" * ";
            board[i][8]=" * ";
            board[14-i][6]=" * ";
            board[14-i][8]=" * ";
            board[6][i]=" * ";
            board[8][i]=" * ";
            board[6][14-i]=" * ";
            board[8][14-i]=" * ";
        }
        for(int i=1;i<=5;i++)
        {
            board[i][7]=" + ";
            board[14-i][7]=" + ";
            board[7][i]=" + ";
            board[7][14-i]=" + ";
        }
    }
}
