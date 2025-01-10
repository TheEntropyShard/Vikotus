/*
 * Vikotus - https://github.com/TheEntropyShard/Vikotus
 * Copyright (C) 2024-2025 TheEntropyShard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.vikotus.gui.player;

import me.theentropyshard.vikotus.gui.Card;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent;
import uk.co.caprica.vlcj.player.component.callback.FilledCallbackImagePainter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;


public class PlayerView extends Card {

    private final JLabel titleLabel;

    CallbackMediaPlayerComponent c;

    BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);

    private float position;

    public PlayerView(String title, JFrame frame) {
        this.setLayout(new BorderLayout());
c         = new CallbackMediaPlayerComponent() {
    @Override
    protected void onPaintOverlay(Graphics2D g2d) {
        int yo = 50;

        g2d.setColor(Color.CYAN);
        g2d.drawRoundRect(30, this.getHeight() - 3 - yo, this.getWidth() - 6, 11, 3, 3);

        g2d.setColor(Color.RED);
        int width = (int) (this.getWidth() * position);
        g2d.fillRect(50, this.getHeight() - 5 - yo, width, 5);

        System.out.println(position);
    }
};



            FilledCallbackImagePainter imagePainter = new FilledCallbackImagePainter();
        c.setImagePainter(imagePainter);

        this.c.setOpaque(false);
        this.c.setBorder(new EmptyBorder(10, 10, 10, 10));

        //this.c.mediaPlayer().overlay().add(this.overlay);
        this.c.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
                SwingUtilities.invokeLater(() -> {
                    position = newPosition;
                    repaint();

                });
            }
        });
        this.add(this.c, BorderLayout.CENTER);

        this.titleLabel = new JLabel(title);
        this.titleLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        this.add(this.titleLabel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int yo = 50;

        g2d.setColor(Color.CYAN);
        g2d.drawRoundRect(30, this.getHeight() - 3 - yo, this.getWidth() - 6, 11, 3, 3);

        g2d.setColor(Color.RED);
        int width = (int) (this.getWidth() * position);
        g2d.fillRect(50, this.getHeight() - 5 - yo, width, 5);

        System.out.println(position);
    }

    public void toggleOverlay(boolean enable) {
        //this.mediaPlayerComponent.mediaPlayer().overlay().enable(true);
    }

    public CallbackMediaPlayerComponent getC() {
        return this.c;
    }

    public JLabel getTitleLabel() {
        return this.titleLabel;
    }
}
