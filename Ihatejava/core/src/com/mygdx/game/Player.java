package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player {
	public Vector2 position;
	public Vector2 position_bullet;
	public Sprite sprite;
	public Sprite sprite_bullet;
	public float speed = 500;
	public float speed_bullet = 1000;
	private Array<Vector2> bullets;
	
	public Player(Texture img,Texture img_bullet)
	{
		bullets = new Array<>(); // Initialize the array for bullets
		sprite = new Sprite(img);
		sprite_bullet = new Sprite(img_bullet);
		sprite_bullet.setScale(2);
		sprite.setScale(1);
		position = new Vector2(Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() * 0.05f);
		position_bullet = new Vector2(0,10000);
	}
	
	public void Update(float deltaTime) 
	{
		if (Gdx.input.isButtonJustPressed(0) && position_bullet.y>=Gdx.graphics.getHeight()) {
	            position_bullet.x = position.x + 31;
	            position_bullet.y = position.y;
	        }
		
        for (Vector2 bullet : bullets) {
            bullet.y += deltaTime * speed_bullet;
        }
        
        for (int i = bullets.size - 1; i >= 0; i--) {
            if (bullets.get(i).y >= Gdx.graphics.getHeight()) {
                bullets.removeIndex(i);
            }
        }

//		if(Gdx.input.isKeyPressed(Keys.S)) position.y-=deltaTime*speed;
//		if(Gdx.input.isKeyPressed(Keys.W)) position.y+=deltaTime*speed;
		if(Gdx.input.isKeyPressed(Keys.A)) position.x-=deltaTime*speed;
		if(Gdx.input.isKeyPressed(Keys.D)) position.x+=deltaTime*speed;
		
		if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x = (sprite.getWidth()*sprite.getScaleX()/2);
		if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
		if(position.y+(sprite.getWidth()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) position.y = Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);
		
	
		position_bullet.y+=deltaTime*speed_bullet;
	}
	
	
	public void Draw(SpriteBatch batch) 
	{
		Update(Gdx.graphics.getDeltaTime());
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
		sprite_bullet.setPosition(position_bullet.x, position_bullet.y);
		sprite_bullet.draw(batch);
        for (Vector2 bullet : bullets) {
            sprite_bullet.setPosition(bullet.x, bullet.y);
            sprite_bullet.draw(batch);
        }
//		System.out.println(position.x);
//		System.out.println(position.y);
	}
}
