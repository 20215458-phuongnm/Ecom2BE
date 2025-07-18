package com.mygame.service.inventory;

import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.constant.SearchFields;
import com.mygame.dto.ListResponse;
import com.mygame.dto.inventory.DocketRequest;
import com.mygame.dto.inventory.DocketResponse;
import com.mygame.entity.client.Preorder;
import com.mygame.entity.general.Notification;
import com.mygame.entity.general.NotificationType;
import com.mygame.entity.inventory.Docket;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.general.NotificationMapper;
import com.mygame.mapper.inventory.DocketMapper;
import com.mygame.repository.client.PreorderRepository;
import com.mygame.repository.general.NotificationRepository;
import com.mygame.repository.inventory.DocketRepository;
import com.mygame.service.general.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DocketServiceImpl implements DocketService {

    private DocketRepository docketRepository;

    private DocketMapper docketMapper;

    private PreorderRepository preorderRepository;

    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    private NotificationMapper notificationMapper;

    @Override
    public ListResponse<DocketResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.DOCKET, docketRepository, docketMapper);
    }

    @Override
    public DocketResponse findById(Long id) {
        return defaultFindById(id, docketRepository, docketMapper, ResourceName.DOCKET);
    }

    @Override
    public DocketResponse save(DocketRequest request) {
        Docket docket = docketRepository.save(docketMapper.requestToEntity(request));
        afterCreateOrUpdateCallback(docket);
        return docketMapper.entityToResponse(docket);
    }

    @Override
    public DocketResponse save(Long id, DocketRequest request) {
        Docket docket = docketRepository.findById(id)
                .map(existingEntity -> docketMapper.partialUpdate(existingEntity, request))
                .map(docketRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.DOCKET, FieldName.ID, id));
        afterCreateOrUpdateCallback(docket);
        return docketMapper.entityToResponse(docket);
    }

    //Hậu xử lý preorder
    private void afterCreateOrUpdateCallback(Docket docket) {
        // Docket nhập (1) có trạng thái Hoàn thành (3)
        // (1) Lấy các sản phẩm có trong Docket nhập kho
        if (docket.getType().equals(1) && docket.getStatus().equals(3)) {
            List<Long> productIds = docket.getDocketVariants().stream()
                    .map(docketVariant -> docketVariant.getVariant().getProduct().getId())
                    .distinct()
                    .collect(Collectors.toList());
// (2) Tìm các preorder đang chờ cho các sản phẩm đó
            List<Preorder> preorders = preorderRepository.findByProduct_IdInAndStatus(productIds, 1);
// (3) Gửi Notification đến người dùng đặt trước
            List<Notification> notifications = preorders.stream()
                    .map(preorder -> new Notification()
                            .setUser(preorder.getUser())
                            .setType(NotificationType.PREORDER)
                            .setMessage(String.format("Sản phẩm %s đã có hàng. Vui lòng kiểm tra.", preorder.getProduct().getName()))
                            .setAnchor("/product/" + preorder.getProduct().getSlug())
                            .setStatus(1))
                    .collect(Collectors.toList());

            notificationRepository.saveAll(notifications);

            notifications.forEach(notification -> notificationService
                    .pushNotification(notification.getUser().getUsername(), notificationMapper.entityToResponse(notification)));

            preorders.forEach(preorder -> {
                preorder.setUpdatedAt(Instant.now());
                preorder.setStatus(2);
            });

            preorderRepository.saveAll(preorders);
// (4) Cập nhật preorder thành đã xử lý
            List<String> usernames = notifications.stream()
                    .map(notification -> notification.getUser().getUsername())
                    .collect(Collectors.toList());

            log.info("Push notifications for users: " + usernames);
        }
    }

    @Override
    public void delete(Long id) {
        docketRepository.deleteById(id);
    }

    @Override
    public void delete(List<Long> ids) {
        docketRepository.deleteAllById(ids);
    }

}
