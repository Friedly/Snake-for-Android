package juliensaevecke.snake.game.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by julien on 09.08.15.
 */
public class TypeComponent extends Component
{
	public enum TYPE
	{
		SNAKEHEAD,
		SNAKEBODY,
		FOOD,
		WALL,
		UNKNOWN
	}

	public TYPE type = TYPE.UNKNOWN;
}
