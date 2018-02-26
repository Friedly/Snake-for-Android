package juliensaevecke.snake.game.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import juliensaevecke.snake.game.Game;
import juliensaevecke.snake.game.componentsystems.FoodSystem;
import juliensaevecke.snake.game.componentsystems.MovementSystem;
import juliensaevecke.snake.game.componentsystems.RenderSystem;
import juliensaevecke.snake.game.componentsystems.SnakeSystem;
import juliensaevecke.snake.game.helper.EntityManager;
import juliensaevecke.snake.game.helper.EntityFactory;
import juliensaevecke.snake.game.helper.Grid;
import juliensaevecke.snake.game.helper.SnakeContactListener;

/**
 * Created by julien on 02.08.15.
 */
public class GameScreen implements Screen
{
    /* BOX2D PHYSICS STUFF */
    private World world;
    /*                     */

    /* ENTITY COMPONENT SYSTEM - ASHLEY */
    private Engine engine;
    /*                                  */

    /* CAMERA AND RENDER STUFF */
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Box2DDebugRenderer box2DDebugRenderer;
    private ImmediateModeRenderer20 renderer;
    private Stage gameStage;
    /*                         */

    /* UI */
    private ImageButton leftPanel;
    private ImageButton rightPanel;
    private ImageButton topPanel;
    private ImageButton bottomPanel;

    private Skin skin;

    private ArrayList<Vector2> navigationQueue = new ArrayList<Vector2>();
    /*    */

    /* OBJECTS AND DATA*/
    private Grid mapGrid;
    private static Entity SNAKEHEAD = null;
    /*         */

    /* FLAGS & OTHER STUFF */
    public static boolean RESETGAME = false;
    public static boolean GROWSNAKE = false;
    /*       */

    @Override
    public void show()
    {
        /* RESET INPUT PROCESSOR*/
        Gdx.input.setInputProcessor(null);

        /* INITIALIZE RENDERER AND CAMERA WITH BOX2D METER CONVERSION */
        box2DDebugRenderer = new Box2DDebugRenderer();
        renderer = new ImmediateModeRenderer20(false, true, 0);
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Game.MAPWIDTH, Game.MAPHEIGHT, camera);
        viewport.apply();

