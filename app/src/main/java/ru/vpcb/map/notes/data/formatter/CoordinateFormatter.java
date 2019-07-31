package ru.vpcb.map.notes.data.formatter;

import java.util.Locale;

public class CoordinateFormatter implements LatLonFormatter {

    @Override
    public String format(double lat, double lon) {

        return String.format(Locale.ENGLISH,"(%.04f; %.04f)", lat, lon);
    }
}
