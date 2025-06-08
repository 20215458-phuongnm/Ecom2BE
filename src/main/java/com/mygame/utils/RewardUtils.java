package com.mygame.utils;

import com.mygame.entity.general.Notification;
import com.mygame.entity.general.NotificationType;
import com.mygame.entity.order.Order;
import com.mygame.entity.review.Review;
import com.mygame.entity.reward.RewardLog;
import com.mygame.entity.reward.RewardStrategy;
import com.mygame.entity.reward.RewardType;
import com.mygame.mapper.general.NotificationMapper;
import com.mygame.repository.general.NotificationRepository;
import com.mygame.repository.reward.RewardLogRepository;
import com.mygame.repository.reward.RewardStrategyRepository;
import com.mygame.service.general.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RewardUtils {

    private RewardStrategyRepository rewardStrategyRepository;
    private RewardLogRepository rewardLogRepository;
    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private NotificationMapper notificationMapper;

    private static final ExpressionParser spelParser = new SpelExpressionParser();

    public void approveReviewHook(Review review) {
        // Khi thay đổi trạng thái review từ (1) Chưa duyệt sang (2) Đã duyệt
        if (review.getStatus().equals(2)) {
            Optional<RewardStrategy> addReviewRewardStrategy = rewardStrategyRepository
                    .findByCodeAndStatus(RewardType.ADD_REVIEW, 1);

            if (addReviewRewardStrategy.isPresent()) {
                // Reference: https://stackoverflow.com/a/22043501
                Integer score = spelParser.parseExpression(addReviewRewardStrategy.get().getFormula())
                        .getValue(Integer.class);

                String note = String.format("Bạn đã nhận được %s điểm thưởng cho đánh giá ở sản phẩm %s.",
                        score,
                        review.getProduct().getName()
                );

                // (1) Save reward log
                RewardLog rewardLog = new RewardLog()
                        .setUser(review.getUser())
                        .setType(RewardType.ADD_REVIEW)
                        .setScore(score)
                        .setNote(note);

                rewardLogRepository.save(rewardLog);

                // (2) Save notification
                Notification notification = new Notification()
                        .setUser(review.getUser())
                        .setType(NotificationType.REVIEW)
                        .setMessage(note)
                        .setAnchor("/user/reward")
                        .setStatus(1);

                notificationRepository.save(notification);

                notificationService.pushNotification(review.getUser().getUsername(),
                        notificationMapper.entityToResponse(notification));
            }
        }
    }

    public void successOrderHook(Order order) {
        // Nếu đơn hàng là "giao hàng thành công" và "đã thanh toán"
        if (order.getStatus().equals(4) && order.getPaymentStatus().equals(2)) {
            Optional<RewardStrategy> successOrderRewardStrategy = rewardStrategyRepository
                    .findByCodeAndStatus(RewardType.SUCCESS_ORDER, 1);

            if (successOrderRewardStrategy.isPresent()) {
                Integer score = spelParser.parseExpression(successOrderRewardStrategy.get().getFormula()
                                .replace("{{ORDER_TOTAL_PAY}}", order.getTotalPay().toString()))
                        .getValue(Integer.class);

                String note = String.format("Bạn đã nhận được %s điểm thưởng cho đơn hàng %s.",
                        score,
                        order.getCode()
                );

                // (1) Save reward log
                RewardLog rewardLog = new RewardLog()
                        .setUser(order.getUser())
                        .setType(RewardType.SUCCESS_ORDER)
                        .setScore(score)
                        .setNote(note);

                rewardLogRepository.save(rewardLog);

                // (2) Save notification
                Notification notification = new Notification()
                        .setUser(order.getUser())
                        .setType(NotificationType.ORDER)
                        .setMessage(note)
                        .setAnchor("/user/reward")
                        .setStatus(1);

                notificationRepository.save(notification);

                notificationService.pushNotification(order.getUser().getUsername(),
                        notificationMapper.entityToResponse(notification));
            }
        }
    }

}
