import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;

public class Main {
    enum StartInput {
        TOPLEFT,
        TOPRIGHT,
        BOTTOMRIGHT,
        BOTTOMLEFT
    }
    public static int input;

    public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }

    public static Square validateStartSquare(Scanner keyboard, Square[][] board, Square goalSquare) {
        System.out.println("\nEnter where you wish to start: ");
        String inputStartCoords = keyboard.nextLine();

        int startSquareX = 0;
        int startSquareY = 0;

        while (!(isInEnum(inputStartCoords.replaceAll("\\s+","").toUpperCase(), StartInput.class))) {
            System.out.println("\nInvalid input. Please try again: ");
            inputStartCoords = keyboard.nextLine();
        }

        StartInput startCoords = StartInput.valueOf(inputStartCoords.replaceAll("\\s+","").toUpperCase());

        switch(startCoords) {
            case TOPLEFT: // already initialized
                break;
            case TOPRIGHT: // x already initialized
                startSquareY = input - 1;
                break;
            case BOTTOMRIGHT:
                startSquareX = input - 1;
                startSquareY = input - 1;
                break;
            case BOTTOMLEFT:  // y already initialized
                startSquareX = input - 1;
        }

        //Validate input: Ensure start square isn't the same as goal square (necessary condition)
        if ((startSquareX == goalSquare.getXCoord()) && (startSquareY == goalSquare.getYCoord())) {
            System.out.println("\nYou cannot start on the goal. Please try again.");
            return board[startSquareX][startSquareY];
        }

        return board[startSquareX][startSquareY];
    }

    public static boolean recursiveMagicBoard(Square[][] board, Square current, Square goal, int visitedSquaresCount) throws EndGameException {
        if (current.getXCoord() == goal.getXCoord() && current.getYCoord() == goal.getYCoord()) { // goal is reached
//            System.out.println("Goal has been reached!");
            throw new EndGameException();
        }

        if ((!current.getMoveNorth().isEnabled() && !current.getMoveEast().isEnabled() && !current.getMoveSouth().isEnabled() && !current.getMoveWest().isEnabled()) // all moves are out of bounds
            || current.isVisited() // square has already been visited
            || visitedSquaresCount == Math.pow(input, 2)) { // all squares have been visited
            System.out.println("No path to goal here.");
            return false;
        }

        current.setVisited(true);

        if (current.getMoveNorth().isEnabled()) {
            System.out.println("Move North from (" + current.getXCoord() + ", " + current.getYCoord() + ")" + ": We are now on (" + (current.getXCoord() - current.getSquareValue()) + ", " + current.getYCoord() + ")");
            recursiveMagicBoard(board, board[current.getXCoord() - current.getSquareValue()][current.getYCoord()], goal, visitedSquaresCount++);
        }
        if (current.getMoveEast().isEnabled()) {
            System.out.println("Move East from (" + current.getXCoord() + ", " + current.getYCoord() + ")" + ": We are now on (" + current.getXCoord() + ", " + (current.getYCoord() + current.getSquareValue()) + ")");
            recursiveMagicBoard(board, board[current.getXCoord()][current.getYCoord() + current.getSquareValue()], goal, visitedSquaresCount++);
        }
        if (current.getMoveSouth().isEnabled()) {
            System.out.println("Move South from (" + current.getXCoord() + ", " + current.getYCoord() + ")" + ": We are now on (" + (current.getXCoord() + current.getSquareValue()) + ", " + current.getYCoord() + ")");
            recursiveMagicBoard(board, board[current.getXCoord() + current.getSquareValue()][current.getYCoord()], goal, visitedSquaresCount++);
        }
        if (current.getMoveWest().isEnabled()) {
            System.out.println("Move West from (" + current.getXCoord() + ", " + current.getYCoord() + ")" + ":  We are now on (" + current.getXCoord() + ", " + (current.getYCoord() - current.getSquareValue()) + ")");
            recursiveMagicBoard(board, board[current.getXCoord()][current.getYCoord() - current.getSquareValue()], goal, visitedSquaresCount++);
        }

        return false;
    }

    public static boolean iterativeMagicBoard(Square[][] board, Queue<Square> toVisit, Square goal) {
        int visitedSquaresCount = 0;

        while (!toVisit.isEmpty() && visitedSquaresCount != Math.pow(input, 2)) {
            Square current = toVisit.remove();

            if (current.getXCoord() == goal.getXCoord() && current.getYCoord() == goal.getYCoord()) {
                System.out.println("Goal has been reached!");
                return true;
            }

            if (current.isVisited()) {  // square has already been visited: skip the loop
                System.out.println("No path to goal here.");
            } else {
                visitedSquaresCount++;
                current.setVisited(true);

                if (current.getMoveNorth().isEnabled()) {
                    System.out.println("Added (" + (current.getXCoord() - current.getSquareValue()) + ", " + current.getYCoord() + ") to stack");
                    if (!toVisit.contains(board[current.getXCoord() - current.getSquareValue()][current.getYCoord()])) {
                        toVisit.add(board[current.getXCoord() - current.getSquareValue()][current.getYCoord()]);
                    }
                }
                if (current.getMoveEast().isEnabled()) {
                    System.out.println("Added (" + current.getXCoord() + ", " + (current.getYCoord() + current.getSquareValue()) + ") to stack");
                    if (!toVisit.contains(board[current.getXCoord()][current.getYCoord() + current.getSquareValue()])) {
                        toVisit.add(board[current.getXCoord()][current.getYCoord() + current.getSquareValue()]);
                    }
                }
                if (current.getMoveSouth().isEnabled()) {
                    System.out.println("Added (" + (current.getXCoord() + current.getSquareValue()) + ", " + current.getYCoord() + ") to stack");
                    if (!toVisit.contains(board[current.getXCoord() + current.getSquareValue()][current.getYCoord()])) {
                        toVisit.add(board[current.getXCoord() + current.getSquareValue()][current.getYCoord()]);
                    }
                }
                if (current.getMoveWest().isEnabled()) {
                    System.out.println("Added (" +  current.getXCoord() + ", " + (current.getYCoord() - current.getSquareValue()) + ") to stack");
                    if (!toVisit.contains(board[current.getXCoord()][current.getYCoord() - current.getSquareValue()])) {
                        toVisit.add(board[current.getXCoord()][current.getYCoord() - current.getSquareValue()]);
                    }
                }
            }
        }

        System.out.println("No path have been found.");
        return false;
    }

    public static void main(String[] args) {
        //Initializations
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter desired Magic Board size between 5 and 20: ");
        input = keyboard.nextInt();

        //Validate input
        while (input > 20 || input < 5) {
            System.out.println("\nInvalid input. Please try again: ");
            input = keyboard.nextInt();
        }

        //Placing goal square
        System.out.println("\nEnter desired coordinates for goal square (row,col): ");
        String goalSquareCoords = keyboard.next();
        keyboard.nextLine(); //consume newline left-over by next()
        Square goalSquare = new Square(Integer.parseInt(goalSquareCoords.substring(0, goalSquareCoords.indexOf(','))) - 1, Integer.parseInt(goalSquareCoords.substring(goalSquareCoords.indexOf(',') + 1, goalSquareCoords.length())) - 1, 0);

        //Magic Board array
        int[][] magicBoardInit = new int[input][input];
        Square[][] magicBoard = new Square[input][input];

        //Initialize board
        magicBoard[goalSquare.getXCoord()][goalSquare.getYCoord()] = new Square(goalSquare.getXCoord(), goalSquare.getYCoord(), 0);

        for (int i = 0; i < input; i++) { //row
            for (int j = 0; j < input; j++) { //column
                if (!(i == (goalSquare.getXCoord()) && j == (goalSquare.getYCoord()))) { // if not already taken by goal square
                    magicBoard[i][j] = new Square(i, j, ThreadLocalRandom.current().nextInt(1, input));
                }
            }
        }

        //Display board
        System.out.println("\nMagic Board: ");
        for (int i = 0; i < input; i++) { //row
            for (int j = 0; j < input; j++) { //column
                System.out.print(magicBoard[i][j].getSquareValue() + "\t");
            }
            System.out.print("\n");
        }

        // Initialize starting point
        Square startSquare = validateStartSquare(keyboard, magicBoard, goalSquare);
        boolean validateStart = magicBoard[startSquare.getXCoord()][startSquare.getYCoord()] == magicBoard[goalSquare.getXCoord()][goalSquare.getYCoord()];

        while (validateStart) {
            startSquare = validateStartSquare(keyboard, magicBoard, goalSquare);
            validateStart = magicBoard[startSquare.getXCoord()][startSquare.getYCoord()] == magicBoard[goalSquare.getXCoord()][goalSquare.getYCoord()];
        }

        System.out.println();

        // RECURSION
        try {
            recursiveMagicBoard(magicBoard, startSquare, goalSquare, 0);
        } catch (EndGameException e) {
            System.out.println("Goal has been reached!");
        }

        // ITERATION
        /*Queue<Square> newlyVisitedSquares = new LinkedList<>();
        newlyVisitedSquares.add(startSquare);
        iterativeMagicBoard(magicBoard, newlyVisitedSquares, goalSquare);*/
    }
}
