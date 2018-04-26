package org.minions.devfund.sergio;

import java.util.Arrays;

/**
 * Main methods for game Squarelotron.
 *
 * @author Sergio Navarro
 * @since 4/20/2018
 */
public class Squarelotron {

    /**
     * Internal Array.
     */
    private int[][] squarelotron;

    /**
     * The zize of the Square.
     */
    private int size;

    /**
     * Constructor for Squarelotron.
     * Fill new Square with default numbers.
     *
     * @param n - The size of the square.
     */
    public Squarelotron(int n) {
        size = n;
        int num = 1;
        squarelotron = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squarelotron[i][j] = num++;
            }
        }
    }

    /**
     * Returns the original Squarelotron.
     *
     * @return the Squarelotron.
     */
    final int[][] getOriginalSquareArray() {
        return Arrays.copyOf(this.squarelotron, size);
    }

    /**
     * Prints the given square.
     *
     * @param squarelotron - The current square.
     * @return A String of the square.
     */
    public String showSquare(final int[][] squarelotron) {

        final String unit = "0";
        final int decimal = 10;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squarelotron[i][j] < decimal) {
                    stringBuilder.append(unit);
                }
                stringBuilder.append(squarelotron[i][j]);
                if (j != size - 1) {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }

    /**
     * Gets the number of the ring in the square.
     *
     * @return The number of rings.
     */
    public int getNumberOfRings() {
        return size % 2 == 0 ? size / 2 : size / 2 + 1;
    }

    /**
     * Makes the logic on the ring, checking if the current number is in the ring or out the ring.
     *
     * @param i    - Represent the rows.
     * @param j    - Represent the columns.
     * @param ring - The number ring to work with.
     * @return - True if the number (i,j) is only in the ring, false otherwise.
     */
    public boolean ringCheck(int i, int j, int ring) {
        // Check if the current number is in the ring
        return (i == ring - 1 || j == ring - 1 || i == size - ring || j == size - ring)
                // And the current number is not outside of the ring
                && !(i <= ring - 2 || i >= size + 1 - ring || j <= ring - 2 || j >= size + 1 - ring);
    }

    /**
     * Calls the method that flips the square upsideDown.
     *
     * @param ring - The Ring number to work with.
     * @return The processed Square.
     */
    public Squarelotron upsideDownFlip(int ring) {
        Squarelotron squarelotronResult = new Squarelotron(size);
        squarelotronResult.squarelotron = upsideDownFlipArray(ring);
        return squarelotronResult;
    }

    /**
     * Makes the square flip upsideDown.
     *
     * @param ring - The ring to work with.
     * @return The flipped square.
     */
    public int[][] upsideDownFlipArray(int ring) {
        final int[][] auxArray = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ringCheck(i, j, ring)) {
                    auxArray[i][j] = this.squarelotron[size - 1 - i][j];
                } else {
                    auxArray[i][j] = this.squarelotron[i][j];
                }
            }
        }
        return auxArray;
    }

    /**
     * Calls the method that swaps the square by main diagonal.
     *
     * @param ring - The ring to work with.
     * @return The processed Square.
     */
    public Squarelotron mainDiagonalFlip(int ring) {
        Squarelotron squarelotronResult = new Squarelotron(size);
        squarelotronResult.squarelotron = mainDiagonalFlipArray(ring);
        return squarelotronResult;
    }

    /**
     * Makes the square swap by main diagonal.
     *
     * @param ring - The ring to work whit.
     * @return The Swapped square.
     */
    public int[][] mainDiagonalFlipArray(int ring) {
        final int[][] auxArray = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ringCheck(i, j, ring)) {
                    auxArray[i][j] = this.squarelotron[j][i];
                } else {
                    auxArray[i][j] = this.squarelotron[i][j];
                }
            }
        }
        return auxArray;
    }

    /**
     * Makes the rotation to right according the number of turns.
     *
     * @param rings - The number of rings.
     * @param turns - Number of turns.
     * @return The processed Array.
     */
    public int[][] rotateEast(int turns, int rings) {
        while (turns > 0) {
            for (int i = 1; i <= rings; i++) {
                this.squarelotron = upsideDownFlipArray(i);
                this.squarelotron = mainDiagonalFlipArray(i);
            }
            turns--;
        }
        return Arrays.copyOf(this.squarelotron, size);
    }

    /**
     * Makes the rotation to left according the number of turns.
     *
     * @param rings - The number of rings.
     * @param turns - Number of turns.
     * @return - The processed Array.
     */
    public int[][] rotateWest(int turns, int rings) {
        while (turns < 0) {
            for (int i = 1; i <= rings; i++) {
                this.squarelotron = mainDiagonalFlipArray(i);
                this.squarelotron = upsideDownFlipArray(i);
            }
            turns++;
        }
        return Arrays.copyOf(this.squarelotron, size);
    }

    /**
     * Makes the rotation according the value of the numberOfTurns, left or right.
     *
     * @param numberOfTurns - The number of turns.
     */
    public void rotateRight(int numberOfTurns) {
        if (numberOfTurns >= 0) {
            rotateEast(numberOfTurns, getNumberOfRings());
            return;
        }
        rotateWest(numberOfTurns, getNumberOfRings());
    }
}