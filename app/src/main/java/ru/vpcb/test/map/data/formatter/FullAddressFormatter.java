package ru.vpcb.test.map.data.formatter;

import android.location.Address;
import android.location.Geocoder;

import java.util.List;

import ru.vpcb.test.map.model.Location;

public class FullAddressFormatter implements LocationFormatter {

    private Geocoder geocoder;
    private String emptyResult;
    private int maxResults;
    private int addressLineIndex;

    public FullAddressFormatter(Geocoder geocoder) {
        this.geocoder = geocoder;
        this.emptyResult = "";
        this.maxResults = 1;
        this.addressLineIndex = 0;
    }


    @Override
    public String format(Location location) {
        try {
            List<Address> address = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), maxResults);
            if (address != null && !address.isEmpty()) {
                Address firstAddress = address.get(0);
                return firstAddress.getAddressLine(addressLineIndex);
            }
        } catch (Exception e) {
            //
        }
        return emptyResult;
    }
}
