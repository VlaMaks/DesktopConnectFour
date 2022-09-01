package four;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectFour extends JFrame {
    private final int countRows = 6;
    private final int countCols = 7;
    private final Color baseColor = Color.pink;
    private final Color winColor = Color.green;
    private final Cell[][] matrixCells = new Cell[countRows][countCols];
    private final boolean[] flag = {false};
    private final boolean[] isWin = {false};

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setTitle("Connect Four");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(countRows, countCols));

        Map<Character, List<Cell>> cells = addCells(jPanel);
        addListenerToCells(cells);
        add(jPanel);

        ButtonReset buttonReset = new ButtonReset();
        buttonReset.setName("ButtonReset");
        buttonReset.setSize(40, 20);
        buttonReset.setText("Reset");
        buttonReset.addActionListener(e-> {
            clearCells(cells);
        });

        add(buttonReset);

        setVisible(true);
    }

    private void addListenerToCells(Map<Character, List<Cell>> map) {
        List<Cell> allCells = new ArrayList<>();


        for (Map.Entry<Character, List<Cell>> entry: map.entrySet()) {
            allCells.addAll(map.get(entry.getKey()));
        }

        for (Cell cell : allCells) {
            char key = cell.getName().charAt(cell.getName().length() - 2);
            cell.addActionListener(e -> {
                    Cell findCell = Cheker.getFirstFreeCell(map.get(key));
                    if (findCell != null && !isWin[0]) {

                        if (!flag[0]) {
                            findCell.setText("X");
                        } else {
                            findCell.setText("O");
                        }

                        flag[0] = !flag[0];
                        isWin[0] = Cheker.isWin(matrixCells, winColor);
                        cell.setFocusPainted(false);
                    }

                });
        }
    }

    private Map<Character, List<Cell>> addCells(JPanel jPanel) {
        int num = 6;

        Map<Character, List<Cell>> map = new HashMap<>();
        for (char s = 'A'; s < 'A' + countCols; s++) {
            map.put(s, new ArrayList<>(countRows));
        }


        for (int i = 0; i < countRows; i++) {
            char symb = 'A';
            for (int j = 0; j < countCols; j++) {
                Cell cell = new Cell();
                cell.setName(String.format("%s%s", symb, num));
                map.get(symb).add(cell);
                cell.setBackground(baseColor);
                cell.setText(" ");
                symb++;
                matrixCells[i][j] = cell;



                jPanel.add(cell);
            }
            num--;
        }
        return map;
    }

    private void clearCells(Map<Character, List<Cell>> cells) {
        flag[0] = false;
        isWin[0] = false;
        for (var allCells : cells.values()) {
            for (Cell cell : allCells) {
                cell.setText(" ");
                cell.setBackground(baseColor);
            }
        }
    }

}
