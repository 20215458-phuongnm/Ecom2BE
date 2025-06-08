package com.mygame.dto.waybill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mygame.entity.waybill.RequiredNote;
import lombok.Data;
import org.springframework.lang.Nullable;

// Reference: https://api.ghn.vn/home/docs/detail?id=103
@Data
public class GhnUpdateOrderRequest {
    @JsonProperty("order_code")
    private String orderCode;
    @JsonProperty("note")
    @Nullable
    private String note;
    @JsonProperty("required_note")
    private RequiredNote requiredNote;
}
