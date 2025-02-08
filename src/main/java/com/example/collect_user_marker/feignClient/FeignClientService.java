package com.example.collect_user_marker.feignClient;

import com.example.collect_user_marker.model.photoAnalyse.PhotoDTO;
import com.example.collect_user_marker.model.photoAnalyse.PhotoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="photo-analyse", url="${photo.analyse.host}:${photo.analyse.port}")
public interface FeignClientService {
    @PostMapping("/analyse")
    PhotoResponseDTO analyse(@RequestBody PhotoDTO photoDTO);
}
