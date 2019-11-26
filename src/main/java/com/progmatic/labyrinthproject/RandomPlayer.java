package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer implements Player {
    @Override
    public Direction nextMove(Labyrinth l) {

        List<Direction> currDirections=l.possibleMoves();
        int rand= ThreadLocalRandom.current().nextInt(0,currDirections.size());
        return currDirections.get(rand);
    }
}
