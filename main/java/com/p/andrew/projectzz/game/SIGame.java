package com.p.andrew.projectzz.game;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.p.andrew.projectzz.Grid;
import com.p.andrew.projectzz.sprites.Ball;
import com.p.andrew.projectzz.sprites.Block;
import com.p.andrew.projectzz.Button;
import com.p.andrew.projectzz.sprites.Debuff;
import com.p.andrew.projectzz.sprites.Detector;
import com.p.andrew.projectzz.sprites.Player;
import com.p.andrew.projectzz.R;
import com.p.andrew.projectzz.sprites.Portal;
import com.p.andrew.projectzz.sprites.PowerUp;
import com.p.andrew.projectzz.sprites.Sprite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SIGame {

    private List<Sprite> sprites;
    private boolean playing;
    private boolean end;
    private Player player1;                      //right player
    private Player player2;                      //left player
    private Bitmap pauseMenu;
    private List<Player> players;
    private Ball ball;
    private BackGround bg;
    public static final float HEIGHT = 480 *2;       //480 960
    public static final float WIDTH = 856 *2;        //856 1712
    private boolean pause;                       //if the game has paused
    private boolean start;                       //if the game has started
    private int Difficulty;
    private Resources res;
    private float pauseMenuX;
    private float pauseMenuY;
    private Button resumeButton;
    private Button backButton;
    private Button restartButton;
    private Paint paint;
    private ScorePanel scorepanel;
    private GamePanel gamePanel;
    private Grid grid;
    private Boolean usedPad;
    private Boolean randomizeAngle;
    private HashMap startimes;
    private Bitmap asteroid;
    private Bitmap portal;
    private Bitmap powerup;
    private Bitmap noPoints;
    private Bitmap randomAngle;
    private Bitmap arc;
    private Bitmap speedUp;


    public SIGame(Resources res) {
        sprites = new LinkedList<>();
        players = new LinkedList<>();
        bg = new BackGround(BitmapFactory.decodeResource(res, R.drawable.background1),(int)WIDTH,(int)HEIGHT);
        ball = new Ball(BitmapFactory.decodeResource(res, R.drawable.ball),this);
        player1 = new Player(BitmapFactory.decodeResource(res, R.drawable.player), 1,this);
        player2 = new Player(BitmapFactory.decodeResource(res, R.drawable.player), 2,this);
        asteroid = BitmapFactory.decodeResource(res, R.drawable.asteroid);
        portal = BitmapFactory.decodeResource(res, R.drawable.portal);
        powerup = BitmapFactory.decodeResource(res,R.drawable.powerup);
        noPoints = BitmapFactory.decodeResource(res,R.drawable.nopoints);
        randomAngle = BitmapFactory.decodeResource(res,R.drawable.randomangle);
        arc = BitmapFactory.decodeResource(res,R.drawable.arc);
        speedUp = BitmapFactory.decodeResource(res,R.drawable.speedup);
        players.add(player1);
        players.add(player2);
        end = false;
        pause = false;
        playing = false;
        Difficulty = 0;
        this.res = res;
        start = true;
        grid = new Grid(this);
        usedPad = false;
        randomizeAngle = false;
        startimes = new HashMap();

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean getPause() {
        return pause;
    }

    public void update() {
        if (playing) {
            player1.update(ball);
            player2.update(ball);
            ball.update();
            CheckCollision();
            increaseDifficulty();
            grid.deleteBallCords();



            if (elapsedTime(1,500)){
                GenerateSprites();
                usedPad = false;
            }
            if (randomizeAngle) {
                if (elapsedTime(2, 10000)) {
                    randomizeAngle = false;
                }
            }

            if (elapsedTime(3,10000)){
                scorepanel.setScoreMultiplier(1);
            }

            offBounds();
        }
    }

    public void GenerateSprites() {
        if (grid.getListOfAvailableCords().size() > 160) {

            spawnBlocks();
            if (elapsedTime(4,5000)){
                spawnDebuffs();
                spawnPowerUp();
                spawnPortals();
            }
        }
    }

    public boolean elapsedTime(int key, int elapse){
        if (startimes.get(key) == null) {
            startimes.put(key, System.nanoTime());
        }
        long elapsed = (System.nanoTime() - (long)startimes.get(key)) / 1000000;
        if (elapsed > elapse) {
            startimes.put(key,System.nanoTime());
            return true;
        }
        return false;
    }


    public void spawnBlocks() {
        int gen = (int) (Math.random() * 2 + Difficulty);
        for (int i = 0; i < gen; i++) {
            float[] cords = grid.getRandom();
            Block blk = new Block(asteroid, cords[0], cords[1],grid,gen);
            sprites.add(blk);
        }
    }

    public void spawnPortals() {
        int gen = (int) (Math.random() * 1 + Difficulty);
        for (int i = 0; i < gen; i++) {
            if (grid.getListOfAvailableCords().size() > 2) {
                float[] cords = grid.getRandom();
                float[] cords2 = grid.getRandom();
                Portal p1 = new Portal(portal, cords[0], cords[1],grid);
                Portal p2 = new Portal(portal, cords2[0], cords2[1],grid);
                p1.setEnd(p2);
                sprites.add(p1);
                sprites.add(p2);
            }
        }
    }

    public void spawnPowerUp() {
        int gen = (int) (Math.random() + Difficulty + 1);
        for (int i=0; i < gen; i++){
            float[] cord = grid.getRandom();
            PowerUp pu = new PowerUp(powerup,cord[0],cord[1],grid);
            sprites.add(pu);
        }
    }

    public void spawnDebuffs() {
        int gen = (int) (Math.random() + Difficulty);
        for (int i=0;i<gen;i++) {
            sprites.add(getRandomDebuff());
        }
    }

    public Debuff getRandomDebuff(){
        int random = (int) (Math.random() * 4);
        float[] cord = grid.getRandom();
        switch (random){
            case 0:
                return new Debuff(speedUp,cord[0],cord[1],grid,0);
            case 1:
                return new Debuff(arc,cord[0],cord[1],grid,1);
            case 2:
                return new Debuff(randomAngle,cord[0],cord[1],grid,2);
            case 3:
                return new Debuff(noPoints,cord[0],cord[1],grid,3);
        }
        return null;
    }


    public void draw(Canvas canvas) {
        if (!end){
        bg.draw(canvas);
        player1.draw(canvas);
        player2.draw(canvas);
        ball.draw(canvas);
        for (Sprite sprite : sprites) {
            sprite.draw(canvas);
        }
        if (pause) {
            drawPauseScreen(canvas);
        }
        }
        else {
            bg = new BackGround(BitmapFactory.decodeResource(res,R.drawable.greenbg),(int)WIDTH,(int)HEIGHT);
            bg.draw(canvas);
            backButton.draw(canvas);
            restartButton.draw(canvas);
        }
    }

    public void CheckCollision() {
        collideCeilingFloor();
        collidePlayer();
        collideSprites();
    }

    public void collidePlayer() {
        for (Player player : players) {
            if (ball.intersect(player) && usedPad == false) {
                switch (player.getDirc()) {
                    case 1:
                        yAxisReflection();
                        break;
                    case 2:
                        playerCollision(player);
                        break;
                    case 3:
                        playerCollision(player);
                        xAxisReflection();
                        break;
                }
                usedPad = true;
                player.setDirc(2);
            }
        }
    }

    public void playerCollision(Player player) {
        float centerBall = ball.getY() + ball.getHeight()/2;
        float centerPlayer = player.getY() + player.getHeight()/2;
        float scaleFromCenter = (centerBall - centerPlayer) / (player.getHeight()/2);
        float range = 60;
        float start = 180;
        float angle = ((scaleFromCenter) * range) + start;
        ball.setAngle(angle);
        yAxisReflection();
    }

    public void xAxisReflection() {
        if (ball.getAngle() < 180) {
            ball.setAngle(ball.getAngle() + 2 * (90 - ball.getAngle()));
        } else {
            ball.setAngle(ball.getAngle() + 2 * (270 - ball.getAngle()));
        }
    }

    public void yAxisReflection() {
        if (ball.getAngle() > 90 && ball.getAngle() < 270) {
            ball.setAngle(ball.getAngle() + 2 * (180 - ball.getAngle()));
        } else {
            ball.setAngle(360 - ball.getAngle());
        }
    }

    public void collideCeilingFloor() {
        if (ball.getY() < 0) {
            if (ball.getdy() < 0) {
                yAxisReflection();
            }
        }
        if (ball.getY() + ball.getHeight() > HEIGHT) {
            if (ball.getdy() > 0) {
                yAxisReflection();
            }
        }
    }

    public boolean collideSprites() {
        for (Sprite sprite : sprites) {
            if (ball.intersect(sprite)) {
                if (sprite.getClass() == Portal.class) {
                    Portal portal = ((Portal) sprite);
                    deletePortal(portal);
                    return false;
                }
                if (sprite.getClass() == Block.class) {
                    switch (((Block) sprite).getDirc()) {
                        case 0:
                            xAxisReflection();
                            yAxisReflection();
                            break;
                        case 1:
                            yAxisReflection();
                            break;
                        case 2:
                            xAxisReflection();
                            break;
                    }
                    if (randomizeAngle) {
                        randomizeAngle(10);
                    }
                    hitBlock((Block) sprite);
                    return false;
                }
                if (sprite.getClass() == PowerUp.class){
                    int random1to3 = (int) (Math.random() * 3 + 1);
                    scorepanel.getPowerUp(random1to3);
                    sprites.remove(sprite);
                    grid.reAddCord(new float[]{sprite.getX(),sprite.getY()});
                    return false;
                }
                if (sprite.getClass() == Debuff.class){
                    hitDeBuff((Debuff)sprite);
                    sprites.remove(sprite);
                    return false;
                }
            }
            if (sprite.getClass() == Block.class) {
                ((Block) sprite).update(ball);
            }
        }
        return true;
    }

    private void hitDeBuff(Debuff debuff) {
        if (debuff.getId() == 0){               //slow
            if (ball.getSpeed() < 11)
            ball.speedMultiplier((float) 1.5);
        } else if (debuff.getId() == 1) {       //arc

        } else if (debuff.getId() == 2) {       //randomangle

        }else if (debuff.getId() == 3) {        //nopoints
            scorepanel.setScoreMultiplier(0);
        }
    }





    public void randomizeAngle(float range){
        ball.setAngle((float) (Math.random()* range + (ball.getAngle() - range/2)));
    }

    public void hitBlock(Block block){
        sprites.remove(block);
        scorepanel.score = scorepanel.score + (10* scorepanel.getScoreMultiplier());
        if(block.getHitPoint() > 1){
            block.hit(1);
            sprites.add(block);
        } else {
            grid.reAddCord(new float[]{block.getX(), block.getY()});
        }
    }


    public void deletePortal(Portal portal) {
        ball.setX(portal.getEnd().getX() + portal.getWidth() / 2);
        ball.setY(portal.getEnd().getY() + portal.getHeight() / 2);
        sprites.remove(sprites.indexOf(portal.getEnd()));
        sprites.remove(sprites.indexOf(portal));
        grid.reAddCord(new float[]{portal.getX(), portal.getY()});
        grid.reAddCord(new float[]{portal.getEnd().getX(), portal.getEnd().getY()});
    }


    public void offBounds() {
        if (ball.getX() > WIDTH || ball.getX() < 0) {
            drawEndGame();

            /*
            ball.restart();
            playing = false;
            scorepanel.setHighScore();
            sprites.clear();
            grid.setGrid();
            start = true;
            Difficulty = 0;
            for (Player player : players) {
                player.startY();
            }
            */
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void pause() {
        pause = true;
        playing = false;
    }

    public void unPause() {
        pause = false;
        if (!start) {
            playing = true;
        }
    }

    public void start() {
        start = false;
        playing = true;
    }

    public void drawPauseScreen(Canvas canvas) {
        canvas.drawBitmap(pauseMenu, pauseMenuX, pauseMenuY, null);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(HEIGHT / 10);
        //canvas.drawText("Paused", WIDTH / 2, pauseMenuY + (paint.getTextSize()), paint);
        paint.setTextSize(HEIGHT / 15);
        //resumeButton.draw(canvas);
        //backButton.draw(canvas);
    }

    public void setPause() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        pauseMenu = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.pausescreen), (int) WIDTH * 1 / 2, (int) HEIGHT * 1 / 2, false);
        pauseMenuX = WIDTH / 2 - pauseMenu.getWidth() / 2;
        pauseMenuY = HEIGHT / 2 - pauseMenu.getHeight() / 2;
        paint.setTextSize(HEIGHT / 15);
        //resumeButton = new Button(pauseMenuX, (pauseMenuY + 2 * paint.getTextSize() + 20), paint, " ", pauseMenu.getWidth(), (paint.getTextSize()));
        //backButton = new Button(pauseMenuX, (pauseMenuY + 3 * paint.getTextSize() + 20), paint, " ", pauseMenu.getWidth(), (paint.getTextSize()));
        resumeButton = new Button(pauseMenuX + 273, (pauseMenuY + 157), paint, " ", 325, 100);
        backButton = new Button(pauseMenuX + 273, (pauseMenuY + 316), paint, " ", 325, 100);
    }

    public void drawEndGame(){
        end = true;
        playing = false;
        paint.setTextAlign(Paint.Align.CENTER);
        restartButton = new Button(pauseMenuX, (HEIGHT/2-paint.getTextSize()), paint, "Restart", pauseMenu.getWidth(), (paint.getTextSize()));
        backButton = new Button(pauseMenuX, (HEIGHT/2), paint, "Back", pauseMenu.getWidth(), (paint.getTextSize()));
    }



    public boolean getStart() {
        return start;
    }

    public Ball getBall(){
        return ball;
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setScorePanel(ScorePanel scorepanel) {
        this.scorepanel = scorepanel;
    }

    public void increaseDifficulty() {
        if (scorepanel.getScore()<500){
            Difficulty =0;
        }
        else if (scorepanel.getScore() >= 500 && scorepanel.getScore()<2000) {
            Difficulty = 1;
            //ball.speedMultiplier((float) 1.5);
        }
        else if(scorepanel.getScore() >= 2000 && scorepanel.getScore()<5000){
            Difficulty = 2;
        }
        else if(scorepanel.getScore() >= 5000 && scorepanel.getScore()<8000){
            Difficulty = 3;
        }
        else if(scorepanel.getScore() >= 8000 && scorepanel.getScore()<10000){
            Difficulty = 4;
        }
        else if(scorepanel.getScore() >= 10000 && scorepanel.getScore()<13000){
            Difficulty = 5;
        }
        else{
            Difficulty = 6;
        }
    }

    public Grid getGrid(){
        return grid;
    }


    public List<Player> getplayers() {
        return players;
    }

    public void deleteSquare() {
        ball.setcenter();
        int i = 100;
        Detector detector = new Detector(ball.getCenterx()-i,ball.getCentery()-i,2*i,2*i);
        Iterator iterator = sprites.iterator();
        while(iterator.hasNext()){
            Object next = iterator.next();
            if (detector.intersect((Sprite) next)){
                iterator.remove();
            }
        }

    }

    public int getDifficulty(){ return Difficulty;}

    public boolean getend(){
        return end;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public void setRestartButton(Button restartButton) {
        this.restartButton = restartButton;
    }
}
