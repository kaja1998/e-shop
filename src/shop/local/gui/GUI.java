package shop.local.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    public GUI() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
    }

    public static void main (String[] args){
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
