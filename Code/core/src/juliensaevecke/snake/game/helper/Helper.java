package juliensaevecke.snake.game.helper;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tschu on 14.08.2015.
 */
public class Helper
{
    public static boolean isEqual(Vector2 vector1, Vector2 vector2)
    {
        if(vector1.x == vector2.x && vector2.y == vector1.y)
        {
            return true;
        }

        return false;
    }

    public static boolean isOpposite(Vector2 vector1, Vector2 vector2)
    {
        if((vector1.x == -vector2.x && vector1.y == vector2.y)
        || (-vector1.x == vector2.x && vector1.y == vector2.y)
        || (vector1.x == vector2.x && vector1.y == -vector2.y)
        || (vector1.x == vector2.x && -vector1.y == vector2.y))
        {
            return true;
        }

        return false;
    }
}
