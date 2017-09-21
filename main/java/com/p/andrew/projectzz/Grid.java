package com.p.andrew.projectzz;

import com.p.andrew.projectzz.game.SIGame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

public class Grid {

    private static final int x = 20;            // amount of x and y coordinates
    private static final int y = 20;            
    private float xSize;                        //size of each x and y coordinates
    private float ySize;                        
    private float width;                        //width of grid
    private float height;
    private SIGame game;
    private LinkedList listOfAvailableCords;
    private float x2;
    private float[] ballCords;
    private HashSet queue = new HashSet();

    public Grid (SIGame game){
        this.game = game;
        height = game.HEIGHT;
        width = height;
        xSize = width / x;
        ySize = game.HEIGHT / y;
        setGrid();
        x2 = game.WIDTH/2 - width/2;

    }

    public void setGrid(){
        listOfAvailableCords = new LinkedList();
        for(int i = 0;i < y; i++) {
            for(int j = 0; j < x; j++) {
                listOfAvailableCords.add(new float[]{j,i});
            }
        }
    }

    public float getxSize() {
        return xSize;
    }

    public float getySize() {
        return ySize;
    }

    public LinkedList getListOfAvailableCords(){
        return listOfAvailableCords;
    }

    public float[] getRandom(){
        int index = ((int) Math.floor(Math.random() * listOfAvailableCords.size()));
        float[] refc = refactorCords((float[]) listOfAvailableCords.get(index));
        listOfAvailableCords.remove(index);
        return refc;
    }

    public float[] refactorCords(float[] i){
        return new float[]{(i[0] * xSize) + x2,i[1] * ySize};                   //grid to game
    }

    public float[] unfactorCords(float[] i){
        return new float[]{(float) Math.floor((i[0] - x2) / xSize), (float) Math.floor(i[1] / ySize)};    //game to grid
    }

    public void reAddCord(float[] i){
        listOfAvailableCords.add(unfactorCords(i));
    }

    public void deleteBallCords(){
        float ballx = game.getBall().getX()+ game.getBall().getWidth();
        float bally = game.getBall().getY() + game.getBall().getHeight();
        float[] curballcords = unfactorCords(new float[]{ballx, bally});
        if (ballCords == null){
            ballCords = curballcords;
        }
        if (!equalcords(ballCords,curballcords)) {

            int size = 2;
            LinkedList temp = new LinkedList();

            for (Object cord: queue){
                temp.add(cord);
                    listOfAvailableCords.add(cord);
            }

            for (Object cord: temp){
                queue.remove(cord);
            }

            for (int i = ((int) curballcords[0] - size);i<=curballcords[0] + size;i++){
                for (int j = ((int)curballcords[1] - size);j<=curballcords[1] + size;j++){
                    for (Object cord:listOfAvailableCords){
                        if(equalcords((float[]) cord, new float[]{i,j})) {
                            queue.add(cord);
                        }
                    }
                }
            }

            for (Object cord:queue) {
                listOfAvailableCords.remove(cord);
            }

            ballCords = unfactorCords(new float[]{ballx, bally});
        }
    }

    public boolean equalcords(float[] c1, float[] c2){
        return (c1[0] == c2[0]) && (c1[1] == c2[1]);
    }

    public boolean noneEqualCords(float[] coord){
        for (Object cord : listOfAvailableCords) {
            if (equalcords((float[]) cord, coord)){
                return false;
            }
        }
        return true;
    }

}
