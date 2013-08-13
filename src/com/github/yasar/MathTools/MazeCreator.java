package com.github.yasar.MathTools;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Yaşar Arabacı
 */
public class MazeCreator {

    private int mazeWidth;
    private int mazeHeight;
    // whether or not a wall is open
    private boolean[] wallRegister;
    // whether or not a room is visited
    private boolean[] roomRegister;

    private class Room {

        public int x;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
        public int y;
        private int distanceToOrigin;

        public Room(int x, int y) {
            this.x = x;
            this.y = y;
            distanceToOrigin = x * x + y * y;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", x, y);
        }

        public int id() {
            return (y * mazeWidth) + x;
        }

        private List<Room> getNeighbors() {
            List<Room> rooms = new ArrayList();
            if (x < mazeWidth - 1) {
                rooms.add(new Room(x + 1, y));
            }
            if (x > 0) {
                rooms.add(new Room(x - 1, y));
            }
            if (y < mazeHeight - 1) {
                rooms.add(new Room(x, y + 1));
            }
            if (y > 0) {
                rooms.add(new Room(x, y - 1));
            }
            return rooms;
        }

        public List<Room> nonVisitedNeighbors() {
            List<Room> nonVisited = new ArrayList<>();
            for (Room r : getNeighbors()) {
                if (roomRegister[r.id()] == false) {
                    nonVisited.add(r);
                }
            }
            return nonVisited;
        }

        public int getDistance() {
            return distanceToOrigin;
        }

        public boolean isSmaller(Room otherRoom) {
            if (getDistance() < otherRoom.getDistance()) {
                return true;
            }
            return false;
        }
    }

    public MazeCreator(int width, int height) {
        mazeWidth = width;
        mazeHeight = height;
        wallRegister = new boolean[mazeWidth * mazeHeight * 2];
        roomRegister = new boolean[mazeWidth * mazeHeight];
    }

    public String createMaze() {
        Random randGen = new Random();
        Stack<Room> stack = new Stack<>();
        Room current = new Room(randGen.nextInt(mazeWidth), randGen.nextInt(mazeHeight));
        // mark start position visited
        roomRegister[current.id()] = true;
        while (true) {
            List<Room> nonVisitedNeighbors = current.nonVisitedNeighbors();
            // cannot move from here, go back
            if (nonVisitedNeighbors.isEmpty()) {
                try {
                    current = stack.pop();
                    continue;
                } catch (EmptyStackException e) {
                    break;
                }
            }
            Room next = nonVisitedNeighbors.get(randGen.nextInt(nonVisitedNeighbors.size()));
            roomRegister[next.id()] = true;

            // open wall between current and next
            Room first = current;
            Room second = next;

            if (next.isSmaller(current)) {
                first = next;
                second = current;
            }

            // second is either at the right or bottom of first.
            if (second.getX() == first.getX() + 1) {
                wallRegister[first.id() * 2] = true;
            } else {
                wallRegister[first.id() * 2 + 1] = true;
            }

            stack.add(next);
            current = next;
        }
        // -- Generation is done, create the string
        
        StringBuilder maze = new StringBuilder();
        // upper border
        maze.append("  _");
        for (int i = 1; i < mazeWidth; i++) {
            maze.append("__");
        }

        maze.append("\n");

        for (int i = 0; i < mazeHeight; i++) {
            maze.append("|"); // left border
            for (int k = 0; k < mazeWidth; k++) {
                int id = (new Room(k, i)).id();
                String right = "|";
                String bottom = "_";
                if (wallRegister[id * 2]) {
                    right = " ";
                }
                if (wallRegister[id * 2 + 1]) {
                    bottom = " ";
                }
                // if bottom right corner
                if (k == mazeWidth - 1 && i == mazeHeight - 1) {
                    right = " ";
                }

                maze.append(bottom);
                maze.append(right);
            }
            maze.append("\n");
        }
        
        return maze.toString();
    }
}
