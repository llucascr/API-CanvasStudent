package com.api.canvas.student.mapper;

import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "userId", source = "userCanvasId")
    UserResponseDTO toUserResponseDTO(User entity);

    default PagedModel<UserResponseDTO> toPagedModel(Page<User> page) {
        if (page == null) {
            return null;
        }
        Page<UserResponseDTO> pageDto = page.map(this::toUserResponseDTO);

        return new PagedModel<>(pageDto);
    }

}
