package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private final static String FILENAME = "labyrinth1.txt";
    private Map<Coordinate, CellType> labyrinth;
    private int width;
    private int height;
    private Coordinate playerPosition;
    private Coordinate start;
    private Coordinate end;


    public LabyrinthImpl() {
        labyrinth = new HashMap<>();
        height = -1;
        width = -1;
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            width = Integer.parseInt(sc.nextLine());
            height = Integer.parseInt(sc.nextLine());

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth.put(new Coordinate(ww, hh), CellType.WALL);
                            break;
                        case 'E':
                            end = new Coordinate(ww, hh);
                            labyrinth.put(end, CellType.END);
                            break;
                        case 'S':
                            start = new Coordinate(ww, hh);
                            labyrinth.put(start, CellType.START);
                            playerPosition = start;
                            break;
                        default:
                            labyrinth.put(new Coordinate(ww, hh), CellType.EMPTY);
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0 || c.getCol() >= width || c.getRow() >= height) {
            throw new CellException(c, "Nem megfelelő koordináták");
        }
        return labyrinth.get(c);
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        labyrinth = new HashMap<>();
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getRow() >= height || c.getCol() >= width || c.getRow() < 0 || c.getCol() < 0) {
            throw new CellException(c, "A megadott indexek nem megfelelőek");
        }
        labyrinth.put(c, type);
        if (type == CellType.START) {
            start = c;
            playerPosition = c;
        }
        if (type == CellType.END) {
            end = c;
        }

    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        return playerPosition.equals(end);
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> possibleMoves = new ArrayList<>();

        int currRow = playerPosition.getRow();
        int currCol = playerPosition.getCol();

        if (labyrinth.get(new Coordinate(currCol + 1, currRow)) == CellType.EMPTY ||
                labyrinth.get(new Coordinate(currCol + 1, currRow)) == CellType.END) {
            possibleMoves.add(Direction.EAST);
        }
        if (labyrinth.get(new Coordinate(currCol - 1, currRow)) == CellType.EMPTY ||
                labyrinth.get(new Coordinate(currCol - 1, currRow)) == CellType.END
        ) {
            possibleMoves.add(Direction.WEST);
        }
        if (labyrinth.get(new Coordinate(currCol, currRow + 1)) == CellType.EMPTY ||
                labyrinth.get(new Coordinate(currCol, currRow + 1)) == CellType.END) {
            possibleMoves.add(Direction.SOUTH);
        }
        if (labyrinth.get(new Coordinate(currCol, currRow - 1)) == CellType.EMPTY ||
                labyrinth.get(new Coordinate(currCol, currRow - 1)) == CellType.END ) {
            possibleMoves.add(Direction.NORTH);
        }

        return possibleMoves;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {

        Coordinate cellEast = new Coordinate(playerPosition.getCol() + 1, playerPosition.getRow());
        Coordinate cellWest = new Coordinate(playerPosition.getCol() - 1, playerPosition.getRow());
        Coordinate cellNorth = new Coordinate(playerPosition.getCol(), playerPosition.getRow() - 1);
        Coordinate cellSouth = new Coordinate(playerPosition.getCol(), playerPosition.getRow() + 1);



        switch (direction) {
            case EAST:
                if(labyrinth.get(cellEast)!=CellType.EMPTY&&labyrinth.get(cellEast)!=CellType.END){
                    throw new InvalidMoveException("A játékos rossz helyre próbál lépni!");
                }
                playerPosition = cellEast;
                break;
            case WEST:
                if(labyrinth.get(cellWest)!=CellType.EMPTY&&labyrinth.get(cellWest)!=CellType.END){
                    throw new InvalidMoveException("A játékos rossz helyre próbál lépni!");
                }
                playerPosition = cellWest;
                break;
            case NORTH:
                if(labyrinth.get(cellNorth)!=CellType.EMPTY&&labyrinth.get(cellNorth)!=CellType.END){
                    throw new InvalidMoveException("A játékos rossz helyre próbál lépni!");
                }
                playerPosition = cellNorth;
                break;
            case SOUTH:
                if(labyrinth.get(cellSouth)!=CellType.EMPTY&&labyrinth.get(cellSouth)!=CellType.END){
                    throw new InvalidMoveException("A játékos rossz helyre próbál lépni!");
                }
                playerPosition = cellSouth;
        }

    }

    public Map<Coordinate, CellType> getLabyrinth() {
        return labyrinth;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }


}
