package juliensaevecke.snake.game.helper;

import juliensaevecke.snake.game.components.RenderComponent;
import juliensaevecke.snake.game.components.SnakeComponent;
import juliensaevecke.snake.game.components.TypeComponent;
import juliensaevecke.snake.game.screens.GameScreen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by julien on 09.08.15.
 */
public class SnakeContactListener implements ContactListener
{
	public SnakeContactListener()
	{
	}

	@Override
	public void beginContact(Contact contact)
	{
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();

		Entity entityA = (Entity)bodyA.getUserData();
		Entity entityB = (Entity)bodyB.getUserData();

		TypeComponent typeComponentA = Mapper.typeMapper.get(entityA);
		TypeComponent typeComponentB = Mapper.typeMapper.get(entityB);

		if(		(typeComponentA.type == TypeComponent.TYPE.SNAKEHEAD && typeComponentB.type == TypeComponent.TYPE.WALL) ||
				(typeComponentA.type == TypeComponent.TYPE.WALL && typeComponentB.type == TypeComponent.TYPE.SNAKEHEAD) ||
				(typeComponentA.type == TypeComponent.TYPE.SNAKEHEAD && typeComponentB.type == TypeComponent.TYPE.SNAKEBODY) ||
				(typeComponentA.type == TypeComponent.TYPE.SNAKEBODY && typeComponentB.type == TypeComponent.TYPE.SNAKEHEAD))
		{
			GameScreen.RESETGAME = true;
		}

		if((typeComponentA.type == TypeComponent.TYPE.FOOD && typeComponentB.type == TypeComponent.TYPE.SNAKEHEAD) ||
		   (typeComponentA.type == TypeComponent.TYPE.SNAKEHEAD && typeComponentB.type == TypeComponent.TYPE.FOOD))
		{
			Entity snakeHead = GameScreen.getSnakeHead();
			
			if(snakeHead != null)
			{
				SnakeComponent snakeComponent = Mapper.snakeMapper.get(snakeHead);
				
				Entity snakeTail = snakeComponent.snakeParts.get(snakeComponent.snakeParts.size()-1);
				RenderComponent renderComponent = Mapper.renderMapper.get(snakeTail);
				
				snakeComponent.snakeParts.add(EntityFactory.createSnakePart(new Vector2(renderComponent.sprites.get(renderComponent.currentSprite).getX(), renderComponent.sprites.get(renderComponent.currentSprite).getY())));
			}

			if(typeComponentA.type == TypeComponent.TYPE.FOOD)
			{
				EntityManager.getInstance().entitiesToDestroy.add(entityA.getId());
			}
			else
			{
				EntityManager.getInstance().entitiesToDestroy.add(entityB.getId());
			}
		}
	}

	@Override
	public void endContact(Contact contact)
	{

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold)
	{
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse)
	{

	}
}
