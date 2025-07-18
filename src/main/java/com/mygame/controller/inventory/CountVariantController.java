package com.mygame.controller.inventory;

import com.mygame.constant.AppConstants;
import com.mygame.dto.inventory.CountVariantKeyRequest;
import com.mygame.entity.inventory.CountVariantKey;
import com.mygame.service.inventory.CountVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/count-variants")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class CountVariantController {

    private CountVariantService countVariantService;

    // API: gỡ sản phẩm ra khỏi đợt kiểm kê
    @DeleteMapping("/{countId}/{variantId}")
    public ResponseEntity<Void> deleteCountVariant(@PathVariable("countId") Long countId,
                                                   @PathVariable("variantId") Long variantId) {
        CountVariantKey id = new CountVariantKey(countId, variantId);
        countVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // API: Xóa nhiều bản ghi count-variant cùng lúc
    @DeleteMapping
    public ResponseEntity<Void> deleteCountVariants(@RequestBody List<CountVariantKeyRequest> idRequests) {
        List<CountVariantKey> ids = idRequests.stream()
                .map(idRequest -> new CountVariantKey(idRequest.getCountId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        countVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
