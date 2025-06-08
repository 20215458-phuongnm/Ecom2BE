package com.mygame.service.inventory;

import com.mygame.constant.ResourceName;
import com.mygame.constant.SearchFields;
import com.mygame.dto.ListResponse;
import com.mygame.dto.inventory.CountVariantRequest;
import com.mygame.dto.inventory.CountVariantResponse;
import com.mygame.entity.inventory.CountVariantKey;
import com.mygame.mapper.inventory.CountVariantMapper;
import com.mygame.repository.inventory.CountVariantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountVariantServiceImpl implements CountVariantService {

    private CountVariantRepository countVariantRepository;

    private CountVariantMapper countVariantMapper;

    @Override
    public ListResponse<CountVariantResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.COUNT_VARIANT, countVariantRepository, countVariantMapper);
    }

    @Override
    public CountVariantResponse findById(CountVariantKey id) {
        return defaultFindById(id, countVariantRepository, countVariantMapper, ResourceName.COUNT_VARIANT);
    }

    @Override
    public CountVariantResponse save(CountVariantRequest request) {
        return defaultSave(request, countVariantRepository, countVariantMapper);
    }

    @Override
    public CountVariantResponse save(CountVariantKey id, CountVariantRequest request) {
        return defaultSave(id, request, countVariantRepository, countVariantMapper, ResourceName.COUNT_VARIANT);
    }

    @Override
    public void delete(CountVariantKey id) {
        countVariantRepository.deleteById(id);
    }

    @Override
    public void delete(List<CountVariantKey> ids) {
        countVariantRepository.deleteAllById(ids);
    }

}
