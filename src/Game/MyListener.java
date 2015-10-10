package Game;

import java.awt.event.KeyEvent;

/**
 * Created by sniken on 10.10.2015.
 */
public interface MyListener {

    void keyMovement(KeyEvent e);  // Callback to another class. e.g Game.java
}
