package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.dto.client.ClientBrandResponse;
import com.mygame.dto.client.ClientFilterResponse;
import com.mygame.entity.product.Brand;
import com.mygame.repository.product.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-api/filters")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientFilterController {

    private BrandRepository brandRepository;

    // API: Lọc brand theo slug của category
    @GetMapping("/category")
    public ResponseEntity<ClientFilterResponse> getFilterByCategorySlug(@RequestParam String slug) {
        // Truy vấn brand có liên quan đến category theo slug
        List<Brand> brands = brandRepository.findByCategorySlug(slug);

        // Tạo response chứa danh sách brand
        ClientFilterResponse clientFilterResponse = new ClientFilterResponse();
        clientFilterResponse.setFilterBrands(mapToClientBrandResponse(brands));
        return ResponseEntity.status(HttpStatus.OK).body(clientFilterResponse);
    }

    // API: Lọc brand theo từ khóa tìm kiếm
    @GetMapping("/search")
    public ResponseEntity<ClientFilterResponse> getFilterBySearchQuery(@RequestParam String query) {
        // Truy vấn brand liên quan đến từ khóa tìm kiếm
        List<Brand> brands = brandRepository.findBySearchQuery(query);
        // Tạo response trả về danh sách brand phù hợp
        ClientFilterResponse clientFilterResponse = new ClientFilterResponse();
        clientFilterResponse.setFilterBrands(mapToClientBrandResponse(brands));
        return ResponseEntity.status(HttpStatus.OK).body(clientFilterResponse);
    }

    // Chuyển danh sách entity Brand → DTO dành cho client
    private List<ClientBrandResponse> mapToClientBrandResponse(List<Brand> brands) {
        return brands.stream()
                .map(brand -> new ClientBrandResponse()
                        .setBrandId(brand.getId())
                        .setBrandName(brand.getName()))
                .collect(Collectors.toList());
    }

}
