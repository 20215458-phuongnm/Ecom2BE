package com.mygame.mapper.reward;

import com.mygame.dto.reward.RewardStrategyRequest;
import com.mygame.dto.reward.RewardStrategyResponse;
import com.mygame.entity.reward.RewardStrategy;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RewardStrategyMapper extends GenericMapper<RewardStrategy, RewardStrategyRequest, RewardStrategyResponse> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RewardStrategy partialUpdate(@MappingTarget RewardStrategy entity, RewardStrategyRequest request);

}
