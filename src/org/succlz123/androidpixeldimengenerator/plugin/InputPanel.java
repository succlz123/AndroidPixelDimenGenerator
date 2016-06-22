package org.succlz123.androidpixeldimengenerator.plugin;

import javax.swing.*;

/**
 * Created by succlz123 on 16/6/22.
 */
public class InputPanel {
    private JPanel contentPane;

    private JLabel text1;
    private JLabel text2;
    private JTextField textField1;
    private JTextField textField2;

    private JLabel text3;
    private JLabel text4;
    private JTextArea textPixel;

    public InputPanel() {
        textPixel.setLineWrap(true);
        textPixel.setWrapStyleWord(true);
        textPixel.setText("1920 1080\n1280 720");
    }

    public JPanel getComponent() {
        return contentPane;
    }

    public String getPositiveNumber() {
        return textField1.getText();
    }

    public String getNegativeNumber() {
        return textField2.getText();
    }

    public String getTextPixel() {
        return textPixel.getText();
    }
}
