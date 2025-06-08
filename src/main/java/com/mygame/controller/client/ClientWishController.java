package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.dto.ListResponse;
import com.mygame.dto.client.ClientWishRequest;
import com.mygame.dto.client.ClientWishResponse;
import com.mygame.entity.client.Wish;
import com.mygame.mapper.client.ClientWishMapper;
import com.mygame.repository.client.WishRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-api/wishes")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientWishController {

    private WishRepository wishRepository;
    private ClientWishMapper clientWishMapper;

    @GetMapping
    public ResponseEntity<ListResponse<ClientWishResponse>> getAllWishes(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        Page<Wish> wishes = wishRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<ClientWishResponse> clientWishResponses = wishes.map(clientWishMapper::entityToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientWishResponses, wishes));
    }

    @PostMapping
    public ResponseEntity<ClientWishResponse> createWish(@RequestBody ClientWishRequest request) throws Exception {
        Optional<Wish> wishOpt = wishRepository.findByUser_IdAndProduct_Id(request.getUserId(), request.getProductId());

        if (wishOpt.isPresent()) {
            throw new Exception("Duplicated wish");
        } else {
            Wish entity = clientWishMapper.requestToEntity(request);
            entity = wishRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientWishMapper.entityToResponse(entity));
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWishes(@RequestBody List<Long> ids) {
        wishRepository.deleteAllById(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
