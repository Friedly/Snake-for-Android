package juliensaevecke.snake.game.helper;

import juliensaevecke.snake.game.Game;
import juliensaevecke.snake.game.components.FoodTypeComponent;
import juliensaevecke.snake.game.components.MovementComponent;
import juliensaevecke.snake.game.components.PhysicComponent;
import juliensaevecke.snake.game.components.RenderComponent;
import juliensaevecke.snake.game.components.SnakeComponent;
import juliensaevecke.snake.game.components.TypeComponent;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Created by julien on 02.08.15.
 */
public class EntityFactory
{
	public static void createSingleWall(Vector2 position, float width, float height)
	{
		Entity wall = new Entity();

		RenderComponent renderComponent = new RenderComponent();
		
		renderComponent.addSprite("", new TextureRegion(new Texture(32, 32, Format.Alpha)), width, height);

		PhysicComponent physicComponent = new PhysicComponent
				(
					wall,
					false,
					position,
					new Vector2(renderComponent.sprites.get(renderComponent.currentSprite).getWidth(), renderComponent.sprites.get(renderComponent.currentSprite).getHeight()),
					false,
					BodyDef.BodyType.StaticBody
				);
		TypeComponent typeComponent = new TypeComponent();
		typeComponent.type = TypeComponent.TYPE.WALL;

		wall.add(renderComponent);
		wall.add(physicComponent);
		wall.add(typeComponent);
		
		EntityManager.getInstance().entitiesToCreate.add(wall);
	}
	public static void createWall()
	{
		createSingleWall(new Vector2(0, Game.MAPHEIGHT - 1), Game.MAPWIDTH, 1);
		createSingleWall(new Vector2(0, 0), Game.MAPWIDTH, 1);

		createSingleWall(new Vector2(Game.MAPWIDTH - 1, 1), 1, Game.MAPHEIGHT - 2);
		createSingleWall(new Vector2(0, 1), 1, Game.MAPHEIGHT - 2);
	}

	public static Entity createSnake(Vector2 position, int length)
	{
		Entity snakehead = new Entity();

		RenderComponent renderComponent = new RenderComponent();
		renderComponent.addSprite("left", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_head_left"), 1, 1);
		renderComponent.addSprite("right", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_head_right"), 1, 1);
		renderComponent.addSprite("up", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_head_up"), 1, 1);
		renderComponent.addSprite("down", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_head_down"), 1, 1);
		renderComponent.currentSprite = "left";


		PhysicComponent physicComponent = new PhysicComponent
				(
						snakehead,
						true,
						position,
						new Vector2(renderComponent.sprites.get(renderComponent.currentSprite).getWidth()/2f, renderComponent.sprites.get(renderComponent.currentSprite).getHeight()/2f),
						false,
						BodyDef.BodyType.DynamicBody
				);

		MovementComponent movementComponent = new MovementComponent
				(
						1,
						0.125f
				);

		SnakeComponent snakeComponent = new SnakeComponent();

		TypeComponent typeComponent = new TypeComponent();
		typeComponent.type = TypeComponent.TYPE.SNAKEHEAD;

		snakehead.add(renderComponent);
		snakehead.add(physicComponent);
		snakehead.add(movementComponent);
		snakehead.add(snakeComponent);
		snakehead.add(typeComponent);
		
		EntityManager.getInstance().entitiesToCreate.add(snakehead);

		for(int currentLength = 1; currentLength < length; ++currentLength)
		{
			snakeComponent.snakeParts.add(createSnakePart(new Vector2(position.x + currentLength * 1, position.y)));
		}

		return snakehead;
	}
	
	public static Entity createSnakePart(Vector2 position)
	{
		Entity snakePart = new Entity();

		RenderComponent renderComponent = new RenderComponent();
		renderComponent.addSprite("left", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_tail_left"), 1, 1);
		renderComponent.addSprite("right", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_tail_right"), 1, 1);
		renderComponent.addSprite("up", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_tail_up"), 1, 1);
		renderComponent.addSprite("down", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_tail_down"), 1, 1);
		renderComponent.addSprite("body", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - snake_body"), 1, 1);
		renderComponent.currentSprite = "body";
		

		PhysicComponent physicComponent = new PhysicComponent
				(
						snakePart,
						true,
						position,
						new Vector2(renderComponent.sprites.get(renderComponent.currentSprite).getWidth()/2f, renderComponent.sprites.get(renderComponent.currentSprite).getHeight()/2f),
						true,
						BodyDef.BodyType.DynamicBody
				);

		TypeComponent typeComponent = new TypeComponent();
		typeComponent.type = TypeComponent.TYPE.SNAKEBODY;

		snakePart.add(renderComponent);
		snakePart.add(physicComponent);
		snakePart.add(typeComponent);
		
		EntityManager.getInstance().entitiesToCreate.add(snakePart);

		return snakePart;
	}

	public static Entity createFood(Vector2 position)
	{
		Entity food = new Entity();

		RenderComponent renderComponent = new RenderComponent();
		renderComponent.addSprite("food", Game.assetManager.get("snake-game-assets.pack", TextureAtlas.class).findRegion("black&white - apple"), 1, 1);
		renderComponent.currentSprite = "food";


		PhysicComponent physicComponent = new PhysicComponent
				(
						food,
						false,
						position,
						new Vector2(renderComponent.sprites.get(renderComponent.currentSprite).getWidth(), renderComponent.sprites.get(renderComponent.currentSprite).getHeight()),
						false,
						BodyDef.BodyType.StaticBody
				);
		TypeComponent typeComponent = new TypeComponent();
		typeComponent.type = TypeComponent.TYPE.FOOD;

		food.add(renderComponent);
		food.add(physicComponent);
		food.add(typeComponent);
		food.add(new FoodTypeComponent());
		
		EntityManager.getInstance().entitiesToCreate.add(food);

		return food;
	}
}