package juliensaevecke.snake.game.helper;

import juliensaevecke.snake.game.components.MovementComponent;
import juliensaevecke.snake.game.components.PhysicComponent;
import juliensaevecke.snake.game.components.RenderComponent;
import juliensaevecke.snake.game.components.SnakeComponent;
import juliensaevecke.snake.game.components.TypeComponent;

import com.badlogic.ashley.core.ComponentMapper;

/**
 * Created by julien on 31.07.15.
 */
public class Mapper
{
	public static ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
	public static ComponentMapper<PhysicComponent> physicMapper = ComponentMapper.getFor(PhysicComponent.class);
	public static ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);
	public static ComponentMapper<SnakeComponent> snakeMapper = ComponentMapper.getFor(SnakeComponent.class);
	public static ComponentMapper<TypeComponent> typeMapper = ComponentMapper.getFor(TypeComponent.class);
}
