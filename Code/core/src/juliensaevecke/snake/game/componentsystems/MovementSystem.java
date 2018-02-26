package juliensaevecke.snake.game.componentsystems;

import juliensaevecke.snake.game.components.MovementComponent;
import juliensaevecke.snake.game.components.PhysicComponent;
import juliensaevecke.snake.game.helper.Mapper;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

/**
 * Created by julien on 07.08.15.
 */
public class MovementSystem extends EntitySystem
{
	private ImmutableArray<Entity> entities;

	@SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine)
	{
		entities = engine.getEntitiesFor(Family.all(MovementComponent.class, PhysicComponent.class).get());
	}
	public void update(float deltatime)
	{
		for(int i = 0; i < entities.size(); ++i)
		{
			Entity entity = entities.get(i);

			PhysicComponent physicComponent = Mapper.physicMapper.get(entity);
			MovementComponent movementComponent = Mapper.movementMapper.get(entity);

			if(movementComponent.elapsedTime <= 0.f)
			{
				movementComponent.direction = movementComponent.nextDirection.cpy();
				
				float positionX = physicComponent.body.getPosition().x;
				float positionY = physicComponent.body.getPosition().y;

				positionX += movementComponent.direction.cpy().x * movementComponent.stepLength;
				positionY += movementComponent.direction.cpy().y * movementComponent.stepLength;

				physicComponent.body.setTransform(positionX, positionY, 0);

				movementComponent.elapsedTime = movementComponent.stepTime;
				movementComponent.moved = true;
			}
			else
			{
				movementComponent.elapsedTime -= deltatime;
			}
		}
	}
}
