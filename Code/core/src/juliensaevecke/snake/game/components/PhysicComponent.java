package juliensaevecke.snake.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by julien on 05.08.15.
 */
public class PhysicComponent extends Component
{
	public Body body;
	
	private FixtureDef fixtureDef;
	private BodyDef bodyDef;
	private Object userData;
	
	private boolean centered;
	private boolean isSensor;
	private Vector2 startPosition;
	private Vector2 size;
	private BodyDef.BodyType bodytype;

	public PhysicComponent(Object userData, boolean centered, Vector2 position, Vector2 size, boolean isSensor, BodyDef.BodyType bodytype)
	{
		this.userData = userData;
		this.centered = centered;
		this.isSensor = isSensor;
		this.size = size;
		this.bodytype = bodytype;
		this.startPosition = position;
	}
	
	public void instantiatePhysicComponent(World world)
	{
		bodyDef = new BodyDef();

		if(centered)
			bodyDef.position.set(startPosition.x + size.x, startPosition.y + size.y);
		else
			bodyDef.position.set(startPosition.x + size.x/2, startPosition.y + size.y/2);

		bodyDef.type = bodytype;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size.x/2, size.y/2);

		fixtureDef = new FixtureDef();
		fixtureDef.isSensor = isSensor;
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0.f;
		fixtureDef.friction = 0.f;
		fixtureDef.density = 0.f;
		
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(userData);

		shape.dispose();
	}
}
