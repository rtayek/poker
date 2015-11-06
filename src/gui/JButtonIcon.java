package gui;
import javax.swing.*;
// cards are 450x650
public class JButtonIcon {
   public static void main(String[] a) {
      JFrame f = new JFrame("gif in an image icon");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JLabel b = new JLabel(new ImageIcon("resources/cards/Kh.gif"));
      f.getContentPane().add(b);
      f.pack();      
      f.setVisible(true);
   }
}