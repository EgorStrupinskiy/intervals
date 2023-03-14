/**

 The Intervals class provides methods for interval construction and interval identification.
 */
package com.innowise.intervals;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Intervals {
    /**
     * The map SEMITONES contains a set of notes with their semitone numbers.
     */
     private static final Map<String, Integer> SEMITONES;

     /**
     * The array NOTES contains all the notes that can be used.
     */
    private static final String[] NOTES = {"C", "D", "E", "F", "G", "A", "B"};

    /**
     * NOTES_COUNT represents the count of all the notes that can be used.
     */
    private static final int NOTES_COUNT = 7;

    /**
     * SEMITONES_COUNT represents the count of all semitones.
     */
    private static final int SEMITONES_COUNT = 12;

    /**
     * The map INTERVALS contains a set of intervals with their corresponding semitone distances.
     */
    private static final Map<String, Integer> INTERVALS;

    static {
        SEMITONES = new HashMap<>();
        SEMITONES.put("Cbb", -2);
        SEMITONES.put("Cb", -1);
        SEMITONES.put("C", 0);
        SEMITONES.put("C#", 1);
        SEMITONES.put("C##", 2);
        SEMITONES.put("Dbb", 0);
        SEMITONES.put("Db", 1);
        SEMITONES.put("D", 2);
        SEMITONES.put("D#", 3);
        SEMITONES.put("D##", 4);
        SEMITONES.put("Ebb", 2);
        SEMITONES.put("Eb", 3);
        SEMITONES.put("E", 4);
        SEMITONES.put("E#", 5);
        SEMITONES.put("E##", 6);
        SEMITONES.put("Fbb", 3);
        SEMITONES.put("Fb", 4);
        SEMITONES.put("F", 5);
        SEMITONES.put("F#", 6);
        SEMITONES.put("F##", 7);
        SEMITONES.put("Gbb", 5);
        SEMITONES.put("Gb", 6);
        SEMITONES.put("G", 7);
        SEMITONES.put("G#", 8);
        SEMITONES.put("G##", 9);
        SEMITONES.put("Abb", 7);
        SEMITONES.put("Ab", 8);
        SEMITONES.put("A", 9);
        SEMITONES.put("A#", 10);
        SEMITONES.put("A##", 11);
        SEMITONES.put("Bbb", 9);
        SEMITONES.put("Bb", 10);
        SEMITONES.put("B", 11);
        SEMITONES.put("B#", 12);
        SEMITONES.put("B##", 13);

        INTERVALS = new HashMap<>();
        INTERVALS.put("m2", 1);
        INTERVALS.put("M2", 2);
        INTERVALS.put("m3", 3);
        INTERVALS.put("M3", 4);
        INTERVALS.put("P4", 5);
        INTERVALS.put("P5", 7);
        INTERVALS.put("m6", 8);
        INTERVALS.put("M6", 9);
        INTERVALS.put("m7", 10);
        INTERVALS.put("M7", 11);
        INTERVALS.put("P8", 12);
    }

    /**
     Calculates the end note of an interval construction based on the interval and start note provided.
     @param arr An array of strings containing the interval and start note in the format [interval, startNote].
     @return The end note of the interval construction.
     @throws IllegalArgumentException If the input is invalid.
     */
    public static String intervalConstruction(String[] arr) {
        checkArgumentsCount(arr);

        final var interval = arr[0];
        final var startNote = arr[1];

        final var semitonesToMove = INTERVALS.get(interval);
        final var startNoteSemitoneIndex = SEMITONES.get(startNote);

        final var startNodeDegree = getNotePositionInSequence(startNote.charAt(0));
        final var degreesToMove = Integer.parseInt(String.valueOf(interval.charAt(1)));
        var endNoteDegreeIndex = (checkIfOrderIsAscending(arr) ? startNodeDegree + degreesToMove - 2 : startNodeDegree - degreesToMove) % NOTES_COUNT;

        if (endNoteDegreeIndex < 0) {
            endNoteDegreeIndex += NOTES_COUNT;
        }

        final var endNoteSemitoneIndex = new AtomicInteger((checkIfOrderIsAscending(arr) ? startNoteSemitoneIndex + semitonesToMove : startNoteSemitoneIndex - semitonesToMove) % SEMITONES_COUNT);
        final var endNoteDegree = NOTES[endNoteDegreeIndex + 1];

        if (endNoteSemitoneIndex.get() < 0) {
            endNoteSemitoneIndex.set(endNoteSemitoneIndex.get() + SEMITONES_COUNT);
        }

        return SEMITONES.entrySet().stream()
                .filter(entry -> String.valueOf(entry.getKey().charAt(0)).equals(endNoteDegree))
                .filter(entry -> entry.getValue().equals(endNoteSemitoneIndex.get()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input"));
    }

    /**

     Identifies the interval between two notes
     @param arr An array of strings with the start note and end note in the format [startNote, endNote].
     @return The interval between the start note and end note.
     @throws IllegalArgumentException If the input is invalid.
     */
    public static String intervalIdentification(String[] arr) {
        checkArgumentsCount(arr);

        final var startNoteSemitoneIndex = SEMITONES.get(arr[0]);
        var endNoteSemitoneIndex = SEMITONES.get(arr[1]);

        if (startNoteSemitoneIndex > endNoteSemitoneIndex) {
            endNoteSemitoneIndex += SEMITONES_COUNT;
        }

        final var semitonesInInterval = new AtomicInteger(Math.abs(endNoteSemitoneIndex - startNoteSemitoneIndex) % SEMITONES.size());

        if (!checkIfOrderIsAscending(arr)) {
            semitonesInInterval.set(-semitonesInInterval.get() + SEMITONES_COUNT);
        }

        return INTERVALS.entrySet().stream()
                .filter(entry -> entry.getValue().equals(semitonesInInterval.get()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input"));
    }

    private static void checkArgumentsCount(String[] arr) {
        if (arr.length < 2 || arr.length > 3) {
            throw new IllegalArgumentException("Illegal number of elements: 2 or 3 elements needed");
        }
    }

    private static boolean checkIfOrderIsAscending(String[] arr) {
        if (arr.length == 3) {
            if (arr[2].equals("dsc")) {
                return false;
            } else if (!arr[2].equals("asc")) {
                throw new IllegalArgumentException("Invalid input");
            }
        }
        return true;
    }

    private static int getNotePositionInSequence(char note) {
        var position = -1;
        for (var i = 0; i < NOTES_COUNT; i++) {
            if (Objects.equals(String.valueOf(note), NOTES[i])) {
                position = i;
            }
        }
        return position;
    }
}