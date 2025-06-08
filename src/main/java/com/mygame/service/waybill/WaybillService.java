package com.mygame.service.waybill;

import com.mygame.dto.waybill.GhnCallbackOrderRequest;
import com.mygame.dto.waybill.WaybillRequest;
import com.mygame.dto.waybill.WaybillResponse;
import com.mygame.service.CrudService;

public interface WaybillService extends CrudService<Long, WaybillRequest, WaybillResponse> {

    void callbackStatusWaybillFromGHN(GhnCallbackOrderRequest ghnCallbackOrderRequest);

}
