package com.noname.wagwag.model.response;

import com.noname.wagwag.repository.entity.SocialType;
import com.noname.wagwag.repository.entity.User;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class UserResponse {
    
    private Long userId;
    private String userName;
    private String nickName;
    private Integer age;
    private String email;
    private String dongCode;
    private SocialType socialType;
    private String userState;
    private String phoneCode;
    private String field;
    private Date created;
    private Date lastUpdated;
    private Instant createdAt;
    private Instant updatedAt;
    
    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        response.setNickName(user.getNickName());
        response.setAge(user.getAge());
        response.setEmail(user.getEmail());
        response.setDongCode(user.getDongCode());
        response.setSocialType(user.getSocialType());
        response.setUserState(user.getUserState());
        response.setPhoneCode(user.getPhoneCode());
        response.setField(user.getField());
        response.setCreated(user.getCreated());
        response.setLastUpdated(user.getLastUpdated());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
