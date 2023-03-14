package com.innowise.intervals;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Intervals {
    private static final Map<String, Integer> SEMITONES;
    private static final String[] NOTES = {"C", "D", "E", "F", "G", "A", "B"};
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

    public static String intervalConstruction(String[] arr) {
        checkArgumentsCount(arr);

        final var interval = arr[0];
        final var startNote = arr[1];

        final var startNoteIndex = SEMITONES.get(startNote);
        final var semitones = INTERVALS.get(interval);

        final var startNodeDegree = getNotePosition(startNote.charAt(0));
        final var degreesToMove = Integer.parseInt(String.valueOf(interval.charAt(1)));
        var endNoteDegreeIndex = (checkIfOrderIsAscending(arr) ? startNodeDegree + degreesToMove - 2 : startNodeDegree - degreesToMove) % NOTES.length;

        if (endNoteDegreeIndex < 0){
            endNoteDegreeIndex += NOTES.length;
        }
        final var endNoteSemitoneIndex = new AtomicInteger((checkIfOrderIsAscending(arr) ? startNoteIndex + semitones : startNoteIndex - semitones) % 12);
        final var endNoteDegree = NOTES[endNoteDegreeIndex + 1];

        if (endNoteSemitoneIndex.get() < 0) {
            endNoteSemitoneIndex.set(endNoteSemitoneIndex.get() + 12);
        }

        return SEMITONES.entrySet().stream()
                .filter(entry -> String.valueOf(entry.getKey().charAt(0)).equals(endNoteDegree))
                .filter(entry -> entry.getValue().equals(endNoteSemitoneIndex.get()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input"));
    }

    public static String intervalIdentification(String[] arr) {
        checkArgumentsCount(arr);

        final var startNoteIndex = SEMITONES.get(arr[0]);
        var endNoteIndex = SEMITONES.get(arr[1]);
        if (startNoteIndex > endNoteIndex) {
            endNoteIndex += 12;
        }

        final var semitones = new AtomicInteger(Math.abs(endNoteIndex - startNoteIndex) % SEMITONES.size());

        if (!checkIfOrderIsAscending(arr)) {
            semitones.set(-semitones.get());
            semitones.set(semitones.get() + 12);
        }

        return INTERVALS.entrySet().stream()
                .filter(entry -> entry.getValue().equals(semitones.get()))
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

    private static int getNotePosition(char note) {
        var position = -1;
        for (var i = 0; i < NOTES.length; i++){
            if (Objects.equals(String.valueOf(note), NOTES[i])){
                position = i;
            }
        }
        return position;
    }
}