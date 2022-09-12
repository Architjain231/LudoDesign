package com.game.model;

public class Player {
    private String name;
    private int pos;
    private boolean isOpen;
    public Player(String name)
    {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int roll()
    {
        return (int)((Math.random()*10)%6+1);
    }

    @Override
    public String toString() {
        return ""+this.getName();
    }
}
