package com.arpatilmh.feedingestor.entity.platform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Odds {
    private double home;
    private double draw;
    private double away;
}
