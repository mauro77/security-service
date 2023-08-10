package com.nisum.securityservice.dto.phone;

import com.nisum.securityservice.model.Phone;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public abstract class PhoneMapper {
    public abstract PhoneDto toDto(Phone phone);

    public abstract List<PhoneDto> toDto(List<Phone> phones);

    public abstract List<Phone> createListFromRequest(List<PhoneRequest> phones);

    public abstract Phone createFromRequest(PhoneRequest phoneRequest);

    public abstract void updateModel(PhoneRequest phoneRequest, @MappingTarget Phone phone);
}
