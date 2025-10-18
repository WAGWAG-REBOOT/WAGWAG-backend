package com.noname.wagwag.model.response;

import com.noname.wagwag.repository.entity.User;
import lombok.Data;

@Data
public class UserSummaryResponse {
    
    private Long userId;
    private String nickName;
    private String dongCode;
    private String userState;
    
    public static UserSummaryResponse from(User user) {
        UserSummaryResponse response = new UserSummaryResponse();
        response.setUserId(user.getUserId());
        response.setNickName(user.getNickName());
        response.setDongCode(user.getDongCode());
        response.setUserState(user.getUserState());
        return response;
    }
}
