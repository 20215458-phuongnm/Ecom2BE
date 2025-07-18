package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.dto.ListResponse;
import com.mygame.dto.client.ClientReviewRequest;
import com.mygame.dto.client.ClientReviewResponse;
import com.mygame.dto.client.ClientSimpleReviewResponse;
import com.mygame.entity.review.Review;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.client.ClientReviewMapper;
import com.mygame.repository.review.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-api/reviews")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientReviewController {

    private ReviewRepository reviewRepository;
    private ClientReviewMapper clientReviewMapper;

    // API: Lấy danh sách review của một sản phẩm cụ thể (theo slug sản phẩm)
    @GetMapping("/products/{productSlug}")
    public ResponseEntity<ListResponse<ClientSimpleReviewResponse>> getAllReviewsByProduct(
            @PathVariable String productSlug,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        // Truy vấn tất cả review theo sản phẩm
        Page<Review> reviews = reviewRepository.findAllByProductSlug(productSlug, sort, filter, PageRequest.of(page - 1, size));
        // Map sang DTO  hiển thị client
        List<ClientSimpleReviewResponse> clientReviewResponses = reviews.map(clientReviewMapper::entityToSimpleResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientReviewResponses, reviews));
    }

    // API: Lấy danh sách review của người dùng hiện tại
    @GetMapping
    public ResponseEntity<ListResponse<ClientReviewResponse>> getAllReviewsByUser(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        // Truy vấn các đánh giá của user đang đăng nhập
        Page<Review> reviews = reviewRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<ClientReviewResponse> clientReviewResponses = reviews.map(clientReviewMapper::entityToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientReviewResponses, reviews));
    }

    // API: Tạo mới một đánh giá (review) sản phẩm
    @PostMapping
    public ResponseEntity<ClientReviewResponse> createReview(@RequestBody ClientReviewRequest request) {
        Review entity = reviewRepository.save(clientReviewMapper.requestToEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientReviewMapper.entityToResponse(entity));
    }

    // API: Cập nhật nội dung đánh giá đã tồn tại theo id
    @PutMapping("/{id}")
    public ResponseEntity<ClientReviewResponse> updateReview(@PathVariable Long id,
                                                             @RequestBody ClientReviewRequest request) {
        ClientReviewResponse clientReviewResponse = reviewRepository.findById(id)
                .map(existingEntity -> clientReviewMapper.partialUpdate(existingEntity, request))
                .map(reviewRepository::save)
                .map(clientReviewMapper::entityToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.REVIEW, FieldName.ID, id));
        return ResponseEntity.status(HttpStatus.OK).body(clientReviewResponse);
    }

    // API: Xóa nhiều review theo danh sách ID
    @DeleteMapping
    public ResponseEntity<Void> deleteReviews(@RequestBody List<Long> ids) {
        reviewRepository.deleteAllById(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
