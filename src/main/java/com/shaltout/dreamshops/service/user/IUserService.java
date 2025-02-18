package com.shaltout.dreamshops.service.user;

import com.shaltout.dreamshops.dto.UserDto;
import com.shaltout.dreamshops.model.User;
import com.shaltout.dreamshops.request.CreateUserRequest;
import com.shaltout.dreamshops.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