        /* INITIALIZE PHYSIC WORLD */
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new SnakeContactListener());

        /* INITIALIZE ENTITY COMPONENT SYSTEM */
        engine = new Engine();
        engine.addSystem(new RenderSystem(spriteBatch));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SnakeSystem(navigationQueue));
        engine.addSystem(new FoodSystem(3, new Rectangle(1, 1, Game.MAPWIDTH-2, Game.MAPHEIGHT-2)));

        /* SET ENTITY ENGINE AND PHYSICWORLD FOR EASIER DESTRUCTION OF ENTITIES AND THEIR BODIES*/
        EntityManager.getInstance().entityEngine = engine;
        EntityManager.getInstance().physicsWorld = world;

        /* INITIALIZE STAGE */
        gameStage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(gameStage);

        initializeUI();

        /* INITIALIZE GRID */
        mapGrid = new Grid(Game.MAPWIDTH, Game.MAPHEIGHT, 1, renderer);

        ///* CREATE GRID */
        EntityFactory.createWall();

        /* CREATE SNAKE */
        SNAKEHEAD = EntityFactory.createSnake(new Vector2((int)Game.MAPHALFWIDTH, (int)Game.MAPHALFHEIGHT), 3);
    }
    @Override
    public void render(float deltatime)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        EntityManager.getInstance().createEntities();
        
        /* UPDATES */
        camera.update();
        world.step(deltatime, 0, 0);
        gameStage.act();

        spriteBatch.setProjectionMatrix(camera.combined);

        /* RENDERING */
        renderer.begin(camera.combined, GL30.GL_LINES);
        mapGrid.drawGrid();
        renderer.end();

        spriteBatch.begin();
        engine.update(deltatime);
        spriteBatch.end();

        gameStage.draw();

        //box2DDebugRenderer.render(world, camera.combined);

        EntityManager.getInstance().destroyEntities();
        
        if(RESETGAME)
            reset();
    }
    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }
    @Override
    public void hide()
    {
    }
    @Override
    public void pause()
    {
    }
    @Override
    public void resume()
    {
    }
    @Override
    public void dispose()
    {
        world.dispose();
        spriteBatch.dispose();
        renderer.dispose();
        gameStage.dispose();
        box2DDebugRenderer.dispose();
    }

    private void reset()
    {
        engine.removeAllEntities();
        world.dispose();

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new SnakeContactListener());
        
        EntityManager.getInstance().entityEngine = engine;
        EntityManager.getInstance().physicsWorld = world;

        EntityFactory.createWall();
        SNAKEHEAD = EntityFactory.createSnake(new Vector2((int)Game.MAPHALFWIDTH, (int)Game.MAPHALFHEIGHT), 3);

        RESETGAME = false;
    }

    private void initializeUI()
    {
        skin = Game.assetManager.get("navigationskins.json", Skin.class);

        leftPanel = new ImageButton(skin, "panel_vertical");
        rightPanel = new ImageButton(skin, "panel_vertical");
        topPanel = new ImageButton(skin, "panel_horizontal");
        bottomPanel = new ImageButton(skin, "panel_horizontal");

        leftPanel.setWidth(Game.CONVERTTOMETER(leftPanel.getWidth()));
        leftPanel.setHeight(Game.CONVERTTOMETER(leftPanel.getHeight()));
        leftPanel.setColor(1, 1, 1, 0.0f);
        leftPanel.addListener(new ClickListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                navigationQueue.add(new Vector2(-1, 0));

                leftPanel.setColor(1, 1, 1, 0.25f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                leftPanel.setColor(1, 1, 1, 0.0f);
            }
        });

        rightPanel.setWidth(Game.CONVERTTOMETER(rightPanel.getWidth()));
        rightPanel.setHeight(Game.CONVERTTOMETER(rightPanel.getHeight()));
        rightPanel.setColor(1, 1, 1, 0.0f);
        rightPanel.setPosition(Game.MAPWIDTH - rightPanel.getWidth(), 0);
        rightPanel.addListener(new ClickListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                navigationQueue.add(new Vector2(1, 0));

                rightPanel.setColor(1, 1, 1, 0.25f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                rightPanel.setColor(1, 1, 1, 0.0f);
            }
        });

        topPanel.setWidth(Game.CONVERTTOMETER(topPanel.getWidth()));
        topPanel.setHeight(Game.CONVERTTOMETER(topPanel.getHeight()));
        topPanel.setColor(1, 1, 1, 0.0f);
        topPanel.setPosition(Game.MAPHALFWIDTH - topPanel.getWidth() / 2.f, topPanel.getHeight());
        topPanel.addListener(new ClickListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                navigationQueue.add(new Vector2(0, 1));

                topPanel.setColor(1, 1, 1, 0.25f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                topPanel.setColor(1, 1, 1, 0.0f);
            }
        });

        bottomPanel.setWidth(Game.CONVERTTOMETER(bottomPanel.getWidth()));
        bottomPanel.setHeight(Game.CONVERTTOMETER(bottomPanel.getHeight()));
        bottomPanel.setColor(1, 1, 1, 0.f);
        bottomPanel.setPosition(Game.MAPHALFWIDTH - bottomPanel.getWidth() / 2.f, 0);
        bottomPanel.addListener(new ClickListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                navigationQueue.add(new Vector2(0, -1));

                bottomPanel.setColor(1, 1, 1, 0.25f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                bottomPanel.setColor(1, 1, 1, 0.0f);
            }
        });

        gameStage.addActor(leftPanel);
        gameStage.addActor(rightPanel);
        gameStage.addActor(topPanel);
        gameStage.addActor(bottomPanel);
    }

    public static Entity getSnakeHead()
    {
        return SNAKEHEAD;
    }
}
