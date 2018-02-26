package juliensaevecke.snake.game.screens;

import juliensaevecke.snake.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by julien on 31.07.15.
 */
public class SplashScreen implements Screen
{
    private Image splashImage;
    private Stage splashStage;

    @Override
    public void render(float deltatime)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        splashStage.act();
        splashStage.draw();
    }
    @Override
    public void resize(int width, int height)
    {
        splashStage.getViewport().update(width, height, true);
    }
    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(null);

        Texture texture = Game.assetManager.get("SplashScreen.png");
        splashImage = new Image(texture);
        splashImage.setPosition(Game.VIEWPORTWIDTH / 2 - splashImage.getWidth() / 2, Game.VIEWPORTHEIGHT / 2 - splashImage.getHeight() / 2);

        splashStage = new Stage(new StretchViewport(Game.VIEWPORTWIDTH, Game.VIEWPORTHEIGHT));

        splashStage.addActor(splashImage);

        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2f), Actions.delay(2), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        })));
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
        splashStage.dispose();

        Game.assetManager.unload("SplashScreen.png");
    }
}
