package juliensaevecke.snake.game.componentsystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import juliensaevecke.snake.game.components.MovementComponent;
import juliensaevecke.snake.game.components.PhysicComponent;
import juliensaevecke.snake.game.components.RenderComponent;
import juliensaevecke.snake.game.components.SnakeComponent;
import juliensaevecke.snake.game.helper.Helper;
import juliensaevecke.snake.game.helper.Mapper;

/**
 * Created by julien on 10.08.15.
 */
public class SnakeSystem extends EntitySystem
{
	private ImmutableArray<Entity> entities;
	private ArrayList<Vector2> navigationQueue;

	public SnakeSystem(ArrayList<Vector2> navigationQueue)
	{
		this.navigationQueue = navigationQueue;
	}

	@SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine)
	{
		entities = engine.getEntitiesFor(Family.all(SnakeComponent.class).get());
	}
	public void update(float deltatime)
	{
		for(int i = 0; i < entities.size(); ++i)
		{
			Entity entity = entities.get(i);

			SnakeComponent snakeComponent = Mapper.snakeMapper.get(entity);
			MovementComponent movementComponent = Mapper.movementMapper.get(entity);
			PhysicComponent snakeheadPhysics = Mapper.physicMapper.get(entity);
			RenderComponent renderComponent = Mapper.renderMapper.get(entity);

			if(navigationQueue.size() > 0 && movementComponent.moved)
			{
				Vector2 potentialNextDirection = navigationQueue.remove(0).cpy();

				if(!Helper.isOpposite(potentialNextDirection.cpy(), movementComponent.direction.cpy()))
				{
					movementComponent.nextDirection = potentialNextDirection.cpy();
				}
			}
			
			if(movementComponent.moved == true)
			{
				Entity lastSnakePart = snakeComponent.snakeParts.get(snakeComponent.snakeParts.size()-1);
				
				/*DRAGGING THE LAST SNAKE PART*/
				PhysicComponent snaketailPhysics = Mapper.physicMapper.get(lastSnakePart);
				snaketailPhysics.body.setTransform(snakeheadPhysics.body.getTransform().getPosition().cpy().sub(movementComponent.direction.cpy()) , snakeheadPhysics.body.getTransform().getRotation());
				
				Entity tmp = snakeComponent.snakeParts.remove(snakeComponent.snakeParts.size()-1);
				snakeComponent.snakeParts.add(0, tmp);
				
				movementComponent.moved = false;
				/*							  */
			}
			else
			{
				if(Helper.isEqual(movementComponent.nextDirection.cpy(), new Vector2(0, 1)))
				{
					renderComponent.currentSprite = "up";
				}
				else if(Helper.isEqual(movementComponent.nextDirection.cpy(), new Vector2(0, -1)))
				{
					renderComponent.currentSprite = "down";
				}
				else if(Helper.isEqual(movementComponent.nextDirection.cpy(), new Vector2(-1, 0)))
				{
					renderComponent.currentSprite = "left";
				}
				else if(Helper.isEqual(movementComponent.nextDirection.cpy(), new Vector2(1, 0)))
				{
					renderComponent.currentSprite = "right";
				}
			}
		}
	}
}
