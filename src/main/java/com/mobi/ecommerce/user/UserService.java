package com.mobi.ecommerce.user;

import com.mobi.ecommerce.security.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
     private final  UserRepository userRepository;
     private final SecurityUtils securityUtils;
     private final  UserMapper userMapper;
     private final PasswordEncoder passwordEncoder;

     public UserService(UserRepository userRepository, SecurityUtils securityUtils, UserMapper userMapper, PasswordEncoder passwordEncoder) {
          this.userRepository = userRepository;
          this.securityUtils = securityUtils;
         this.userMapper = userMapper;
          this.passwordEncoder = passwordEncoder;
     }



     /*
     * getAllUsers (Admin responsibility)
     * delete User
     * update user data
     * */
     UserResponse getUser(){
          User user = securityUtils.getAuthenticatedUser();
          return userMapper.toUserResponse(user);
     }
     public List<UserResponse> getAllUsers(){
          List<User> users=userRepository.findAll();
          return users.stream().map(userMapper::toUserResponse).toList();
     }

@Transactional
public UserResponse updateUser(UserRequest request) {
     User user = securityUtils.getAuthenticatedUser(); // Fetches authenticated User
     if (request.getFirstName() != null&& !request.getFirstName().equals(user.getFirstName())) {
          user.setFirstName(request.getFirstName());
     }
     if (request.getLastName() != null && !request.getLastName().equals(user.getLastName())) {
          user.setLastName(request.getLastName());
     }
     if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(user.getPhoneNumber())) {
          user.setPhoneNumber(request.getPhoneNumber());
     }
     return userMapper.toUserResponse(user);
     }

     @Transactional
     public void updatePassword(UpdatePasswordRequest request) {
          User user = securityUtils.getAuthenticatedUser();

          // Check if the old password matches
          if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
          }

          // Validate that the new password is different from the old one
          if (request.getOldPassword().equals(request.getNewPassword())) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be the same as the old password");
          }

          // Assign the new password
          user.setPassword(passwordEncoder.encode(request.getNewPassword()));
          userRepository.save(user);
     }


     @Transactional
     public  void deleteUser(UUID id){
          User user = userRepository.findById(id)
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

          userRepository.delete(user);
     }
}
