package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Alien {
	public Vector2 position;
	public Vector2 position_initial;
	public Sprite sprite;
	public Boolean Alive = true;
	Player player;
	public Alien(Vector2 _position, Texture img) 
	{
		position = _position;
		position_initial = position;
		sprite = new Sprite(img);
		sprite.setScale(1);
	}
	public void Draw(SpriteBatch batch) 
	{
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}
}
