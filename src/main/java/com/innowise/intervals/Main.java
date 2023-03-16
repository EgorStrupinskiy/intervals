package com.innowise.intervals;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String[] intervalConstructionInput = {"M3", "Cb", "dsc"};
        String intervalConstructionOutput = Intervals.intervalConstruction(intervalConstructionInput);
        System.out.println("Interval construction: " + intervalConstructionOutput);

        String[] intervalIdentificationInput = {"C", "D", "asc"};
        String intervalIdentificationOutput = Intervals.intervalIdentification(intervalIdentificationInput);
        System.out.println("Interval identification: " + intervalIdentificationOutput);
    }
}
