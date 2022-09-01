package four;

import javax.swing.*;

public class Cell extends JButton {
    @Override
    public void setName(String name) {
        super.setName("Button" + name);
    }
}
