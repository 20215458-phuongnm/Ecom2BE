package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.dto.CollectionWrapper;
import com.mygame.dto.client.ClientCategoryResponse;
import com.mygame.entity.product.Category;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.client.ClientCategoryMapper;
import com.mygame.repository.product.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-api/categories")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientCategoryController {

    private CategoryRepository categoryRepository;
    private ClientCategoryMapper clientCategoryMapper;

    /**
     * Lấy danh sách tất cả các danh mục cấp cao (root categories):
     * - Tìm tất cả category mà không có danh mục cha (`parentCategory is null`).
     * - Dùng `ClientCategoryMapper` để map sang DTO và tự động đệ quy hiển thị con (tối đa 3 cấp).
     * - Trả về danh sách bọc trong `CollectionWrapper` cho client.
     *
     * @return Danh sách danh mục cấp cao (kèm theo danh mục con) ở dạng JSON
     */
    @GetMapping
    public ResponseEntity<CollectionWrapper<ClientCategoryResponse>> getAllCategories() {
        List<Category> firstCategories = categoryRepository.findByParentCategoryIsNull();
        List<ClientCategoryResponse> clientCategoryResponses = clientCategoryMapper.entityToResponse(firstCategories, 3);
        return ResponseEntity.status(HttpStatus.OK).body(CollectionWrapper.of(clientCategoryResponses));
    }

    /**
     * Lấy thông tin chi tiết một danh mục theo `slug`:
     * - Tìm danh mục theo slug từ URL.
     * - Nếu tìm thấy thì map sang `ClientCategoryResponse`, không đệ quy danh mục con.
     * - Nếu không tìm thấy thì ném lỗi 404 (ResourceNotFoundException).
     *
     * @param slug Slug của danh mục (URL friendly)
     * @return Chi tiết danh mục
     */
    @GetMapping("/{slug}")
    public ResponseEntity<ClientCategoryResponse> getCategory(@PathVariable("slug") String slug) {
        ClientCategoryResponse clientCategoryResponse = categoryRepository.findBySlug(slug)
                .map(category -> clientCategoryMapper.entityToResponse(category, false))
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.CATEGORY, FieldName.SLUG, slug));
        return ResponseEntity.status(HttpStatus.OK).body(clientCategoryResponse);
    }

}
