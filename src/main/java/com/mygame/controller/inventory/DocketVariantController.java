package com.mygame.controller.inventory;

import com.mygame.constant.AppConstants;
import com.mygame.dto.inventory.DocketVariantKeyRequest;
import com.mygame.entity.inventory.DocketVariantKey;
import com.mygame.service.inventory.DocketVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/docket-variants")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class DocketVariantController {

    private DocketVariantService docketVariantService;

    //Xóa sp khỏi phiếu
    @DeleteMapping("/{docketId}/{variantId}")
    public ResponseEntity<Void> deleteDocketVariant(@PathVariable("docketId") Long docketId,
                                                    @PathVariable("variantId") Long variantId) {
        DocketVariantKey id = new DocketVariantKey(docketId, variantId);
        docketVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Xóa nhìu sp khỏi phiếu
    @DeleteMapping
    public ResponseEntity<Void> deleteDocketVariants(@RequestBody List<DocketVariantKeyRequest> idRequests) {
        List<DocketVariantKey> ids = idRequests.stream()
                .map(idRequest -> new DocketVariantKey(idRequest.getDocketId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        docketVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
