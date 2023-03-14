package com.innowise.intervals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntervalsTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/twoNotes.csv", numLinesToSkip = 1)
    public void shouldSuccessfullyIdentifyMusicalInterval(
            final String firstNote, final String secondNote, final String order, final String interval) {
        //given
        var expected = interval;

        //when
        var actual = Intervals.intervalIdentification(new String[]{firstNote, secondNote, order});

        //then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/noteAndInterval.csv", numLinesToSkip = 1)
    public void shouldSuccessfullyConstructMusicalInterval(
            final String firstNote, final String interval, final String order, final String secondNote) {
        //given
        var expected = secondNote;

        //when
        var actual = Intervals.intervalConstruction(new String[]{firstNote, interval, order});

        //then
        assertEquals(expected, actual);
    }
}
