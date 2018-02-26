package juliensaevecke.snake.game;

import juliensaevecke.snake.game.screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * The actual game class - starting point for the game.
 */
public class Game extends com.badlogic.gdx.Game
{
    public static final AssetManager assetManager = new AssetManager();

    /*CAMERA PROPERTIES*/
    public static final int VIEWPORTWIDTH = 800;
    public static final int VIEWPORTHEIGHT = 480;
    public static final float PIXELSPERMETER = 32;

    public static final float MAPWIDTH = VIEWPORTWIDTH / PIXELSPERMETER;
    public static final float MAPHEIGHT = VIEWPORTHEIGHT / PIXELSPERMETER;
    public static final float MAPHALFWIDTH = MAPWIDTH / 2;
    public static final float MAPHALFHEIGHT = MAPHEIGHT / 2;

    /*USER INTERFACE PROPERTIES*/
    public static final int DEFAULTBUTTONSIZEX = 200;
    public static final int DEFAULTBUTTONSIZEY = 50;
    public static final int DEFAULTPADDING = 10;

    /*APPLICATION PROPERTIES*/
    public static final int VIBRATELENGTH = 25;
    public static final boolean VIBRATE = true;

    /**
     * Called when the Application is first created.
     */
    @Override
    public void create()
    {
        /* LOAD-RESOURCES */
        assetManager.load("SplashScreen.png", Texture.class);
        assetManager.load("menuUI.pack", TextureAtlas.class);
        assetManager.load("gameUI.pack", TextureAtlas.class);
        assetManager.load("snake-game-assets.pack", TextureAtlas.class);
        assetManager.load("panel.pack", TextureAtlas.class);
        assetManager.load("gameskins.json", Skin.class, new SkinLoader.SkinParameter("gameUI.pack"));
        assetManager.load("menuskins.json", Skin.class, new SkinLoader.SkinParameter("menuUI.pack"));
        assetManager.load("navigationskins.json", Skin.class, new SkinLoader.SkinParameter("panel.pack"));
        /*                */

        assetManager.finishLoading();

        setScreen(new MenuScreen());
    }

    /**
     * Called when the Application is destroyed.
     */
    @Override
    public void dispose()
    {
        assetManager.dispose();
    }

    /**
     * Called when the Application should render itself.
     */
	@Override
	public void render()
    {
        getScreen().render(Gdx.graphics.getDeltaTime());
	}

    /**
     * Called when the Application is paused.
     */
    @Override
    public void pause()
    {
    }

    /**
     * Called when the Application is resumed from a paused state.
     */
    @Override
    public void resume()
    {
    }

    /**
     * Called when the Application is resized.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height)
    {
    }

    /**
     * Converts a pixel value to box2D meters
     * @param pixelValue
     * @return value converted to box2D meters
     */
    public static float CONVERTTOMETER(float pixelValue)
    {
        return (pixelValue/PIXELSPERMETER);
    }
}
