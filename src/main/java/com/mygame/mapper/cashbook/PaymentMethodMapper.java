package com.mygame.mapper.cashbook;

import com.mygame.dto.cashbook.ClientPaymentMethodResponse;
import com.mygame.dto.cashbook.PaymentMethodRequest;
import com.mygame.dto.cashbook.PaymentMethodResponse;
import com.mygame.entity.cashbook.PaymentMethod;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMethodMapper extends GenericMapper<PaymentMethod, PaymentMethodRequest, PaymentMethodResponse> {

    @Mapping(source = "id", target = "paymentMethodId")
    @Mapping(source = "name", target = "paymentMethodName")
    @Mapping(source = "code", target = "paymentMethodCode")
    ClientPaymentMethodResponse entityToClientResponse(PaymentMethod entity);

    List<ClientPaymentMethodResponse> entityToClientResponse(List<PaymentMethod> entities);

}
