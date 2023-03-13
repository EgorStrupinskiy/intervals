package com.innowise.intervals;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String[] intervalConstructionInput = {"P4", "E", "dsc"};
        String intervalConstructionOutput = Intervals.intervalConstruction(intervalConstructionInput);
        System.out.println("Interval construction: " + intervalConstructionOutput);

        String[] intervalIdentificationInput = {"Cb", "Abb", "asc"};
        String intervalIdentificationOutput = Intervals.intervalIdentification(intervalIdentificationInput);
        System.out.println("Interval identification: " + intervalIdentificationOutput);
    }
}
