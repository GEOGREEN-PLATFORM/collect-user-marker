package com.example.collect_user_marker.feignClient;

import com.example.collect_user_marker.model.geo.GeoMarkerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name="geo-marker", url="${geospatial.server.host}")
public interface FeignClientGeoMarkerService {
    @PostMapping("/geo/info")
    GeoMarkerDTO postGeoPointById(@RequestHeader("Authorization") String token, @RequestBody GeoMarkerDTO geoMarkerDTO);

}
