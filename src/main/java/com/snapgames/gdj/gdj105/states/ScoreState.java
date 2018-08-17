package com.snapgames.gdj.gdj105.states;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.ResourceManager;
import com.snapgames.gdj.core.entity.Layer;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.i18n.Messages;
import com.snapgames.gdj.core.io.InputHandler;
import com.snapgames.gdj.core.state.AbstractGameState;
import com.snapgames.gdj.core.ui.Repeat;
import com.snapgames.gdj.core.ui.UIImage;
import com.snapgames.gdj.core.ui.UIText;
import com.snapgames.gdj.gdj105.i18n.Labels;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ScoreState extends AbstractGameState {

    UIText titleText;
    UIImage bgi;

    List<UIText> scoreItems = new ArrayList<>();

    String[] scores = new String[]{
            "McGivrer,100000",
            "Peter,090000",
            "Vince,080000",
            "Phils,070000",
            "Nico,060000",
            "Nuno,050000",
            "Stv,040000",
            "Ju1,020000",
            "Ju2,010000",
            "JeeBee,005000",
    };

    @Override
    public void initialize(Game game, boolean forcedReload) {
        super.initialize(game, forcedReload);


        // activate needed layers
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(true, false);
        }

        // Define the background image object
        BufferedImage bgImg = ResourceManager.getImage("/res/images/background-image.jpg");

        UIImage bgi = new UIImage("background", bgImg, 0, (Game.HEIGHT - bgImg.getHeight()) / 2, 2, 1);
        bgi.scale = 1.0f;
        bgi.dx = 0.031f;
        bgi.x = 0;
        bgi.repeat = Repeat.HORIZONTAL_INFINITY;
        addObject(bgi);

        // read i18n labels
        String titleLabel = Messages.getString(Labels.OPTION_TITLE.getKey());

        // Prepare fonts.
        Font titleFont = ResourceManager.getFont("/res/fonts/Prince Valiant.ttf")
                .deriveFont(2.5f * Game.SCREEN_FONT_RATIO);
        Font menuItemFont = titleFont.deriveFont(1.6f * Game.SCREEN_FONT_RATIO);

        game.getWindow().setTitle("ScoreState");

        // Define the main Game title object
        titleText = new UIText("title",
                (int) (Game.WIDTH) / 2,
                (int) (Game.HEIGHT * 0.01f),
                titleLabel,
                titleFont,
                1, 1,
                Color.WHITE,
                RenderHelper.Justification.CENTER);
        titleText.setLabel(Labels.SCORE_TITLE.getKey());
        addObject(titleText);

        int lineFeed = 16;
        for (int i = 0; i < scores.length; i++) {
            String line = scores[i];
            String[] values = line.split(",");

            String scoreName = String.format("%02d. %s", (i + 1), values[0]);
            String scoreValue = values[1];

            UIText itemName = new UIText("scoreItem_" + i,
                    (int) (Game.WIDTH * 0.28f),
                    (int) (Game.HEIGHT * 0.18f) + (i * lineFeed),
                    scoreName,
                    menuItemFont,
                    1, 1,
                    Color.WHITE,
                    RenderHelper.Justification.LEFT);
            UIText itemValue = new UIText("scoreItem_" + i,
                    (int) (Game.WIDTH * 0.65f),
                    (int) (Game.HEIGHT * 0.18f) + (i * lineFeed),
                    scoreValue,
                    menuItemFont,
                    1, 1,
                    Color.LIGHT_GRAY,
                    RenderHelper.Justification.LEFT);
            scoreItems.add(itemName);
            scoreItems.add(itemValue);
            addObject(itemName);
            addObject(itemValue);
        }

    }

    @Override
    public void input(Game game, InputHandler input) {

    }

    @Override
    public void update(Game game, long dt) {
        // Compute Background Image
        if (bgi != null) {
            bgi.x -= bgi.dx * dt;

            float ax = Math.abs(bgi.x);
            if (ax > game.getWidth() - bgi.width) {
                ax = Math.floorMod((int) ax, bgi.width);
                bgi.x = -(ax + (bgi.dx * dt));
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.snapgames.gdj.core.state.AbstractGameState#keyReleased(com.snapgames.gdj.
     * core.Game, java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(Game game, KeyEvent e) {
        super.keyReleased(game, e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                game.getGSM().activate("title");
                break;
            default:
                break;
        }
    }
}
