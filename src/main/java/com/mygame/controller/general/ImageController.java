package com.mygame.controller.general;

import com.mygame.constant.AppConstants;
import com.mygame.dto.CollectionWrapper;
import com.mygame.dto.general.UploadedImageResponse;
import com.mygame.service.general.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
@Slf4j
public class ImageController {

    private ImageService imageService;

    // API: Upload một ảnh đơn lên server
    @PostMapping("/upload-single")
    public ResponseEntity<UploadedImageResponse> uploadSingleImage(@RequestParam("image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.store(image));
    }

    // API: Upload nhiều ảnh lên server cùng lúc
    @PostMapping("/upload-multiple")
    public ResponseEntity<CollectionWrapper<UploadedImageResponse>> uploadMultipleImages(@RequestParam("images") MultipartFile[] images) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CollectionWrapper<>(Stream.of(images)
                        .map(imageService::store)
                        .collect(Collectors.toList())));
    }

    // API: Truy cập/hiển thị ảnh đã upload dựa vào tên file
    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageName, HttpServletRequest request) {
        Resource resource = imageService.load(imageName);

        String contentType = null;

        try {
            // Xác định type (image/jpeg, image/png, ...)
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // API: Xóa một ảnh theo tên file
    @DeleteMapping("/{imageName:.+}")
    public ResponseEntity<Void> deleteImage(@PathVariable String imageName) {
        imageService.delete(imageName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // API: Xóa nhiều ảnh theo danh sách tên file
    @DeleteMapping
    public ResponseEntity<Void> deleteMultipleImages(@RequestBody List<String> imageNames) {
        imageNames.forEach(imageService::delete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
