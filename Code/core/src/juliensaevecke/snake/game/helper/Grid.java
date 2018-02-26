package juliensaevecke.snake.game.helper;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;

/**
 * Created by julien on 05.08.15.
 */
public class Grid
{
	private float width;
	private float height;
	private float rasterScale;
	private ImmediateModeRenderer20 renderer;

	public Grid(float width, float height, float rasterScale, ImmediateModeRenderer20 renderer)
	{
		this.width = width;
		this.height = height;
		this.rasterScale = rasterScale;

		this.renderer = renderer;
	}

	private void drawLine(float x1, float y1,
						  float x2, float y2,
						  float r, float g, float b, float a)
	{
		renderer.color(r, g, b, a);
		renderer.vertex(x1, y1, 0);
		renderer.color(r, g, b, a);
		renderer.vertex(x2, y2, 0);
	}

	public void drawGrid()
	{
		for(int x = 0; x < width-1; ++x)
		{
			drawLine(1 + rasterScale * x, 1, 1 + rasterScale * x, height-1, 0, 0, 0, 0.125f);
		}

		for(int y = 0; y < height-1; ++y)
		{
			drawLine(1, 1 + rasterScale * y, width-1, 1 + rasterScale * y, 0, 0, 0, 0.125f);
		}
	}
}
