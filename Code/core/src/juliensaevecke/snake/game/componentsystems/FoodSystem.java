package juliensaevecke.snake.game.componentsystems;

import java.util.Random;

import juliensaevecke.snake.game.components.FoodTypeComponent;
import juliensaevecke.snake.game.components.PhysicComponent;
import juliensaevecke.snake.game.helper.EntityFactory;
import juliensaevecke.snake.game.helper.Helper;
import juliensaevecke.snake.game.helper.Mapper;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class FoodSystem extends EntitySystem
{
	private ImmutableArray<Entity> food;
	private ImmutableArray<Entity> entities;
	
    private Random random = new Random(System.currentTimeMillis());

    public int foodCountToSpawn = 3;
    public Rectangle boundingBox = new Rectangle();
    
    public FoodSystem(int foodCountToSpawn, Rectangle boundingBox)
    {
    	this.foodCountToSpawn = foodCountToSpawn;
    	this.boundingBox = boundingBox;
    }

	@SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine)
	{
		entities = engine.getEntitiesFor(Family.all(PhysicComponent.class).get());
		food = engine.getEntitiesFor(Family.all(FoodTypeComponent.class).get());
	}
	public void update(float deltatime)
	{
		for(int createdFood = food.size(); createdFood < foodCountToSpawn; ++createdFood)
    	{
    		boolean viablePosition = true;
    		Vector2 newPosition = new Vector2();
    		
    		do
    		{
    			newPosition.x = random.nextInt(((int)boundingBox.getWidth() - (int)boundingBox.getX()) + 1) + (int)boundingBox.getX();
    	    	newPosition.y = random.nextInt(((int)boundingBox.getHeight() - (int)boundingBox.getY()) + 1) + (int)boundingBox.getY();
    	    	
    	    	for(int entityIndex = 0; entityIndex < entities.size(); ++entityIndex)
    	    	{
    	    		PhysicComponent physicComponent = Mapper.physicMapper.get(entities.get(entityIndex));
    	    		
    	    		if(physicComponent.body != null && Helper.isEqual(physicComponent.body.getPosition(), newPosition))
	    	    	{
	    	    		viablePosition = false;
	    	    		break;
	    	    	}
    	    	}
    		}
    		while(!viablePosition);
    		
    		if(viablePosition)
    			EntityFactory.createFood(newPosition);
    	}
	}
}
