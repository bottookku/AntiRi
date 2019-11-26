package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;


public class MyGdxGame extends Game {
	final int MAX_PLANET = 3;
	int[] rotationRndVelocity;
	int[] rotationRndVelocityTmp;
	int screenX;
	int screenY;
	SpriteBatch batch;
	TextureAtlas textureAtlas;
	Sprite[] sprite;
	Sprite spriteSputink;
	int rotationSputnikAngle;
	Sprite pause;
	Sprite play;
	boolean switchPlayPause;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenX = Gdx.graphics.getWidth()/2;
		screenY = Gdx.graphics.getHeight()/2;
		switchPlayPause = true;
		rotationRndVelocity = new int[MAX_PLANET];
		rotationRndVelocityTmp = new int[MAX_PLANET];
		int indexLenght = 300;
		sprite = new Sprite[MAX_PLANET];
		textureAtlas = new TextureAtlas("222.txt");
		pause = textureAtlas.createSprite("pause");
		play = textureAtlas.createSprite("play");

		pause.setPosition(10,10);
		play.setPosition(10,10);
		Random rnd = new Random();
		for(int i = 0; i< MAX_PLANET; i++) {
			rotationRndVelocity[i] = 1 + rnd.nextInt(5);
			rotationRndVelocityTmp[i] = rotationRndVelocity[i];
			int d = i * 100;
			sprite[i] = textureAtlas.createSprite(((Integer) i).toString());
			sprite[i].setSize(20,20);
			if(i==0){
				sprite[i].setCenter( screenX, screenY);
			}else {
				sprite[i].setPosition(d ,d );
				sprite[i].setOrigin(screenX-d, screenY-d);
			}

		}
		spriteSputink = textureAtlas.createSprite("1");
		spriteSputink.setPosition(100,100);
		spriteSputink.setSize(5,5);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.22f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();



		if(Gdx.input.justTouched()&& Gdx.input.getY() < screenY *2 - 10 && Gdx.input.getY() > screenY *2 - 10 - pause.getHeight() &&  Gdx.input.getY() < screenY *2 - 10 && Gdx.input.getX() >  10 && Gdx.input.getX() <  10 + pause.getWidth()) {
			if(switchPlayPause) {

				switchPlayPause = false;
			}else {
				switchPlayPause = true;
			}
		}
		if(switchPlayPause){
			rotationSputnikAngle += 10;
			for(int i = 0; i< MAX_PLANET ; i++) {
				rotationRndVelocity[i] = rotationRndVelocityTmp[i];
			}

			pause.draw(batch);
		}else {
			for(int i = 0; i< MAX_PLANET ; i++) {
				rotationRndVelocity[i] = 0;
			}
			rotationSputnikAngle = 0;
			play.draw(batch);
		}


		for(int i = 0; i< MAX_PLANET; i++) {
			if (i != 0) {
				sprite[i].rotate(rotationRndVelocity[i]);
			}
			sprite[i].draw(batch);

		}
		float x = screenX - (float) Math.cos(Math.toRadians(sprite[2].getRotation()+15))*110;
		float y = screenY - (float)Math.sin(Math.toRadians(sprite[2].getRotation()+15))*110;


		spriteSputink.setPosition(x + (float)Math.cos(Math.toRadians(rotationSputnikAngle))*50,y + (float)Math.sin(Math.toRadians(rotationSputnikAngle))*50);
		spriteSputink.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();

		for(int i =0 ; i < sprite.length ; i++){
			sprite[i] = null;
			
		}
	}
}

