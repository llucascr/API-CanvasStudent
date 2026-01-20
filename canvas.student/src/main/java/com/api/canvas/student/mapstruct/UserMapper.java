package com.api.canvas.student.mapstruct;

import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User toUserEntity(UserResponseDTO dto);

    @Mapping(target = "userId", source = "userCanvasId")
    UserResponseDTO toUserResponseDTO(User entity);

    List<UserResponseDTO> toListUserResponseDTO(List<User> list);

    default PagedModel<UserResponseDTO> toPagedModel(Page<User> page) {
        if (page == null) {
            return null;
        }
        Page<UserResponseDTO> pageDto = page.map(this::toUserResponseDTO);

        return new PagedModel<>(pageDto);
    }

}
