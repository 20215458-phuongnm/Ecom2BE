package com.mygame.dto.waybill;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mygame.entity.waybill.RequiredNote;
import com.mygame.utils.DefaultInstantDeserializer;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class WaybillRequest {
    private Long orderId;
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    private Instant shippingDate;
    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;
    @Nullable
    private String note;
    private RequiredNote ghnRequiredNote;
}
