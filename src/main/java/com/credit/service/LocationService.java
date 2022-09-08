package com.credit.service;

import com.credit.util.ReturnUtil;

public interface LocationService {
    ReturnUtil addLocation(String locationName, Integer locationFatherid, Integer locationGrade);

    ReturnUtil updateLocation(Integer locationId, String locationName);

    ReturnUtil deleteLocation(Integer locationId);

    ReturnUtil getFirstLocation();

    ReturnUtil getSecondOrThirdLocation( String search);

}
