package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	Music backgroundMusic;
    SpriteBatch batch;
    Texture img;
    Texture img_bullet;
    Texture img_alien;
    Player player;
    Alien[] aliens;
    int NumWidth_aliens = 5;
    int NumHeight_aliens = 4;
    int spacing_aliens = 50; // Increased spacing for better visibility
    int minX_aliens;
    int minY_aliens;
    int maxX_aliens;
    int maxY_aliens;
    int direction_aliens = 1;
    float speed_aliens = 300;
    Vector2 offset_aliens; // To move alienzzz
    @Override
    public void create() {
    	offset_aliens = Vector2.Zero;
        batch = new SpriteBatch();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("/Ihatejava/assets/breakcore sza_xd.mp3"));
        img = new Texture("/Ihatejava/assets/DurrrSpaceShip.png");
        img_bullet = new Texture("/Ihatejava/assets/frame2-removebg-preview.png");
        img_alien = new Texture("/Ihatejava/assets/Screenshot_2023-11-24_204332-removebg-preview.png");
        player = new Player(img, img_bullet);
        aliens = new Alien[NumWidth_aliens * NumHeight_aliens];
        int i = 0;
        for (int y = 0; y < NumHeight_aliens; y++) {
            for (int x = 0; x < NumWidth_aliens; x++) {
                Vector2 position = new Vector2(x * spacing_aliens + 200, y * spacing_aliens + 210);
                aliens[i] = new Alien(position, img_alien);
                i++;
            }
        }
        
        // Set volume
        backgroundMusic.setVolume(0.1f); // Set the volume to half (0.0 to 1.0)

        // Set looping
        backgroundMusic.setLooping(true); // Set looping to true if you want the music to repeat

        // Play the music
        backgroundMusic.play();
    }

    int amount_alive_aliens = 0;
	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		player.Draw(batch);
		for(int i = 0;i<aliens.length;i++)
		{
			if(aliens[i].Alive)
			{
				if(player.sprite_bullet.getBoundingRectangle().overlaps(aliens[i].sprite.getBoundingRectangle()))
				{
					player.position_bullet.y = 10000;
					aliens[i].Alive = false;
					break;
				}	
			}
		}
		minX_aliens = 10000;
		minY_aliens = 10000;
		maxX_aliens = 0;
		maxY_aliens = 0;
		amount_alive_aliens = 0;
		for(int i = 0;i<aliens.length;i++)
		{
			if(aliens[i].Alive)
			{
				int IndexX = i%NumWidth_aliens;
				int IndexY = i/NumWidth_aliens;
				if(IndexX>maxX_aliens)maxX_aliens = IndexX;
				if(IndexX<minX_aliens)minX_aliens = IndexX;
				if(IndexY>maxY_aliens)maxY_aliens = IndexY;
				if(IndexY<minY_aliens)minY_aliens = IndexY;
				amount_alive_aliens++;
			}
		}
		if(amount_alive_aliens  == 0)
		{
			for(int i = 0;i<aliens.length;i++)
			{
				aliens[i].Alive = true;
			}
			offset_aliens =  new Vector2(0,0);
			batch.end();
			speed_aliens = 100;
			return;
		}
		offset_aliens.x+=direction_aliens*deltaTime*speed_aliens;
		if(aliens[maxX_aliens].position.x>=Gdx.graphics.getWidth())
		{
			direction_aliens = -1;
			offset_aliens.y-=aliens[0].sprite.getHeight()*aliens[0].sprite.getScaleY()*0.25f;
			speed_aliens+=3;
		}
		if(aliens[minX_aliens].position.x<=0)
		{
			direction_aliens = 1;
			offset_aliens.y-=aliens[0].sprite.getHeight()*aliens[0].sprite.getScaleY()*0.25f;
			speed_aliens+=3;
		}
		if(aliens[minY_aliens].position.y<=0)
		{
			Gdx.app.exit();
		}
		for(int i = 0;i<aliens.length;i++)
		{
			aliens[i].position = new Vector2(aliens[i].position_initial.x+offset_aliens.x,aliens[i].position_initial.y+offset_aliens.y);
			if(aliens[i].Alive)
			{
				aliens[i].Draw(batch);
				if(aliens[i].sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle()))
				{
					Gdx.app.exit();
				}
			}
		}
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}