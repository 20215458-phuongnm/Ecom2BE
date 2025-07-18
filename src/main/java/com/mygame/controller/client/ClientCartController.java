package com.mygame.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mygame.constant.AppConstants;
import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.dto.client.ClientCartRequest;
import com.mygame.dto.client.ClientCartResponse;
import com.mygame.dto.client.ClientCartVariantKeyRequest;
import com.mygame.entity.cart.Cart;
import com.mygame.entity.cart.CartVariant;
import com.mygame.entity.cart.CartVariantKey;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.client.ClientCartMapper;
import com.mygame.repository.cart.CartRepository;
import com.mygame.repository.cart.CartVariantRepository;
import com.mygame.repository.inventory.DocketVariantRepository;
import com.mygame.utils.InventoryUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-api/carts")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientCartController {

    private CartRepository cartRepository;
    private CartVariantRepository cartVariantRepository;
    private ClientCartMapper clientCartMapper;
    private DocketVariantRepository docketVariantRepository;

    /**
     * Lấy thông tin giỏ hàng của người dùng hiện tại:
     * - Dựa vào thông tin từ JWT (Spring Security) để lấy username.
     * - Tìm giỏ hàng theo username trong DB.
     * - Nếu có thì map sang ObjectNode để trả về, nếu không thì trả về ObjectNode rỗng.
     *
     * @param authentication Chứa username đã xác thực từ JWT
     * @return JSON object chứa thông tin giỏ hàng (ClientCartResponse dưới dạng ObjectNode)
     */
    @GetMapping
    public ResponseEntity<ObjectNode> getCart(Authentication authentication) {
        String username = authentication.getName();
        ObjectMapper mapper = new ObjectMapper();

        // Reference: https://stackoverflow.com/a/11828920, https://stackoverflow.com/a/51456293
        ObjectNode response = cartRepository.findByUsername(username)
                .map(clientCartMapper::entityToResponse)
                .map(clientCartResponse -> mapper.convertValue(clientCartResponse, ObjectNode.class))
                .orElse(mapper.createObjectNode());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Tạo mới hoặc cập nhật giỏ hàng:
     * - Nếu `cartId` là null: Tạo giỏ hàng mới từ request.
     * - Nếu có `cartId`: Tìm giỏ hàng cũ trong DB và cập nhật lại theo request.
     * - Trước khi lưu, kiểm tra từng biến thể sản phẩm trong giỏ có vượt quá số lượng tồn kho không.
     * - Nếu vượt quá tồn kho thì throw lỗi, ngược lại thì lưu vào DB.
     *
     * @param request Dữ liệu giỏ hàng gửi lên từ client
     * @return Dữ liệu giỏ hàng sau khi lưu (ClientCartResponse)
     */
    @PostMapping
    public ResponseEntity<ClientCartResponse> saveCart(@RequestBody ClientCartRequest request) {
        final Cart cartBeforeSave;

        // TODO: Đôi khi cartId null nhưng thực tế user vẫn đang có cart trong DB
        if (request.getCartId() == null) {
            cartBeforeSave = clientCartMapper.requestToEntity(request);
        } else {
            cartBeforeSave = cartRepository.findById(request.getCartId())
                    .map(existingEntity -> clientCartMapper.partialUpdate(existingEntity, request))
                    .orElseThrow(() -> new ResourceNotFoundException(ResourceName.CART, FieldName.ID, request.getCartId()));
        }

        // Validate Variant Inventory
        for (CartVariant cartVariant : cartBeforeSave.getCartVariants()) {
            int inventory = InventoryUtils
                    .calculateInventoryIndices(docketVariantRepository.findByVariantId(cartVariant.getCartVariantKey().getVariantId()))
                    .get("canBeSold");
            if (cartVariant.getQuantity() > inventory) {
                throw new RuntimeException("Variant quantity cannot greater than variant inventory");
            }
        }

        Cart cart = cartRepository.save(cartBeforeSave);
        ClientCartResponse clientCartResponse = clientCartMapper.entityToResponse(cart);
        return ResponseEntity.status(HttpStatus.OK).body(clientCartResponse);
    }

    /**
     * Xóa nhiều item trong giỏ hàng:
     * - Nhận danh sách cartId + variantId từ client.
     * - Duyệt qua từng item để tạo `CartVariantKey` (khóa tổng hợp).
     * - Gọi repository để xóa tất cả theo danh sách key đó.
     *
     * @param idRequests Danh sách các item cần xóa (dựa theo cartId + variantId)
     * @return 204 No Content nếu xóa thành công
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteCartItems(@RequestBody List<ClientCartVariantKeyRequest> idRequests) {
        List<CartVariantKey> ids = idRequests.stream()
                .map(idRequest -> new CartVariantKey(idRequest.getCartId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        cartVariantRepository.deleteAllById(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
