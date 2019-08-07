package game.ui;

import game.MineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class MineSweeperBoard extends JFrame {
    private static final int SIZE=10;
    MineSweeper mineSweeper;
    MineSweeperCell cells[][];

    @Override
    protected void frameInit(){
        super.frameInit();
        mineSweeper=new MineSweeper();
        cells = new MineSweeperCell[SIZE][SIZE];
        setLayout(new GridLayout(10,10));
        for(int i=0; i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                MineSweeperCell cell=new MineSweeperCell(i,j);
                cells[i][j] = cell;
                getContentPane().add(cell);
                cell.addMouseListener(new CellClickedHandler());
            }
        }
        mineSweeper.setMines((int) (new Date().getTime()/1000));
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        JFrame frame= new MineSweeperBoard();
        frame.setTitle("MineSweeper Game");
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    private class CellClickedHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

            MineSweeperCell cell = (MineSweeperCell) mouseEvent.getSource();

            if (SwingUtilities.isRightMouseButton(mouseEvent) && mineSweeper.getCellStatus(cell.row, cell.column) != MineSweeper.CellState.EXPOSED) {
                executeRightClick(cell);
            }

            if (SwingUtilities.isLeftMouseButton(mouseEvent) && mineSweeper.getCellStatus(cell.row, cell.column) != MineSweeper.CellState.SEALED) {
                executeLeftClick(cell);
            }

            updateBoard(cell);

            if(mineSweeper.getGameStatus()== MineSweeper.GameStatus.WON){
                ImageIcon victoryIcon = new ImageIcon("src/victory.jpg");
                JOptionPane.showMessageDialog(null, "You won the game!", "GAME WON",1,victoryIcon);
                System.exit(0);
            } else if (mineSweeper.getGameStatus() == MineSweeper.GameStatus.LOST) {
                ImageIcon lostIcon = new ImageIcon("src/lost.jpg");
                JOptionPane.showMessageDialog(null, "You lost the game!", "GAME LOST",1,lostIcon);
                System.exit(0);
            }
        }
    }

    public void executeRightClick(MineSweeperCell cell){
        if (cell.getIcon() == null) {
            ImageIcon flagIcon = new ImageIcon("src/Flag.png");
            cell.setIcon(flagIcon);
        } else {
            cell.setIcon(null);
        }
        mineSweeper.toggleSeal(cell.row, cell.column);
    }

    public void updateBoard(MineSweeperCell cell) {
        MineSweeper.CellState cellState = mineSweeper.getCellStatus(cell.row, cell.column);
        if (mineSweeper.isMineAt(cell.row, cell.column) && cellState == MineSweeper.CellState.EXPOSED) {
            ImageIcon mineIcon = new ImageIcon("src/mine.png");
            cell.setIcon(mineIcon);

            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    if(mineSweeper.isMineAt(i,j)){
                        if(mineSweeper.getCellStatus(i,j)== MineSweeper.CellState.SEALED){
                            ImageIcon minecrossed = new ImageIcon("src/minecrossed.png");
                            MineSweeperCell mineCell= cells[i][j];
                            mineCell.setIcon(minecrossed);
                        }
                        else{
                            MineSweeperCell mineCell= cells[i][j];
                            mineCell.setIcon(mineIcon);
                        }

                    }
                }
            }
        }else{
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!mineSweeper.isMineAt(i,j)) {
                        if (mineSweeper.getCellStatus(i, j) == MineSweeper.CellState.EXPOSED) {
                            cells[i][j].setBackground(Color.GREEN);
                            int adjMineCount = mineSweeper.adjacentMinesCount(i, j);
                            if (adjMineCount != 0) {
                                cells[i][j].setFont(new Font("Serif", Font.BOLD, 20));
                                cells[i][j].setText(Integer.toString(adjMineCount));
                            }
                        }
                    }
                }
            }
        }
    }

    public void executeLeftClick(MineSweeperCell cell){
        mineSweeper.expose(cell.row, cell.column);
    }
}
