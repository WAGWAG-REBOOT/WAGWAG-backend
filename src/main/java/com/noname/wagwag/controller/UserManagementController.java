package com.noname.wagwag.controller;

import com.noname.wagwag.model.request.CreateUserRequest;
import com.noname.wagwag.model.request.UpdateUserRequest;
import com.noname.wagwag.model.response.UserResponse;
import com.noname.wagwag.model.response.UserSummaryResponse;
import com.noname.wagwag.repository.entity.SocialType;
import com.noname.wagwag.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserManagementController {
    
    private final UserService userService;
    
    /**
     * 새 사용자 생성 (관리자용)
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Creating new user with email: {}", request.getEmail());
        
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * 사용자 ID로 조회
     * GET /api/users/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        log.info("Getting user by ID: {}", userId);
        
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 모든 사용자 조회
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserSummaryResponse>> getAllUsers() {
        log.info("Getting all users");
        
        List<UserSummaryResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * 이메일로 사용자 조회
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        log.info("Getting user by email: {}", email);
        
        UserResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 닉네임으로 사용자 조회
     * GET /api/users/nickname/{nickName}
     */
    @GetMapping("/nickname/{nickName}")
    public ResponseEntity<UserResponse> getUserByNickName(@PathVariable String nickName) {
        log.info("Getting user by nickname: {}", nickName);
        
        UserResponse response = userService.getUserByNickName(nickName);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 동 코드로 사용자 조회
     * GET /api/users/dong/{dongCode}
     */
    @GetMapping("/dong/{dongCode}")
    public ResponseEntity<List<UserSummaryResponse>> getUsersByDongCode(@PathVariable String dongCode) {
        log.info("Getting users by dong code: {}", dongCode);
        
        List<UserSummaryResponse> users = userService.getUsersByDongCode(dongCode);
        return ResponseEntity.ok(users);
    }
    
    /**
     * 소셜 타입으로 사용자 조회
     * GET /api/users/social/{socialType}
     */
    @GetMapping("/social/{socialType}")
    public ResponseEntity<List<UserSummaryResponse>> getUsersBySocialType(@PathVariable SocialType socialType) {
        log.info("Getting users by social type: {}", socialType);
        
        List<UserSummaryResponse> users = userService.getUsersBySocialType(socialType);
        return ResponseEntity.ok(users);
    }
    
    /**
     * 나이 범위로 사용자 조회
     * GET /api/users/age?min=20&max=30
     */
    @GetMapping("/age")
    public ResponseEntity<List<UserSummaryResponse>> getUsersByAgeRange(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        log.info("Getting users by age range: {} - {}", min, max);
        
        List<UserSummaryResponse> users = userService.getUsersByAgeRange(min, max);
        return ResponseEntity.ok(users);
    }
    
    /**
     * 사용자 정보 업데이트
     * PUT /api/users/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("Updating user: {}", userId);
        
        UserResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 사용자 상태 변경
     * PATCH /api/users/{userId}/state
     */
    @PatchMapping("/{userId}/state")
    public ResponseEntity<UserResponse> updateUserState(
            @PathVariable Long userId,
            @RequestParam String state) {
        log.info("Updating user state for ID: {} to: {}", userId, state);
        
        UserResponse response = userService.updateUserState(userId, state);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 사용자 삭제 (논리 삭제)
     * DELETE /api/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user: {}", userId);
        
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 사용자 물리 삭제
     * DELETE /api/users/{userId}/permanent
     */
    @DeleteMapping("/{userId}/permanent")
    public ResponseEntity<Void> permanentlyDeleteUser(@PathVariable Long userId) {
        log.info("Permanently deleting user: {}", userId);
        
        userService.permanentlyDeleteUser(userId);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 이메일 중복 검사
     * GET /api/users/validate/email?email={email}
     */
    @GetMapping("/validate/email")
    public ResponseEntity<Map<String, Boolean>> validateEmail(@RequestParam String email) {
        log.info("Validating email: {}", email);
        
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(Map.of("isExist", exists));
    }
    
    /**
     * 소셜 타입별 사용자 수 조회
     * GET /api/users/count/social/{socialType}
     */
    @GetMapping("/count/social/{socialType}")
    public ResponseEntity<Map<String, Long>> countUsersBySocialType(@PathVariable SocialType socialType) {
        log.info("Counting users by social type: {}", socialType);
        
        Long count = userService.countUsersBySocialType(socialType);
        return ResponseEntity.ok(Map.of("count", count));
    }
    
    /**
     * 온보딩 완료 처리
     * POST /api/users/{userId}/complete-onboarding
     */
    @PostMapping("/{userId}/complete-onboarding")
    public ResponseEntity<UserResponse> completeOnboarding(
            @PathVariable Long userId,
            @RequestParam String nickname,
            @RequestParam String dongCode) {
        log.info("Completing onboarding for user: {} with nickname: {}", userId, nickname);
        
        UserResponse response = userService.completeOnboarding(userId, nickname, dongCode);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 사용자 상태별 조회
     * GET /api/users/state/{userState}
     */
    @GetMapping("/state/{userState}")
    public ResponseEntity<List<UserSummaryResponse>> getUsersByState(@PathVariable String userState) {
        log.info("Getting users by state: {}", userState);
        
        List<UserSummaryResponse> users = userService.getUsersByState(userState);
        return ResponseEntity.ok(users);
    }
}
