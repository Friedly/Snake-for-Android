package juliensaevecke.snake.game.screens;

import juliensaevecke.snake.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by julien on 31.07.15.
 */
public class MenuScreen implements Screen
{
    private Stage menuStage;
    private Table table;
    private ShapeRenderer debugRenderer;
    private Skin skin;

    private TextButton playButton;
    private TextButton highscoreButton;
    private TextButton extrasButton;
    private TextButton quitButton;

    @Override
    public void show()
    {
        menuStage = new Stage(new StretchViewport(Game.VIEWPORTWIDTH, Game.VIEWPORTHEIGHT));

        Gdx.input.setInputProcessor(menuStage);

        skin = Game.assetManager.get("menuskins.json", Skin.class);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        playButton = new TextButton("Play", skin);
        highscoreButton = new TextButton("Highscore", skin);
        extrasButton = new TextButton("Extras", skin);
        quitButton = new TextButton("Quit", skin);

        addListener();

        table.add(playButton)
                .width(Game.DEFAULTBUTTONSIZEX)
                .height(Game.DEFAULTBUTTONSIZEY)
                .pad(Game.DEFAULTPADDING);
        table.row();
        table.add(highscoreButton)
                .width(Game.DEFAULTBUTTONSIZEX)
                .height(Game.DEFAULTBUTTONSIZEY)
                .pad(Game.DEFAULTPADDING);
        table.row();
        table.add(extrasButton)
                .width(Game.DEFAULTBUTTONSIZEX)
                .height(Game.DEFAULTBUTTONSIZEY)
                .pad(Game.DEFAULTPADDING);
        table.row();
        table.add(quitButton)
                .width(Game.DEFAULTBUTTONSIZEX)
                .height(Game.DEFAULTBUTTONSIZEY)
                .pad(Game.DEFAULTPADDING);

        menuStage.addActor(table);

        debugRenderer = new ShapeRenderer();
        debugRenderer.setAutoShapeType(true);
    }
    @Override
    public void render(float deltatime)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.begin();

        menuStage.act(deltatime);
        menuStage.draw();

        debugRenderer.end();
    }
    @Override
    public void dispose()
    {
        debugRenderer.dispose();
        menuStage.dispose();
    }
    @Override
    public void resize(int width, int height)
    {
        menuStage.getViewport().update(width, height, true);
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

    private void addListener()
    {
        playButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(Game.VIBRATE)
                    Gdx.input.vibrate(Game.VIBRATELENGTH);

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        highscoreButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(Game.VIBRATE)
                    Gdx.input.vibrate(Game.VIBRATELENGTH);

                ((Game) Gdx.app.getApplicationListener()).setScreen(new SplashScreen());
            }
        });

        extrasButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(Game.VIBRATE)
                    Gdx.input.vibrate(Game.VIBRATELENGTH);

                ((Game)Gdx.app.getApplicationListener()).setScreen(new SplashScreen());
            }
        });

        quitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(Game.VIBRATE)
                    Gdx.input.vibrate(Game.VIBRATELENGTH);

                Gdx.app.exit();
            }
        });
    }

}
