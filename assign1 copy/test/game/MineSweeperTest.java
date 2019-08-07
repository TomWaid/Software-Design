package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static game.MineSweeper.CellState.*;
import static org.junit.jupiter.api.Assertions.*;
import static game.MineSweeper.GameStatus.*;
public class MineSweeperTest {

    private MineSweeper mineSweeper;

    @BeforeEach
    public void setup() {
        mineSweeper = new MineSweeper();
    }

    @Test
    public void canary() {
        assertTrue(true);
    }

    @Test
    public void exposeAnUnExposedCell() {
        mineSweeper.expose(3, 2);

        assertEquals(EXPOSED, mineSweeper.getCellStatus(3, 2));
    }

    @Test
    public void exposeAnExposedCell() {
        mineSweeper.expose(5, 7);
        mineSweeper.expose(5, 7);

        assertEquals(EXPOSED, mineSweeper.getCellStatus(5, 7));
    }

    @Test
    public void exposeOutsideRange() {
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.expose(-3,  4)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.expose(14,  4)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.expose(5,  -4)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.expose(4,  15))
        );
    }

    @Test
    public void sealAnUnexposedCell() {
        mineSweeper.toggleSeal(5,7);

        assertEquals(SEALED, mineSweeper.getCellStatus(5, 7));
    }

    @Test
    public void unsealASealedCell() {
        mineSweeper.toggleSeal(5, 7);
        mineSweeper.toggleSeal(5,7);

        assertEquals(UNEXPOSED, mineSweeper.getCellStatus(5, 7));
    }

    @Test
    public void userTriesToExposeASealedCell() {
        mineSweeper.toggleSeal(5, 7);
        mineSweeper.expose(5, 7);

        assertEquals(SEALED, mineSweeper.getCellStatus(5, 7));
    }

    @Test
    public void userTriestoSealAnExposedCell() {
        mineSweeper.expose(5, 7);
        mineSweeper.toggleSeal(5, 7);

        assertEquals(EXPOSED, mineSweeper.getCellStatus(5, 7));
    }

    @Test
    public void sealOutsideRange() {
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.toggleSeal(-3,  4)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.toggleSeal(14,  4)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.toggleSeal(5,  -4)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> mineSweeper.toggleSeal(4,  15)));
    }

    @Test
    public void exposeCallsExposeNeighbors(){
        boolean[] exposeNeighborsCalled = new boolean[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void exposeNeighbors(int row, int column) {
                exposeNeighborsCalled[0] = true;
            }
        };

        mineSweeper.expose(3, 5);

        assertTrue(exposeNeighborsCalled[0]);
    }


    @Test
    public void exposeCalledOnAlreadyExposedCellDoesNotCallExposeNeighbors(){
        boolean[] exposeNeighborsCalled = new boolean[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void exposeNeighbors(int row, int column) {
                exposeNeighborsCalled[0] = true;
            }
        };

        mineSweeper.expose(5, 7);
        exposeNeighborsCalled[0]=false;
        mineSweeper.expose(5, 7);

        assertFalse(exposeNeighborsCalled[0]);

    }

    @Test
    public void exposeCalledOnSealedCellDoesNotCallExposeNeighbors(){
        boolean[] exposeNeighborsCalled = new boolean[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void exposeNeighbors(int row, int column) {
                exposeNeighborsCalled[0] = true;
            }
        };

        mineSweeper.toggleSeal(5, 7);
        mineSweeper.expose(5, 7);

        assertFalse(exposeNeighborsCalled[0]);
    }

    @Test
    public void exposeNeighborsCallsExposeOnEightNeighbors(){
        int[] count = new int[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void expose(int row, int column) {
                count[0]=count[0]+1;
            }
        };
        mineSweeper.exposeNeighbors(4, 7);

        assertEquals(8, count[0]);
    }

    @Test
    public void exposeNeighborsOnTopLeftCellCallsExposeOnlyOnExistingCells(){
        int[] count = new int[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void expose(int row, int column) {
                count[0] = count[0] + 1;
            }
        };

        mineSweeper.exposeNeighbors(0, 0);

        assertEquals(3, count[0]);
    }

    @Test
    public void exposeNeighborsOnBottomRightCellCallsExposeOnlyOnExistingCells(){
        int[] count = new int[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void expose(int row, int column) {
                count[0] = count[0] + 1;
            }
        };

        mineSweeper.exposeNeighbors(9, 9);

        assertEquals(3, count[0]);
    }

    @Test
    public void checkIfMineExistsAtNonMinedCell(){
        assertEquals(false, mineSweeper.isMineAt(3,2));
    }

    @Test
    public void checkIfMineExistsAtMinedCell(){
        mineSweeper.setMine(3, 2);

        assertEquals(true, mineSweeper.isMineAt(3, 2));
    }

    @Test
    public void checkCellStatusofMinedCellAfterSealing(){
        mineSweeper.setMine(3, 2);
        mineSweeper.toggleSeal(3, 2);

        assertTrue(mineSweeper.isMineAt(3, 2));
        assertEquals(SEALED, mineSweeper.getCellStatus(3, 2));
    }

    @Test
    public void checkMineOutsideRange() {
        assertAll(
                ()-> assertEquals(false, mineSweeper.isMineAt(-1, 4)),
                ()-> assertEquals(false, mineSweeper.isMineAt(10, 5)),
                ()-> assertEquals(false, mineSweeper.isMineAt(5, 1)),
                ()-> assertEquals(false, mineSweeper.isMineAt(7, 10)));
    }

    @Test
    public void exposeCalledOnAdjacentCellDoesNotCallExposeNeighbors(){
        boolean[] exposeNeighborsCalled = new boolean[1];

        MineSweeper mineSweeper = new MineSweeper() {
            public void exposeNeighbors(int row, int column) {
                exposeNeighborsCalled[0] = true;
            }
        };
        mineSweeper.setMine(5, 7);
        mineSweeper.expose(5, 6);

        assertFalse(exposeNeighborsCalled[0]);
    }

    @Test
    public void checkAdjacentMineCountOfCellWhichIsNotAroundMinedCell(){
        assertAll(
                ()->assertEquals(0, mineSweeper.adjacentMinesCount(4, 6)),
                ()->assertEquals(0, mineSweeper.adjacentMinesCount(0, 9)),
                ()->assertEquals(0, mineSweeper.adjacentMinesCount(9, 0))
        );
    }

    @Test
    public void checkAdjacentMinesCountOnMinedCell(){
        mineSweeper.setMine(3, 4);

        assertEquals(0, mineSweeper.adjacentMinesCount(3, 4));
    }

    @Test
    public void checkAdjacentMinesCountOfCellWithOneMineAroundIt(){
        mineSweeper.setMine(3, 4);

        assertEquals(1, mineSweeper.adjacentMinesCount(3, 5));
    }

    @Test
    public void checkAdjacentMinesCountOfCellWithTwoMinesAroundIt(){
        mineSweeper.setMine(3, 4);
        mineSweeper.setMine(2, 6);

        assertEquals(2, mineSweeper.adjacentMinesCount(3, 5));
    }

    @Test
    public void checkAdjacentMinesCountOfTopLeftCellWithOneMineAroundIt(){
        mineSweeper.setMine(0, 1);

        assertEquals(1, mineSweeper.adjacentMinesCount(0,0));
    }

    @Test
    public void checkAdjacentMinesCountOfBottomRightCellWithOneMineAroundIt(){
        mineSweeper.setMine(9, 8);

        assertEquals(1, mineSweeper.adjacentMinesCount(9, 9));
    }

    @Test
    public void checkGameStatusIsInProgress(){
        assertEquals(INPROGRESS, mineSweeper.getGameStatus());
    }

    @Test
    public void checkGameStatusIsLostWhenMinedCellIsExposed(){
        mineSweeper.setMine(0, 0);
        mineSweeper.expose(0, 0);

        assertEquals(LOST, mineSweeper.getGameStatus());
    }

    @Test
    public void gameInProgressAfterAllMinesSealedButOtherCellsUnexposed() {
        mineSweeper.setMine(5, 7);
        mineSweeper.toggleSeal(5, 7);

        assertEquals(INPROGRESS, mineSweeper.getGameStatus());
    }

    @Test
    public void gameInProgressAfterAllMinesSealedButEmptyCellSealedToo() {
        mineSweeper.setMine(5, 7);
        mineSweeper.toggleSeal(5, 7);
        mineSweeper.toggleSeal(7, 2);

        assertEquals(INPROGRESS, mineSweeper.getGameStatus());
    }

    @Test
    public void gameInProgressAfterAllMinesSealedButAdjacentCellUnexposed() {
        mineSweeper.setMine(5, 7);
        mineSweeper.toggleSeal(5, 7);

        assertEquals(UNEXPOSED, mineSweeper.getCellStatus(6, 7));
        assertEquals(INPROGRESS, mineSweeper.getGameStatus());
    }

    @Test
    public void gameWonAfterAllMinesSealedAndAllOtherCellsExposed() {
        mineSweeper.setMines(0);
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(mineSweeper.isMineAt(i,j)){
                    mineSweeper.toggleSeal(i,j);
                }
                else{
                    mineSweeper.expose(i,j);
                }
            }
        }

        assertEquals(WON, mineSweeper.getGameStatus());
    }

    @Test
    public void checkIfTenMinesAreSet(){
        int totalMines= mineSweeper.setMines(0);

        assertEquals(10, totalMines);
    }

    @Test
    public void settingMinesWithDifferentSeedsResultsInAtleastOneDifferentMinePositions(){
        boolean differentMineFound = false;
        mineSweeper.setMines(0);
        MineSweeper secondMineSweeper = new MineSweeper();
        secondMineSweeper.setMines(1);

        outerloop:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mineSweeper.isMineAt(i, j) != secondMineSweeper.isMineAt(i, j)) {
                    differentMineFound = true;
                    break outerloop;
                }
            }
        }

        assertTrue(differentMineFound);

    }
}