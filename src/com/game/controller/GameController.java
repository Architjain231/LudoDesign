package com.game.controller;

import com.game.model.GameBoard;
import com.game.model.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GameController {

    class Pair
    {
        int x,y;
        Pair(int x,int y)
        {
            this.x=x;
            this.y=y;
        }
    }

    private int playerCount;
    private GameBoard board;
    private HashMap<Integer,Pair> posMap;
    private Queue<Player> turnQueue;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    public GameController(int playerCount,GameBoard board)
    {
        this.playerCount=playerCount;
        this.board=board;
        posMap=new HashMap<>();
        turnQueue=new LinkedList<>();
        init();
    }
    public void start()
    {
       while(true)
       {
           displayBoard();
           Player player=turnQueue.poll();
           System.out.println(player.getName()+" Roll Dice...");
           try {
               System.in.read();
           } catch (IOException e) {
               e.printStackTrace();
           }
           int num= player.roll();
           System.out.println("GOT : "+num);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           boolean openHuiH=false;
           if(!player.isOpen())
           {
               if (num == 1||num==6) {
                   {
                       openPlayer(player);
                   }
                   if(num==6)
                       openHuiH=true;
               }
               if(num!=6) {
                   turnQueue.add(player);
                   continue;
               }
           }
              if(player.isOpen())
              {
                  if(num==6)
                  {
                      if(!openHuiH)
                      {
                          System.out.println("Roll again..");
                          try {
                              System.in.read();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                          int roll=player.roll();
                          System.out.println("GOT : "+roll);
                          num+=roll;
                      }
                      else
                      {
                          System.out.println("Roll again..");
                          try {
                              System.in.read();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                          num=player.roll();
                          System.out.println("GOT : "+num);
                      }
                  }
                  if(hasCurrentPlayerWon(player,num))
                  {
                      displayWinner(player);
                      break;
                  }
                  updatePos(num,player);
              }
             turnQueue.add(player);
       }
    }
    private void openPlayer(Player player)
    {
        String gameBoard[][]= board.getBoard();
        player.setOpen(true);
        if(player==p1)
        {
            Pair pos=posMap.get(p1.getPos());
            gameBoard[0][14]="   ";
            gameBoard[pos.x][pos.y]=p1.toString();
        }
        else if(player==p2)
        {
            Pair pos=posMap.get(p2.getPos());
            gameBoard[14][14]="   ";
            gameBoard[pos.x][pos.y]=p2.toString();
        }
        else if(player==p3)
        {
            Pair pos=posMap.get(p3.getPos());
            gameBoard[14][0]="   ";
            gameBoard[pos.x][pos.y]=p3.toString();
        }
        else
        {
            Pair pos=posMap.get(p4.getPos());
            gameBoard[0][0]="   ";
            gameBoard[pos.x][pos.y]=p4.toString();
        }
    }
    private void displayWinner(Player player)
    {
        System.out.println("Hurrahhh..."+player.getName()+" Won");
    }
    private boolean hasCurrentPlayerWon(Player player,int num)
    {
        int newPos=player.getPos()+num;
       if(player==p1)
       {
           return newPos==58;
       }
       else if(player==p2)
       {
           return newPos==63;
       }
       else if(player==p3)
       {
           return newPos==68;
       }
       else
       {
           return newPos==73;
       }
    }

    private void updatePos(int num,Player player)
    {
        String gameBoard[][]=board.getBoard();
        int pos=player.getPos();
        int newPos=num+pos;
       if(player==p1)
       {
           if(newPos>52)
           {
               if(newPos<=57)
               {
                   System.out.println(pos);
                 Pair obj=posMap.get(pos);
                 gameBoard[obj.x][obj.y]=" * ";
                 gameBoard[newPos%52][7]=p1.toString();
                 p1.setPos(newPos);
               }
           }
           else
           {
               Pair obj=posMap.get(pos);
               gameBoard[obj.x][obj.y]=" * ";
               obj=posMap.get(newPos);
               if(p2!=null&&gameBoard[obj.x][obj.y].equals(p2.getName()))
               {
                   p2.setPos(15);
                   p2.setOpen(false);
                   gameBoard[14][14]=p2.toString();
               }
               else if(p3!=null&&gameBoard[obj.x][obj.y].equals(p3.getName()))
               {
                   p3.setPos(28);
                   gameBoard[14][0]=p3.toString();
                   p3.setOpen(false);
               }
               else if(p4!=null&&gameBoard[obj.x][obj.y].equals(p4.getName()))
               {
                   p4.setPos(41);
                   gameBoard[0][0]=p4.toString();
                   p4.setOpen(false);
               }
               gameBoard[obj.x][obj.y]=p1.toString();
               p1.setPos(newPos);
           }

       }
       else if(player==p2)
       {
           if(newPos>13&&(player.getPos()<15||player.getPos()>57))
           {
               if(newPos<=18||newPos>57)
               {
                   System.out.println(pos);
                   newPos=(newPos%57)%13;
                   Pair obj=posMap.get(pos);
                   gameBoard[obj.x][obj.y]=" * ";
                   gameBoard[7][14-newPos]=p2.toString();
                   p2.setPos(57+newPos);
               }
           }
           else
           {
               newPos=(newPos-1)%52+1;
               Pair obj=posMap.get(pos);
               gameBoard[obj.x][obj.y]=" * ";
               obj=posMap.get(newPos);
               if(p1!=null&&gameBoard[obj.x][obj.y].equals(p1.getName()))
               {
                   gameBoard[0][14]=p1.toString();
                   p1.setPos(2);
                   p1.setOpen(false);
               }
               else if(p3!=null&&gameBoard[obj.x][obj.y].equals(p3.getName()))
               {
                   gameBoard[14][0]=p3.toString();
                   p3.setPos(28);
                   p3.setOpen(false);
               }
               else if(p4!=null&&gameBoard[obj.x][obj.y].equals(p4.getName()))
               {
                   gameBoard[0][0]=p4.toString();
                   p4.setPos(41);
                   p4.setOpen(false);
               }
               gameBoard[obj.x][obj.y]=p2.toString();
               p2.setPos(newPos);
           }
       }
       else if(player==p3)
       {
           if(newPos>26&&(player.getPos()<28||player.getPos()>62))
           {
               if(newPos<=31||newPos>62)
               {
                   System.out.println(pos);
                   newPos=(newPos%62)%26;
                   Pair obj=posMap.get(pos);
                   gameBoard[obj.x][obj.y]=" * ";
                   gameBoard[14-newPos][7]=p3.toString();
                   p3.setPos(62+newPos);
               }
           }
           else
           {
               newPos=(newPos-1)%52+1;
               Pair obj=posMap.get(pos);
               gameBoard[obj.x][obj.y]=" * ";
               obj=posMap.get(newPos);
               if(p2!=null&&gameBoard[obj.x][obj.y].equals(p2.getName()))
               {
                   gameBoard[14][14]=p2.toString();
                   p2.setPos(15);
                   p2.setOpen(false);
               }
               else if(p1!=null&&gameBoard[obj.x][obj.y].equals(p1.getName()))
               {
                   gameBoard[0][14]=p1.toString();
                   p1.setPos(2);
                   p1.setOpen(false);
               }
               else if(p4!=null&&gameBoard[obj.x][obj.y].equals(p4.getName()))
               {
                   gameBoard[0][0]=p4.toString();
                   p4.setPos(41);
                   p4.setOpen(false);
               }
               gameBoard[obj.x][obj.y]=p3.toString();
               p3.setPos(newPos);
           }
       }
       else
       {
           if(newPos>39&&(player.getPos()<41)||player.getPos()>67)
           {
               if(newPos<=44||newPos>67)
               {
                   newPos=(newPos%67)%39;
                   Pair obj=posMap.get(pos);
                   gameBoard[obj.x][obj.y]=" * ";
                   gameBoard[7][newPos]=p4.toString();
                   p4.setPos(67+newPos);
               }
           }
           else
           {
               newPos=(newPos-1)%52+1;
               Pair obj=posMap.get(pos);
               gameBoard[obj.x][obj.y]=" * ";
               obj=posMap.get(newPos);
               if(p2!=null&&gameBoard[obj.x][obj.y].equals(p2.getName()))
               {
                   gameBoard[14][14]=p2.toString();
                   p2.setPos(15);
                   p2.setOpen(false);
               }
               else if(p3!=null&&gameBoard[obj.x][obj.y].equals(p3.getName()))
               {
                   gameBoard[14][0]=p3.toString();
                   p3.setPos(28);
                   p3.setOpen(false);
               }
               else if(p1!=null&&gameBoard[obj.x][obj.y].equals(p1.getName()))
               {
                   gameBoard[0][14]=p1.toString();
                   p1.setPos(2);
                   p1.setOpen(false);
               }
               gameBoard[obj.x][obj.y]=p4.toString();
               p4.setPos(newPos);
           }
       }
    }
    private void init()
    {
        int counter=1;
         String gameBoard[][]=board.getBoard();
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(i,8));
            counter++;
        }
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(6,9+i));
            counter++;
        }
        counter--;
        for(int i=0;i<3;i++)
        {
            posMap.put(counter,new Pair(6+i,14));
            counter++;
        }
        counter--;
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(8,14-i));
            counter++;
        }
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(9+i,8));
            counter++;
        }
        counter--;
        for(int i=0;i<3;i++)
        {
            posMap.put(counter,new Pair(14,8-i));
            counter++;
        }
        counter--;
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(14-i,6));
            counter++;
        }
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(8,5-i));
            counter++;
        }
        counter--;
        for(int i=0;i<3;i++)
        {
            posMap.put(counter,new Pair(8-i,0));
            counter++;
        }
        counter--;
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(6,i));
            counter++;
        }
        for(int i=0;i<6;i++)
        {
            posMap.put(counter,new Pair(5-i,6));
            counter++;
        }

            posMap.put(counter,new Pair(0,7));
            counter++;

        for(int i=0;i<5;i++)
        {
            posMap.put(counter,new Pair(1+i,7));
            counter++;
        }
        for(int i=0;i<5;i++)
        {
            posMap.put(counter,new Pair(7,13-i));
            counter++;
        }
        for(int i=0;i<5;i++)
        {
            posMap.put(counter,new Pair(13-i,7));
            counter++;
        }
        for(int i=0;i<5;i++)
        {
            posMap.put(counter,new Pair(7,1+i));
            counter++;
        }
        if(playerCount==2)
        {
            p1=new Player("AJ");
            p1.setPos(2);
            p3=new Player("PJ");
            p3.setPos(28);
            turnQueue.add(p1);
            turnQueue.add(p3);
            gameBoard[0][14]=p1.toString();
            gameBoard[14][0]=p3.toString();
        }
        else if(playerCount==3)
        {
            p1=new Player("AJ");
            p1.setPos(2);
            p2=new Player("VJ");
            p2.setPos(15);
            p3=new Player("PJ");
            p3.setPos(28);
            turnQueue.add(p1);
            turnQueue.add(p2);
            turnQueue.add(p3);
            gameBoard[0][14]=p1.toString();
            gameBoard[14][14]=p2.toString();
            gameBoard[14][0]=p3.toString();
        }
          else
        {
            p1=new Player("AJ");
            p1.setPos(2);
            p2=new Player("VJ");
            p2.setPos(15);
            p3=new Player("PJ");
            p3.setPos(28);
            p4=new Player("SJ");
            p4.setPos(41);
            turnQueue.add(p1);
            turnQueue.add(p2);
            turnQueue.add(p3);
            turnQueue.add(p4);
            gameBoard[0][14]=p1.toString();
            gameBoard[14][14]=p2.toString();
            gameBoard[14][0]=p3.toString();
            gameBoard[0][0]=p4.toString();
        }
    }
    private void displayBoard()
    {
        String gameBoard[][]=board.getBoard();
        for(int i=0;i<15;i++)
        {
            for(int j=0;j<15;j++)
            {
                System.out.print(gameBoard[i][j]+"  ");
            }
            System.out.println();
        }
    }
}
