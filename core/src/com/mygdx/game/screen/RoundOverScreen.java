package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameState;
import com.mygdx.game.util.AssetProvider;
import com.mygdx.game.util.Button;
import com.mygdx.game.util.GameDataProvider;
import com.mygdx.game.util.Text;
import com.mygdx.game.util.TextButton;

public class RoundOverScreen extends Screen {
    /**
     * The text showing round over
     */
    private Text roundOverText;
    /**
     * The number of buttons on the screen
     */
    private static final int BUTTONS = 3;
    /**
     * The buttons
     */
    private TextButton[] buttons;

    /**
     * RoundOverScreen constructor
     */
    public RoundOverScreen() {
        super();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        roundOverText = new Text(AssetProvider.instance().getAsset(AssetProvider.TITLE_FONT, BitmapFont.class),
                "ROUND OVER", 0, 0);
        roundOverText.setX(w/2 - roundOverText.getWidth()/2);
        roundOverText.setY(h - roundOverText.getHeight() * 1.5f);

        buttons = new TextButton[BUTTONS];
        buttons[0] = new TextButton(new Text(AssetProvider.instance().getAsset(AssetProvider.STANDARD_FONT, BitmapFont.class),
                "NEXT ROUND", 0, 0));
        buttons[0].setX(w/2 - buttons[0].getWidth()/2);
        buttons[0].setY(h/2);

        buttons[1] = new TextButton(new Text(AssetProvider.instance().getAsset(AssetProvider.STANDARD_FONT, BitmapFont.class),
                "RETURN TO MAIN MENU", 0, 0));
        buttons[1].setX(w/2 - buttons[1].getWidth()/2);
        buttons[1].setY(h/3);

        buttons[2] = new TextButton(new Text(AssetProvider.instance().getAsset(AssetProvider.STANDARD_FONT, BitmapFont.class),
                "QUIT", 0, 0));
        buttons[2].setX(w/2 - buttons[2].getWidth()/2);
        buttons[2].setY(h/6);
    }

    /**
     * Renders the screen
     *
     * @param entityBatch The entity batch
     * @param uiBatch The ui batch
     */
    @Override
    public void render(SpriteBatch entityBatch, SpriteBatch uiBatch) {
        uiBatch.begin();
        roundOverText.render(uiBatch);

        for(Button b : buttons) {
            b.render(uiBatch);
        }
        uiBatch.end();
    }

    /**
     * Updates the state of the screen
     *
     * @param elapsedTime The time elapsed since the previous frame
     */
    @Override
    public void update(float elapsedTime) {
        boolean checkTouch = Gdx.input.isTouched();
        GameDataProvider.instance().setIsTouched(checkTouch);
        boolean wasTouched = GameDataProvider.instance().wasTouched();

        int touchX = Gdx.input.getX();
        int touchY = Gdx.input.getY();

        for(int i = 0; i < BUTTONS; ++i) {
            buttons[i].update(elapsedTime, checkTouch, touchX, touchY);
        }

        // Don't check is buttons downs if checked last frame
        if(!wasTouched) {
            if(buttons[0].isDown()) {
                GameDataProvider dataProvider = GameDataProvider.instance();
                dataProvider.startNewRound();
                dataProvider.setState(GameState.PLAYING);
            }

            if(buttons[1].isDown()) {
                GameDataProvider.instance().setState(GameState.MAIN_MENU);
            }

            if(buttons[2].isDown()) {
                Gdx.app.exit();
            }
        }
    }

    /**
     * Disposes of the screen resources
     */
    @Override
    public void dispose() {
        roundOverText.dispose();
        for(Button b : buttons) {
            b.dispose();
        }
    }
}
