package com.mygame.controller.statistic;

import com.mygame.constant.AppConstants;
import com.mygame.dto.statistic.StatisticResponse;
import com.mygame.service.statistic.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class StatisticController {

    private StatisticService statisticService;

    // API: Lấy các thống kê tổng quan
    @GetMapping
    public ResponseEntity<StatisticResponse> getStatistic() {
        // Chưa rõ API này có lấy thống kê theo 7 ngày gần nhất?
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getStatistic());
    }

}
