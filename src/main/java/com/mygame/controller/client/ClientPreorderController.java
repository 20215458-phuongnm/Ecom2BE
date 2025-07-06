package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.dto.ListResponse;
import com.mygame.dto.client.ClientPreorderRequest;
import com.mygame.dto.client.ClientPreorderResponse;
import com.mygame.entity.client.Preorder;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.client.ClientPreorderMapper;
import com.mygame.repository.client.PreorderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-api/preorders")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientPreorderController {

    private PreorderRepository preorderRepository;
    private ClientPreorderMapper clientPreorderMapper;

    private static final String PREORDER_SORT = "updatedAt,desc";

    // API: Lấy danh sách preorder của người dùng hiện tại
    @GetMapping
    public ResponseEntity<ListResponse<ClientPreorderResponse>> getAllPreorders(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = PREORDER_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        Page<Preorder> preorders = preorderRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<ClientPreorderResponse> clientPreorderResponses = preorders.map(clientPreorderMapper::entityToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientPreorderResponses, preorders));
    }

    // API: Tạo mới preorder – hoặc cập nhật lại nếu preorder đã tồn tại nhưng từng bị hủy
    @PostMapping
    public ResponseEntity<ClientPreorderResponse> createPreorder(@RequestBody ClientPreorderRequest request) throws Exception {
        Optional<Preorder> preorderOpt = preorderRepository.findByUser_IdAndProduct_Id(request.getUserId(), request.getProductId());

        if (preorderOpt.isPresent()) {
            Preorder preorder = preorderOpt.get();

            if (preorder.getStatus().equals(1)) {
                throw new Exception("Duplicated preorder"); // Nếu đã có preorder đang hoạt động → báo lỗi
            } else {
                preorder.setUpdatedAt(Instant.now()); // Nếu preorder tồn tại nhưng đã bị hủy → khôi phục lại
                preorder.setStatus(1);
                preorder = preorderRepository.save(preorder);
                return ResponseEntity.status(HttpStatus.OK).body(clientPreorderMapper.entityToResponse(preorder));
            }
        } else { // Nếu chưa có → tạo mới preorder
            Preorder entity = clientPreorderMapper.requestToEntity(request);
            entity = preorderRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientPreorderMapper.entityToResponse(entity));
        }
    }

    // API: Cập nhật thông tin preorder dựa theo userId productId
    @PutMapping
    public ResponseEntity<ClientPreorderResponse> updatePreorder(@RequestBody ClientPreorderRequest request) {
        ClientPreorderResponse clientPreorderResponse = preorderRepository
                .findByUser_IdAndProduct_Id(request.getUserId(), request.getProductId())
                .map(existingEntity -> clientPreorderMapper.partialUpdate(existingEntity, request))
                .map(preorderRepository::save)
                .map(clientPreorderMapper::entityToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResourceName.PREORDER,
                        List.of(FieldName.USER_ID, FieldName.PRODUCT_ID).toString(),
                        List.of(request.getUserId(), request.getProductId())));
        return ResponseEntity.status(HttpStatus.OK).body(clientPreorderResponse);
    }

    // API: Xóa nhiều preorder theo danh sách id
    @DeleteMapping
    public ResponseEntity<Void> deletePreorders(@RequestBody List<Long> ids) {
        preorderRepository.deleteAllById(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
