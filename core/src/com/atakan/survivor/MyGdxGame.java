package com.atakan.survivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.awt.Button;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture bird;
    Texture bee,bee1,bee2;
    float birdY = 0;
    float birdX = 0;
    int gameState = 0;
    float velocity=0; //hız
    float gravity=0.3f;
    float enemyVelocity=4;
    BitmapFont font,font2;
    Button button;

    Circle birdCircles;
    ShapeRenderer shapeRenderer;

    int numberOfEnemies =4;
    float[] enemyX=new float[numberOfEnemies];
    float [] enemyoffSet= new float[numberOfEnemies];
    float [] enemyoffSet2= new float[numberOfEnemies];
    float [] enemyoffSet3= new float[numberOfEnemies];

    float distance=0;
    Random random;
    int score=0;
    int scoredEnemy=0;

    Circle[] enemyCircles1,enemyCircles2,enemyCircles3;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        bee=new Texture("bee.png");
        bee1=new Texture("bee.png");
        bee2=new Texture("bee.png");

        distance= Gdx.graphics.getWidth()/2;
        random=new Random(1);

        birdX = Gdx.graphics.getWidth() / 5;
        birdY = Gdx.graphics.getHeight() / 4;
        shapeRenderer=new ShapeRenderer();


        birdCircles=new Circle();
        enemyCircles1=new Circle[numberOfEnemies];
        enemyCircles2=new Circle[numberOfEnemies];
        enemyCircles3=new Circle[numberOfEnemies];
        font=new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4);

        font2=new BitmapFont();
        font2.setColor(Color.WHITE);
        font2.getData().setScale(6);

        for (int i=0; i<numberOfEnemies;i++){

            enemyoffSet[i]=(random.nextFloat()*Gdx.graphics.getHeight());
            enemyoffSet2[i]=(random.nextFloat()*Gdx.graphics.getHeight());
            enemyoffSet3[i]=(random.nextFloat()*Gdx.graphics.getHeight());

            enemyX[i]=Gdx.graphics.getWidth() - bee1.getWidth()/2 + i*distance;

            enemyCircles1[i]=new Circle();
            enemyCircles2[i]=new Circle();
            enemyCircles3[i]=new Circle();
        }

    }
    @Override
    public void render() {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            score();

            if (Gdx.input.justTouched()) {
                velocity=-9;
            }

            for (int i=0;i<numberOfEnemies;i++) {

                if (enemyX[i]<Gdx.graphics.getWidth() / 15) {
                    enemyX[i]=enemyX[i] +numberOfEnemies*distance;   //SONSUZ ARI DONGUSU


                    enemyoffSet[i]=((random.nextFloat())*Gdx.graphics.getHeight());
                    if (enemyoffSet[i]<0) {
                        enemyoffSet[i]=enemyoffSet[i]+50;
                    } else if (enemyoffSet[i]>=Gdx.graphics.getHeight()*0.95) {
                        enemyoffSet[i]=enemyoffSet[i]-70;
                    }
                    enemyoffSet2[i]=((random.nextFloat())*Gdx.graphics.getHeight());
                    if (enemyoffSet2[i]<0) {
                        enemyoffSet2[i]=enemyoffSet2[i]+50;
                    } else if (enemyoffSet2[i]>=Gdx.graphics.getHeight()*0.95) {
                        enemyoffSet2[i]=enemyoffSet2[i]-70;
                    }
                    enemyoffSet3[i]=((random.nextFloat())*Gdx.graphics.getHeight());
                    if (enemyoffSet3[i]<0) {
                        enemyoffSet3[i]=enemyoffSet3[i]+50;
                    } else if (enemyoffSet3[i]>=Gdx.graphics.getHeight()*0.95) {
                        enemyoffSet3[i]=enemyoffSet3[i]-70;
                    }

                    if (enemyoffSet[i]>enemyoffSet2[i] && (enemyoffSet[i]>enemyoffSet3[i])) {
                        if (enemyoffSet[i]-enemyoffSet2[i]<=40) {
                            enemyoffSet[i]=enemyoffSet[i]+40;
                        }
                        if (enemyoffSet[i]-enemyoffSet3[i]<=40) {
                            enemyoffSet[i]=enemyoffSet[i]+40;
                        }
                    }

                    if (enemyoffSet2[i]>enemyoffSet[i] && (enemyoffSet2[i]>enemyoffSet3[i])) {
                        if (enemyoffSet2[i]-enemyoffSet[i]<=40) {
                            enemyoffSet2[i]=enemyoffSet2[i]+40;
                        }
                        if (enemyoffSet2[i]-enemyoffSet3[i]<=40) {
                            enemyoffSet2[i]=enemyoffSet2[i]+40;
                        }
                    }

                    if (enemyoffSet3[i]>enemyoffSet2[i] && (enemyoffSet3[i]>enemyoffSet[i])) {
                        if (enemyoffSet3[i]-enemyoffSet2[i]<=40) {
                            enemyoffSet3[i]=enemyoffSet3[i]+40;
                        }
                        if (enemyoffSet3[i]-enemyoffSet2[i]<=40) {
                            enemyoffSet3[i]=enemyoffSet3[i]+40;
                        }
                    }

                } else {
                    enemyX[i]=enemyX[i] - enemyVelocity;
                }

                batch.draw(bee,enemyX[i],enemyoffSet[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
                batch.draw(bee1,enemyX[i],enemyoffSet2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
                batch.draw(bee2,enemyX[i],enemyoffSet3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

                enemyCircles1[i] =new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,enemyoffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
                enemyCircles2[i] =new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,enemyoffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
                enemyCircles3[i] =new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,enemyoffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
            }

            if (birdY>0) {
                velocity=velocity+gravity;
                birdY = birdY - velocity;
            }else {
                gameState=2;
            }

        } else if (gameState==0){

            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState==2) {

            font2.draw(batch,"Game Over! Tap to play ",100,Gdx.graphics.getHeight()/2);
            if (Gdx.input.justTouched()) {
                gameState = 1;

                birdY = Gdx.graphics.getHeight() / 4;

                for (int i = 0; i < numberOfEnemies; i++) {

                    enemyoffSet[i] = (random.nextFloat() * Gdx.graphics.getHeight());
                    enemyoffSet2[i] = (random.nextFloat() * Gdx.graphics.getHeight());
                    enemyoffSet3[i] = (random.nextFloat() * Gdx.graphics.getHeight());

                    enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

                    enemyCircles1[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();
                }
                velocity = 0;
                scoredEnemy=0;
                score=0;
            }
        }

        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

        if(birdY>Gdx.graphics.getHeight()) {
            birdY=Gdx.graphics.getHeight()-50;
        }

        font.draw(batch,String.valueOf(score),100,200);

        batch.end();

        birdCircles.set(birdX+Gdx.graphics.getWidth() / 30,birdY+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 35);

       // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
       // shapeRenderer.setColor(Color.BLACK);
       // shapeRenderer.circle(birdCircles.x,birdCircles.y,birdCircles.radius);

        for (int i=0;i<numberOfEnemies;i++) {

         //   shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,enemyoffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
         //    shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,enemyoffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
         //   shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,enemyoffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);


           if(Intersector.overlaps (birdCircles,enemyCircles1[i]) || Intersector.overlaps(birdCircles,enemyCircles2[i]) || Intersector.overlaps(birdCircles,enemyCircles3[i])) {

                    gameState=2;
           }
        }
       // shapeRenderer.end();
}

    @Override
    public void dispose() {

    }
    public void score () {
        if(enemyX[scoredEnemy]< birdX) {
            score++;

            if(scoredEnemy<numberOfEnemies-1) {
                scoredEnemy++;
            } else {
                scoredEnemy=0;
            }

        }
    }
}

