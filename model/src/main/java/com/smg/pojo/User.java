package com.smg.pojo;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private LocalDateTime lastLogin;
    private String profilePicture;
    private String faceToken;

    // 用户角色，包括管理员、维护人员和工人
    public enum Role {
        admin, maintenance, worker
    }

    // 登陆状态，包括活跃、非活跃和挂起
    public enum Status {
        active, inactive, suspended
    }
}