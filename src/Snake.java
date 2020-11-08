import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 @author Hakim Bashe
 @author Rahul Rajesh
 @author Sumeet Sara
 This is the board class, it contains the grid that the snake game is played on
 The snake moves by the user pressing the arrow keys, and the snake grows if you eat any apples. There are also mines scattered across the board, if you come into contact with any of them, the game ends. The game also ends if the snake touches itself when moving
 */
//Inspired by : https://github.com/janbodnar/Java-Snake-Game
public class Snake extends JFrame {

    public Snake() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
               
        setResizable(false);
        pack();
        
        setTitle("Snake Finds You");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}
