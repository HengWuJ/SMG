package com.smg.user.controller;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.smg.pojo.Log;
import com.smg.pojo.User;
import com.smg.user.CodeCompents;
import com.smg.user.configuration.BaiduAiConfig;
import com.smg.user.feign.LogFeignClient;
import com.smg.user.service.UserService;
import com.smg.user.util.Base64Util;
import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LogFeignClient logFeignClient;

    private void createLog(String content, String user, boolean successful) {
        Log log = new Log();
        log.setContent(content);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setSuccessful(successful ? 1 : 0);
        logFeignClient.createLog(log);
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Request received to fetch all users.");
        createLog("Fetching all users.", "System", true);
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        logger.info("Request received to fetch user by ID: {}", id);
        createLog(String.format("Fetching user by ID: %d", id), "System", true);
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("Request received to create a new user: {}", user.getUsername());
        createLog(String.format("Creating a new user: %s", user.getUsername()), user.getUsername(), true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(User.Status.active);

        userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        if (id != null && id > 0) {
            Optional<User> optionalExistingUser = userService.getUserById(id);

            if (!optionalExistingUser.isPresent()) {
                createLog(String.format("Update request failed for non-existent user ID: %d", id), "System", false);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User existingUser = optionalExistingUser.get();

            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword()); // 注意：实际应用中应加密密码
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setFullName(userDetails.getFullName());
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            existingUser.setRole(userDetails.getRole());
            existingUser.setUpdatedAt(LocalDateTime.now());

            userService.updateUser(existingUser);
            createLog(String.format("User with ID: %d updated successfully.", id), userDetails.getUsername(), true);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Invalid update request for user with ID: {}", id);
            createLog(String.format("Invalid update request for user with ID: %d", id), "System", false);
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (id != null && id > 0) {
            Optional<User> optionalUser = userService.getUserById(id);
            if (!optionalUser.isPresent()) {
                createLog(String.format("Delete request failed for non-existent user ID: %d", id), "System", false);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            userService.deleteUser(id);
            logger.info("User with ID: {} deleted successfully.", id);
            createLog(String.format("User with ID: %d deleted successfully.", id), "System", true);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Invalid delete request for user with ID: {}", id);
            createLog(String.format("Invalid delete request for user with ID: %d", id), "System", false);
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Integer id) {
        if (id != null && id > 0) {
            Optional<User> optionalUser = userService.getUserById(id);
            if (!optionalUser.isPresent()) {
                createLog(String.format("Activate request failed for non-existent user ID: %d", id), "System", false);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User user = optionalUser.get();
            user.setStatus(User.Status.active);
            userService.updateUser(user);
            logger.info("User with ID: {} activated successfully.", id);
            createLog(String.format("User with ID: %d activated successfully.", id), "System", true);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Invalid activation request for user with ID: {}", id);
            createLog(String.format("Invalid activation request for user with ID: %d", id), "System", false);
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Integer id) {
        if (id != null && id > 0) {
            Optional<User> optionalUser = userService.getUserById(id);
            if (!optionalUser.isPresent()) {
                createLog(String.format("Deactivate request failed for non-existent user ID: %d", id), "System", false);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User user = optionalUser.get();
            user.setStatus(User.Status.inactive);
            userService.updateUser(user);
            logger.info("User with ID: {} deactivated successfully.", id);
            createLog(String.format("User with ID: %d deactivated successfully.", id), "System", true);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Invalid deactivation request for user with ID: {}", id);
            createLog(String.format("Invalid deactivation request for user with ID: %d", id), "System", false);
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/suspend")
    public ResponseEntity<?> suspendUser(@PathVariable Integer id) {
        if (id != null && id > 0) {
            Optional<User> optionalUser = userService.getUserById(id);
            if (!optionalUser.isPresent()) {
                createLog(String.format("Suspend request failed for non-existent user ID: %d", id), "System", false);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User user = optionalUser.get();
            user.setStatus(User.Status.suspended);
            userService.updateUser(user);
            logger.info("User with ID: {} suspended successfully.", id);
            createLog(String.format("User with ID: %d suspended successfully.", id), "System", true);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Invalid suspension request for user with ID: {}", id);
            createLog(String.format("Invalid suspension request for user with ID: %d", id), "System", false);
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<?> resetUserPassword(@PathVariable Integer id, @RequestParam String newPassword) {
        if (id != null && id > 0 && newPassword != null && !newPassword.trim().isEmpty()) {
            Optional<User> optionalUser = userService.getUserById(id);
            if (!optionalUser.isPresent()) {
                createLog(String.format("Password reset failed for non-existent user ID: %d", id), "System", false);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User user = optionalUser.get();
            user.setPassword(newPassword);
            userService.updateUser(user);
            logger.info("Password reset for user with ID: {} successfully.", id);
            createLog(String.format("Password reset for user with ID: %d successfully.", id), user.getUsername(), true);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Invalid password provided or invalid user ID for reset.");
            createLog("Invalid password provided or invalid user ID for reset.", "System", false);
            return new ResponseEntity<>("Invalid user ID or password", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        logger.info("Login request received for user: {}", username);
        Optional<User> optionalUser = userService.login(username, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            createLog(String.format("Login successful for user: %s", username), username, true);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            createLog(String.format("Login failed for user: %s", username), username, false);
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }



//    @PostMapping("/registerBaiduAi")
//    public void registerBaiduAi(@RequestParam("file") MultipartFile file ,User user) throws IOException, JSONException {
//        // 传入appId、apiKey、secretkey 创建Java代码和百度云交互的Client对象
    @PostMapping("/registerBaiduAi")
    public void registerBaiduAi(
        @RequestParam("file") MultipartFile file,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("email") String email,
        @RequestParam("fullName") String fullName,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("role") User.Role role,
        @RequestParam("status") User.Status status,
        @RequestParam(value = "profilePicture", required = false) String profilePicture) throws IOException, JSONException {

    // 创建 User 对象
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    user.setFullName(fullName);
    user.setPhoneNumber(phoneNumber);
    user.setRole(role);
    user.setStatus(status);
    user.setProfilePicture(profilePicture);
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());

    if (user == null) {
            logger.warn("Invalid user information provided for Baidu AI registration.");
        }
        System.out.println("66666666666666666 user:"+user);
        AipFace client = new AipFace(BaiduAiConfig.AppId, BaiduAiConfig.AK, BaiduAiConfig.SK);
        // 根据用户名获取用户信息
        // redis项目可以删除userName参数，直接从redis或者token中获取用户名
        // MultipartFile类型转换Base64字符串格式
        String registerImageBase64 = Base64Util.multipartFileToBase64(file);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", user.getUsername());
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("action_type", "REPLACE");

        /*
         调用api方法完成人脸注册
         image 图片的url或者base64字符串
         imageType 图片形式（URL，BASE64）
         groupId 组Id（固定一个字符串）
         userId 用户Id
         options hashMap基本参数配置
         */
        JSONObject res = client.addUser(registerImageBase64, CodeCompents.baidu_ai_imageType, CodeCompents.baidu_ai_groupId, user.getUsername(), options);
        if (res.getInt("error_code")==0){

            //获取注册的人脸的唯一标识--方案二
            String faceToken = res.getJSONObject("result").getString("face_token");
            //将人脸信息的唯一标识存入该用户的数据库--方案二
            user.setFaceToken(faceToken);
            //更新数据库
            userService.createUser(user);
            //更新redis中的用户信息
//            updateUser(hmsUser);
            System.out.println("人脸注册成功");
        }else {
            System.out.println("人脸注册失败");
        }
        System.out.println(res.toString(2));
    }


    @PostMapping("/loginBaiduAi")
    public ResponseEntity<String> loginBaiduAi(@RequestParam("file") MultipartFile file ,@RequestParam String username, @RequestParam String password) throws Exception {
        // 传入appId、apiKey、secretkey。创建Java代码和百度云交互的Client对象
        AipFace client = new AipFace(BaiduAiConfig.AppId, BaiduAiConfig.AK, BaiduAiConfig.SK);
        // 登录图片
        // MultipartFile类型转换Base64字符串格式
        String loginImageBase64 = Base64Util.multipartFileToBase64(file);
        // 根据用户名获取用户信息
        User one = userService.findByUsername(username);
        if (!ObjectUtils.isEmpty(one)){
            /*
            判断该用户是否注册了人脸
            如果没有注册则不进行校验
            如果注册了则进行校验
            */
            if (!ObjectUtils.isEmpty(one.getFaceToken())){

                String comparedFaceToken = one.getFaceToken();

                //方案二
                Double faceComparison = faceComparison(client, loginImageBase64, comparedFaceToken);
                if (faceComparison > 85) {
                    System.out.println(one.getUsername());
                    System.out.println("人脸识别登录成功");
                } else {
                    System.out.println("人脸识别登录失败");
                }
            }else {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
        }else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
        return null;
    }


    static Double faceComparison(AipFace client, String loginImageBase64, String comparedFaceToken) throws Exception {
        // 将图片的URL传递给百度API--方案一
//        MatchRequest req2 = new MatchRequest(comparedImageUrl, "URL");
        //将注册时人脸的唯一标识传递给百度API--方案二
        MatchRequest req2 = new MatchRequest(comparedFaceToken, "FACE_TOKEN");
        // 将前端传过来的图片传递给百度API
        MatchRequest req1 = new MatchRequest(loginImageBase64, CodeCompents.baidu_ai_imageType);
        // 讲MatchRequest信息存入list集合中
        ArrayList<MatchRequest> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);

        // 进行人脸比对 返回值是json串
        JSONObject match = client.match(requests);
        System.out.println(match.toString(2));
        // 返回两张照片的相似度
        return match.getJSONObject("result").getDouble("score");
    }


}