package com.mygame.service.inventory;

import com.mygame.dto.inventory.DocketRequest;
import com.mygame.dto.inventory.DocketResponse;
import com.mygame.service.CrudService;

public interface DocketService extends CrudService<Long, DocketRequest, DocketResponse> {}
