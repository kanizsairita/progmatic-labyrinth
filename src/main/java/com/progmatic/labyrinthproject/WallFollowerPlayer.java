package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public class WallFollowerPlayer implements Player {



    private List<Direction> directionOrder;
    private Direction playerFace=Direction.NORTH;

    public WallFollowerPlayer() {
        directionOrder=new ArrayList<>();
        directionOrder.add(Direction.NORTH);
        directionOrder.add(Direction.EAST);
        directionOrder.add(Direction.SOUTH);
        directionOrder.add(Direction.WEST);
    }

    @Override
    public Direction nextMove(Labyrinth l) throws CellException {
        List<Direction> currDirections=l.possibleMoves();

        do {
            if(hasWallOnTheRight(l)&&!hasWallInFront(l)){
                return playerFace;
            }else if(!hasWallOnTheRight(l)){
                turnRight();
                return playerFace;
            }else {
                turnRight();
                turnRight();
                turnRight();
            }
        } while (true);

    }



    private void turnRight(){
        for (int i=0; i<directionOrder.size(); i++) {
            if(directionOrder.get(i)==playerFace){
                playerFace=directionOrder.get((i+1)%4);
                break;
            }
        }
    }

    private boolean hasWallOnTheRight(Labyrinth l) throws CellException {

        int currRow=l.getPlayerPosition().getRow();
        int currCol=l.getPlayerPosition().getCol();

        if(playerFace==Direction.NORTH){
             return l.getCellType(new Coordinate(currCol+1, currRow))== CellType.WALL;
        }
        else if(playerFace==Direction.EAST){
            return l.getCellType(new Coordinate(currCol, currRow+1))== CellType.WALL;
        }
        else if(playerFace==Direction.SOUTH){
            return l.getCellType(new Coordinate(currCol-1, currRow))== CellType.WALL;
        }
        else{
            return l.getCellType(new Coordinate(currCol, currRow-1))== CellType.WALL;
        }

    }

    private boolean hasWallInFront(Labyrinth l) throws CellException {
        int currRow=l.getPlayerPosition().getRow();
        int currCol=l.getPlayerPosition().getCol();

        if(playerFace==Direction.NORTH){
            return l.getCellType(new Coordinate(currCol, currRow-1))== CellType.WALL;
        }
        else if(playerFace==Direction.EAST){
            return l.getCellType(new Coordinate(currCol+1, currRow))== CellType.WALL;
        }
        else if(playerFace==Direction.SOUTH){
            return l.getCellType(new Coordinate(currCol, currRow+1))== CellType.WALL;
        }
        else{
            return l.getCellType(new Coordinate(currCol-1, currRow))== CellType.WALL;
        }
    }

}
