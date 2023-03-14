package com.innowise.intervals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Intervals {
    private static final Map<String, Integer> NOTES;
    private static final Map<String, Integer> INTERVALS;

    static {
        NOTES = new HashMap<>();
        NOTES.put("Cbb", -2);
        NOTES.put("Cb", -1);
        NOTES.put("C", 0);
        NOTES.put("C#", 1);
        NOTES.put("C##", 2);
        NOTES.put("Dbb", 0);
        NOTES.put("Db", 1);
        NOTES.put("D", 2);
        NOTES.put("D#", 3);
        NOTES.put("D##", 4);
        NOTES.put("Ebb", 2);
        NOTES.put("Eb", 3);
        NOTES.put("E", 4);
        NOTES.put("E#", 5);
        NOTES.put("E##", 6);
        NOTES.put("Fbb", 3);
        NOTES.put("Fb", 4);
        NOTES.put("F", 5);
        NOTES.put("F#", 6);
        NOTES.put("F##", 7);
        NOTES.put("Gbb", 5);
        NOTES.put("Gb", 6);
        NOTES.put("G", 7);
        NOTES.put("G#", 8);
        NOTES.put("G##", 9);
        NOTES.put("Abb", 7);
        NOTES.put("Ab", 8);
        NOTES.put("A", 9);
        NOTES.put("A#", 10);
        NOTES.put("A##", 11);
        NOTES.put("Bbb", 9);
        NOTES.put("Bb", 10);
        NOTES.put("B", 11);
        NOTES.put("B#", 12);
        NOTES.put("B##", 13);

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

        String interval = arr[0];
        String startNote = arr[1];

        int startNoteIndex = NOTES.get(startNote);
        int semitones = INTERVALS.get(interval);

        final var endNoteIndex = new AtomicInteger((checkIfOrderIsAscending(arr) ? startNoteIndex + semitones : startNoteIndex - semitones) % 12);

        if (endNoteIndex.get() < 0) {
            endNoteIndex.set(endNoteIndex.get() + 12);
        }

        return NOTES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(endNoteIndex.get()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input"));
    }

    public static String intervalIdentification(String[] arr) {
        checkArgumentsCount(arr);

        int startNoteIndex = NOTES.get(arr[0]);
        int endNoteIndex = NOTES.get(arr[1]);
        if (startNoteIndex > endNoteIndex) {
            endNoteIndex += 12;
        }

        final var semitones = new AtomicInteger(Math.abs(endNoteIndex - startNoteIndex) % NOTES.size());

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

    private static void checkArgumentsCount(String[] arr){
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
}