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

package me.theentropyshard.vikotus.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Card extends JPanel {
    public Card() {
        this.setBackground(Color.decode("#DEE6ED"));
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(this.getBackground());

        int x = 0;
        int y = 0;
        int width = this.getWidth();
        int height = this.getHeight();

        Border border = this.getBorder();

        if (border != null) {
            Insets insets = border.getBorderInsets(this);

            x = insets.left;
            y = insets.top;
            width -= insets.right * 2;
            height -= insets.bottom * 2;
        }

        g2d.fillRoundRect(x, y, width, height, 25, 25);

        super.paintComponent(g);
    }
}
