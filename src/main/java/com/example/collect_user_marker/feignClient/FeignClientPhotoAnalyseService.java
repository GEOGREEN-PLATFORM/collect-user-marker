package com.example.collect_user_marker.feignClient;

import com.example.collect_user_marker.model.UserDTO;
import com.example.collect_user_marker.model.photoAnalyse.PhotoDTO;
import com.example.collect_user_marker.model.photoAnalyse.PhotoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name="photo-analyse", url="${photo.analyse.host}")
public interface FeignClientPhotoAnalyseService {
    @PostMapping("/analyse")
    PhotoResponseDTO analyse(@RequestHeader("Authorization") String token, @RequestBody PhotoDTO photoDTO);

    @GetMapping("/user/by-id/{id}")
    UserDTO getUserById(@RequestHeader("Authorization") String token, @PathVariable UUID id);
}
