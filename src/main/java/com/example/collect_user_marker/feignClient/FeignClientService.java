package com.example.collect_user_marker.feignClient;

import com.example.collect_user_marker.model.photoAnalyse.PhotoDTO;
import com.example.collect_user_marker.model.photoAnalyse.PhotoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="photo-analyse", url="${photo.analyse.host}")
public interface FeignClientService {
    @PostMapping("/analyse")
    PhotoResponseDTO analyse(@RequestHeader("Authorization") String token, @RequestBody PhotoDTO photoDTO);
}
