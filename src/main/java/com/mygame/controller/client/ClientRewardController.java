package com.mygame.controller.client;

import com.mygame.constant.AppConstants;
import com.mygame.dto.client.ClientRewardLogResponse;
import com.mygame.dto.client.ClientRewardResponse;
import com.mygame.entity.reward.RewardLog;
import com.mygame.mapper.client.ClientRewardLogMapper;
import com.mygame.repository.reward.RewardLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-api/rewards")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientRewardController {

    private RewardLogRepository rewardLogRepository;
    private ClientRewardLogMapper clientRewardLogMapper;

    // API: Lấy tổng điểm thưởng và lịch sử tích điểm của user hiện tại
    @GetMapping
    public ResponseEntity<ClientRewardResponse> getReward(Authentication authentication) {
        String username = authentication.getName();

        int totalScore = rewardLogRepository.sumScoreByUsername(username); // Tổng hợp điểm thưởng
        // Lấy lịch sử tích điểm
        List<ClientRewardLogResponse> logs = clientRewardLogMapper
                .entityToResponse(rewardLogRepository
                        .findByUserUsername(username)
                        .stream()
                        .sorted(Comparator.comparing(RewardLog::getId).reversed())
                        .collect(Collectors.toList()));

        ClientRewardResponse clientWishResponse = new ClientRewardResponse();
        clientWishResponse.setRewardTotalScore(totalScore);
        clientWishResponse.setRewardLogs(logs);

        return ResponseEntity.status(HttpStatus.OK).body(clientWishResponse);
    }

}
