package juliensaevecke.snake.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by julien on 07.08.15.
 */
public class MovementComponent extends Component
{
	public float stepLength;
	public float elapsedTime;
	public float stepTime;
	
	public Vector2 direction = new Vector2(-1, 0);
	public Vector2 nextDirection = new Vector2(-1, 0);
	
	public boolean moved = false;

	public MovementComponent(float stepLength, float stepTime)
	{
		this.stepLength = stepLength;
		this.elapsedTime = stepTime;
		this.stepTime = stepTime;
	}
}
