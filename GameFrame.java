import javax.swing.*;
    public class GameFrame extends JFrame {

        GameFrame(){

            this.add(new GamePanel());
            this.setTitle("Snake");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            // Automatically fit the subcomponents in the JFrame
            this.pack();
            this.setVisible(true);
            // Frame to appear in the center of the screen
            this.setLocationRelativeTo(null);

        }
    }
