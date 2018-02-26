package juliensaevecke.snake.game.components;

import java.util.HashMap;
import java.util.Map;

import juliensaevecke.snake.game.Game;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by julien on 05.08.15.
 */
public class RenderComponent extends Component
{
	public Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	public String currentSprite = "";

	public RenderComponent()
	{
	}
	
	public void addSprite(String spriteName, TextureRegion textureRegion, float scaleX, float scaleY)
	{
		Sprite newSprite = new Sprite(textureRegion);
		
		newSprite.setSize(Game.CONVERTTOMETER(newSprite.getWidth()*scaleX), Game.CONVERTTOMETER(newSprite.getHeight()*scaleY));
		
		sprites.put(spriteName, newSprite);
	}
}
