package com.mygame.service.general;

import com.mygame.dto.general.UploadedImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    UploadedImageResponse store(MultipartFile image);

    Resource load(String imageName);

    void delete(String imageName);

}
