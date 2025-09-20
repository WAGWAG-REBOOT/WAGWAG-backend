package com.noname.wagwag.service;

import com.noname.wagwag.model.request.CreateUserRequest;
import com.noname.wagwag.model.request.UpdateUserRequest;
import com.noname.wagwag.model.response.UserResponse;
import com.noname.wagwag.model.response.UserSummaryResponse;
import com.noname.wagwag.repository.UserRepository;
import com.noname.wagwag.repository.entity.SocialType;
import com.noname.wagwag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    private final AuthService authService;
    
    /**
     * 새 사용자 생성
     */
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating new user with email: {}", request.getEmail());
        
        // 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }
        
        if (userRepository.existsByNickName(request.getNickName())) {
            throw new RuntimeException("Nickname already exists: " + request.getNickName());
        }
        
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Username already exists: " + request.getUserName());
        }
        
        // 사용자 생성
        User user = User.builder()
                .userName(request.getUserName())
                .nickName(request.getNickName())
                .age(request.getAge())
                .email(request.getEmail())
                .dongCode(request.getDongCode())
                .socialType(request.getSocialType())
                .phoneCode(request.getPhoneCode())
                .field(request.getField())
                .userState("ACTIVE") // 기본 상태
                .created(new Date())
                .lastUpdated(new Date())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getUserId());
        
        return UserResponse.from(savedUser);
    }
    
    /**
     * 사용자 ID로 조회
     */
    public UserResponse getUserById(Long userId) {
        log.info("Getting user by ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        return UserResponse.from(user);
    }
    
    /**
     * 이메일로 사용자 조회
     */
    public UserResponse getUserByEmail(String email) {
        log.info("Getting user by email: {}", email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        
        return UserResponse.from(user);
    }
    
    /**
     * 닉네임으로 사용자 조회
     */
    public UserResponse getUserByNickName(String nickName) {
        log.info("Getting user by nickname: {}", nickName);
        
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new RuntimeException("User not found with nickname: " + nickName));
        
        return UserResponse.from(user);
    }
    
    /**
     * 모든 사용자 조회
     */
    public List<UserSummaryResponse> getAllUsers() {
        log.info("Getting all users");
        
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserSummaryResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 동 코드로 사용자 조회
     */
    public List<UserSummaryResponse> getUsersByDongCode(String dongCode) {
        log.info("Getting users by dong code: {}", dongCode);
        
        List<User> users = userRepository.findByDongCode(dongCode);
        return users.stream()
                .map(UserSummaryResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 소셜 타입으로 사용자 조회
     */
    public List<UserSummaryResponse> getUsersBySocialType(SocialType socialType) {
        log.info("Getting users by social type: {}", socialType);
        
        List<User> users = userRepository.findBySocialType(socialType);
        return users.stream()
                .map(UserSummaryResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 나이 범위로 사용자 조회
     */
    public List<UserSummaryResponse> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        log.info("Getting users by age range: {} - {}", minAge, maxAge);
        
        List<User> users = userRepository.findByAgeBetween(minAge, maxAge);
        return users.stream()
                .map(UserSummaryResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자 정보 업데이트
     */
    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // 닉네임 중복 검사 (변경하는 경우만)
        if (request.getNickName() != null && !request.getNickName().equals(user.getNickName())) {
            if (userRepository.existsByNickName(request.getNickName())) {
                throw new RuntimeException("Nickname already exists: " + request.getNickName());
            }
            user.setNickName(request.getNickName());
        }
        
        // 필드별 업데이트
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }
        if (request.getDongCode() != null) {
            user.setDongCode(request.getDongCode());
        }
        if (request.getPhoneCode() != null) {
            user.setPhoneCode(request.getPhoneCode());
        }
        if (request.getField() != null) {
            user.setField(request.getField());
        }
        if (request.getUserState() != null) {
            user.setUserState(request.getUserState());
        }
        
        // 업데이트 시간 설정
        user.setLastUpdated(new Date());
        user.setUpdatedAt(Instant.now());
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully: {}", updatedUser.getUserId());
        
        return UserResponse.from(updatedUser);
    }
    
    /**
     * 사용자 삭제 (논리 삭제)
     */
    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // 논리 삭제
        user.setUserState("DELETED");
        user.setLastUpdated(new Date());
        user.setUpdatedAt(Instant.now());
        
        userRepository.save(user);
        log.info("User deleted successfully: {}", userId);
    }
    
    /**
     * 사용자 물리 삭제
     */
    @Transactional
    public void permanentlyDeleteUser(Long userId) {
        log.info("Permanently deleting user with ID: {}", userId);
        
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        
        userRepository.deleteById(userId);
        log.info("User permanently deleted: {}", userId);
    }
    
    /**
     * 닉네임 존재 여부 확인 (AuthService 연동)
     * 주의: AuthService.nicknameExists()는 userName 필드를 체크함
     */
    public boolean nicknameExists(String nickname) {
        log.info("Checking if nickname exists: {}", nickname);
        return authService.nicknameExists(nickname);
    }
    
    /**
     * 이메일 존재 여부 확인
     */
    public boolean emailExists(String email) {
        log.info("Checking if email exists: {}", email);
        return userRepository.existsByEmail(email);
    }
    
    /**
     * 사용자 상태 변경
     */
    @Transactional
    public UserResponse updateUserState(Long userId, String newState) {
        log.info("Updating user state for ID: {} to: {}", userId, newState);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        user.setUserState(newState);
        user.setLastUpdated(new Date());
        user.setUpdatedAt(Instant.now());
        
        User updatedUser = userRepository.save(user);
        log.info("User state updated successfully: {}", updatedUser.getUserId());
        
        return UserResponse.from(updatedUser);
    }
    
    /**
     * 소셜 타입별 사용자 수 조회
     */
    public Long countUsersBySocialType(SocialType socialType) {
        log.info("Counting users by social type: {}", socialType);
        return userRepository.countBySocialType(socialType);
    }
    
    /**
     * 온보딩 완료 (AuthService 연동)
     * NOTREADY → ACTIVE 상태 변경
     */
    @Transactional
    public UserResponse completeOnboarding(Long userId, String nickname, String dongCode) {
        log.info("Completing onboarding for user: {} with nickname: {}", userId, nickname);
        
        User user = authService.finishOnboarding(userId, nickname, dongCode);
        return UserResponse.from(user);
    }
    
    /**
     * 사용자 상태별 조회
     */
    public List<UserSummaryResponse> getUsersByState(String userState) {
        log.info("Getting users by state: {}", userState);
        
        List<User> users = userRepository.findByUserState(userState);
        return users.stream()
                .map(UserSummaryResponse::from)
                .collect(Collectors.toList());
    }
}
