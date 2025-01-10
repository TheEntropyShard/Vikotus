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

package me.theentropyshard.vikotus;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.ui.FlatButtonBorder;
import me.theentropyshard.vikotus.gui.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gui {
    public static void main(String[] args) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        FlatIntelliJLaf.setup();

        /*JPanel root = new JPanel();*/
        Card root = new Card();
        root.setPreferredSize(new Dimension(960, 540));
       /* root.setBackground(Color.LIGHT_GRAY);
        root.setPreferredSize(new Dimension(960, 540));
        MyTextField myTextField = new MyTextField();
        myTextField.setPreferredSize(new Dimension(500, 40));
        root.add(myTextField);
        MyButton myButton = new MyButton("Hello");
        myButton.setPreferredSize(new Dimension(myButton.getPreferredSize().width, 40));
        root.add(myButton);*/

        JFrame frame = new JFrame("Vikotus");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //playerComponent.release();
                System.exit(0);
            }
        });
        frame.add(root, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class MyTextField extends JTextField {
        public MyTextField() {
            this.putClientProperty("JComponent.roundRect", true);
            //this.putClientProperty("TextComponent.arc", 999);
            this.setBackground(Color.decode("#DEE6ED"));
            //this.setBorder(null);
            this.putClientProperty("JTextField.padding", new Insets(0, 16, 0, 16));
        }
    }

    static class MyButton extends JButton {
        public MyButton(String text) {
            super(text);

            this.putClientProperty(FlatClientProperties.BUTTON_TYPE, "roundRect");
            this.setBackground(Color.decode("#DEE6ED"));
            this.setBorder(new FlatButtonBorder() {
                @Override
                public Insets getBorderInsets(Component c) {
                    Insets i = super.getBorderInsets(c);
                    return new Insets(i.top, 24, i.bottom, 24);
                }
            });
        }
    }
}
