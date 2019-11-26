package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsciousPlayer implements Player {

    /*
    Sajnos sok sebből vérzik ez a megoldás, de azért felrakom, ha már ennyit gépeltem. Nem volt több időm befejezni.
     */

    private Labyrinth l;
    private List<Coordinate> strategyCells;
    private List<Direction> strategy;
    private int moveCounter;
    private Map<Coordinate, Integer> rightCells = new HashMap<>();
    private Coordinate start;
    private Coordinate end;

    public ConsciousPlayer() {
        strategy = new ArrayList<>();
        moveCounter = -1;
    }

    @Override
    public Direction nextMove(Labyrinth l) throws CellException {

        if (moveCounter == -1) {
            this.l = l;
            start = ((LabyrinthImpl) l).getStart();
            end = ((LabyrinthImpl) l).getEnd();
            planStrategy();
            moveCounter++;
        }
        try {
            return strategy.get(moveCounter++);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("A játékos már beért a célba!");
        }
        return null;
    }


    private void planStrategy() throws CellException {

        getRightCells();
        countNumbersOnRightCells();
        makeTheStrategy();
        countDirections();
    }

    private void countDirections() {
        for (Coordinate cell : strategyCells) {

            //TODO ezt nem tudtam befejezni



        }


    }

    private void makeTheStrategy() throws CellException {
        strategyCells.add(end);
        Coordinate lastCell=end;
        do {
            ArrayList<Coordinate> neighbourCells =getNeighbourCells(lastCell);
            for (Coordinate cell : neighbourCells) {
                if(rightCells.get(cell)==rightCells.get(lastCell)-1){
                    strategyCells.add(cell);
                    lastCell=cell;
                    break;
                }
            }
        } while (lastCell!=start);


    }

    private void countNumbersOnRightCells() throws CellException {
        Coordinate previousCell = start;
        do {
            ArrayList<Coordinate> neighbourCells = getNeighbourCells(previousCell);
            for (Coordinate cell : neighbourCells) {
                if (rightCells.get(cell) == -1 || rightCells.get(previousCell) + 1 < rightCells.get(cell)) {
                    rightCells.put(cell, rightCells.get(previousCell) + 1);
                    previousCell=cell;
                }
                // TODO ez itt sajnos hibás, lehet, hogy rekurzívan kellett volna...? Nem tudtam megoldani, hogy
                //  hogyan tudom beállítani a következő előző cellát
            }
        } while (doesRightCellsContainNegative());
    }

    private boolean doesRightCellsContainNegative() {
        for (Map.Entry<Coordinate, Integer> cell : rightCells.entrySet()) {
            if (cell.getValue() < 0)
                return true;
        }
        return false;
    }

    private void getRightCells() {
        Map<Coordinate, CellType> labCells = ((LabyrinthImpl) l).getLabyrinth();
        Map<Coordinate, Integer> rightCells = new HashMap<>();
        for (Map.Entry<Coordinate, CellType> cell : labCells.entrySet()) {
            if (cell.getValue() != CellType.WALL) {
                rightCells.put(cell.getKey(), -1);
            }
        }
        rightCells.put(start, 0);
        this.rightCells = rightCells;
    }

    private ArrayList<Coordinate> getNeighbourCells(Coordinate c) throws CellException {

        ArrayList<Coordinate> neighbours = new ArrayList<>();

        int currRow = c.getRow();
        int currCol = c.getCol();

        try {
            if (l.getCellType(new Coordinate(currCol + 1, currRow)) == CellType.EMPTY ||
                    (l.getCellType(new Coordinate(currCol + 1, currRow)) == CellType.END)) {
                neighbours.add(new Coordinate(currCol + 1, currRow));
            }
        } catch (CellException e) {

        }
        try {
            if (l.getCellType(new Coordinate(currCol - 1, currRow)) == CellType.EMPTY ||
                    l.getCellType(new Coordinate(currCol - 1, currRow)) == CellType.END
            ) {
                neighbours.add(new Coordinate(currCol - 1, currRow));
            }
        } catch (CellException e) {

        }
        try {
            if (l.getCellType(new Coordinate(currCol, currRow + 1)) == CellType.EMPTY ||
                    l.getCellType(new Coordinate(currCol, currRow + 1)) == CellType.END) {
                neighbours.add(new Coordinate(currCol, currRow + 1));
            }
        } catch (CellException e) {

        }
        try {
            if (l.getCellType(new Coordinate(currCol, currRow - 1)) == CellType.EMPTY ||
                    l.getCellType(new Coordinate(currCol, currRow - 1)) == CellType.END) {
                neighbours.add(new Coordinate(currCol, currRow - 1));
            }
        } catch (CellException e) {

        }

        return neighbours;
    }


}
