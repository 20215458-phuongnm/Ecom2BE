package com.mygame.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromotionCheckingResponse {
    private boolean promotionable;
}
