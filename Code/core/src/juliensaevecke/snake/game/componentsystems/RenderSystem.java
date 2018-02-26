package juliensaevecke.snake.game.componentsystems;

import juliensaevecke.snake.game.components.PhysicComponent;
import juliensaevecke.snake.game.components.RenderComponent;
import juliensaevecke.snake.game.helper.Mapper;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by julien on 05.08.15.
 */
public class RenderSystem extends EntitySystem
{
	private ImmutableArray<Entity> entities;
	private SpriteBatch spriteBatch;

	public RenderSystem(SpriteBatch spriteBatch)
	{
		this.spriteBatch = spriteBatch;
	}

	@SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine)
	{
		entities = engine.getEntitiesFor(Family.one(RenderComponent.class, PhysicComponent.class).get());
	}

	public void update(float deltaTime)
	{
		for (int i = 0; i < entities.size(); ++i)
		{
			Entity entity = entities.get(i);

			RenderComponent renderComponent = (Mapper.renderMapper.has(entity)) ? Mapper.renderMapper.get(entity) : null;
			PhysicComponent physicComponent = (Mapper.physicMapper.has(entity)) ? Mapper.physicMapper.get(entity): null;

			if(renderComponent == null)
				continue;

			if(physicComponent != null)
			{
				renderComponent.sprites.get(renderComponent.currentSprite).setPosition(physicComponent.body.getPosition().x - renderComponent.sprites.get(renderComponent.currentSprite).getWidth()/2, physicComponent.body.getPosition().y - renderComponent.sprites.get(renderComponent.currentSprite).getHeight()/2);
				renderComponent.sprites.get(renderComponent.currentSprite).setRotation(physicComponent.body.getAngle() * MathUtils.radiansToDegrees);
			}

			renderComponent.sprites.get(renderComponent.currentSprite).draw(spriteBatch);
		}
	}
}
