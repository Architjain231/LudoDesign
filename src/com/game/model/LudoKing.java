package com.game.model;

import com.game.controller.GameController;

public class LudoKing {

    private int playerCount;
    private GameBoard board;
    GameController controller;
    public LudoKing(int playerCount)
    {
        this.playerCount=playerCount;
        board=new GameBoard();
        controller=new GameController(playerCount,board);
    }
    public void startGame()
    {
        controller.start();
    }
    public int getPlayerCount()
    {
        return playerCount;
    }
}
