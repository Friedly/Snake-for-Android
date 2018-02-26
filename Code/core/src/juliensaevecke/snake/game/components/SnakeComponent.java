package juliensaevecke.snake.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

/**
 * Created by julien on 09.08.15.
 */
public class SnakeComponent extends Component
{
	public ArrayList<Entity> snakeParts = new ArrayList<Entity>();
}
