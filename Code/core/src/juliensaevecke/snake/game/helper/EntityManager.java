package juliensaevecke.snake.game.helper;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import juliensaevecke.snake.game.components.PhysicComponent;

/**
 * Created by Tschu on 17.08.2015.
 */
public class EntityManager
{
    private static EntityManager instance;

    public ArrayList<Long> entitiesToDestroy = new ArrayList<Long>();
    public ArrayList<Entity> entitiesToCreate = new ArrayList<Entity>();
    public World physicsWorld = null;
    public Engine entityEngine = null;

    protected EntityManager()
    {

    }

    public static synchronized EntityManager getInstance()
    {
        if (EntityManager.instance == null)
        {
            EntityManager.instance = new EntityManager ();
        }

        return EntityManager.instance;
    }
    
    public void createEntities()
    {
    	for(int index = 0; index < entitiesToCreate.size(); ++index)
        {
            if(entityEngine != null)
            {
            	Entity entity = entitiesToCreate.remove(0);
               
                if(entity != null)
                {
                    PhysicComponent physicComponent = entity.getComponent(PhysicComponent.class);

                    if(physicsWorld != null && physicComponent != null)
                    {
                    	physicComponent.instantiatePhysicComponent(physicsWorld);
                    }

                    entityEngine.addEntity(entity);
                }
            }
        }
    }

    public void destroyEntities()
    {
        for(int index = 0; index < entitiesToDestroy.size(); ++index)
        {
            if(entityEngine != null)
            {
                Entity entity = entityEngine.getEntity(entitiesToDestroy.remove(0));

                if(entity != null)
                {
                    PhysicComponent physicComponent = entity.getComponent(PhysicComponent.class);

                    if(physicsWorld != null && physicComponent != null)
                    {
                        physicsWorld.destroyBody(physicComponent.body);
                    }

                    entityEngine.removeEntity(entity);
                }
            }
        }
    }
}
