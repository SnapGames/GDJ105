package com.snapgames.gdj.gdj105.ui;

import com.snapgames.gdj.core.Game;
import com.snapgames.gdj.core.gfx.RenderHelper;
import com.snapgames.gdj.core.ui.UIText;

import java.awt.*;

public class UIPause extends UIText {
    Game game;

    public UIPause(Game game, String name, int x, int y, String text, Font font, int layer, int priority, Color color, RenderHelper.Justification j) {
        super(name, x, y, text, font, layer, priority, color, j);
        this.game = game;
    }


    @Override
    public void draw(Game game, Graphics2D g) {
        g.setColor(new Color(10, 10, 70, 120));
        g.fillRect(0, (int) this.y, game.getWidth(), 24);
        super.draw(game, g);
        g.setColor(new Color(10, 10, 220, 120));
        g.drawRect(-1, (int) this.y - 1, game.getWidth() + 2, 26);
        boundingBox.setBounds(-1, (int) this.y - 1, game.getWidth() + 2, 26);

    }
}
