package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.NewPasswordDto;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.service.ImageService;
import ga.heaven.marketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import static ga.heaven.marketplace.config.Constants.*;

@CrossOrigin(origins = {"http://marketplace.heaven.ga", "http://localhost:3000"})
@RestController
@RequestMapping(USER_RM)
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    private final ImageService imageService;
    
    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }
    
    @Operation(
            tags = "Пользователи",
            summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        UserDto authUser = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(authUser);
    }
    
    @Operation(
            tags = "Пользователи",
            summary = "Обновить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        UserModel authUser = userService.getUser(authentication.getName());
        if (userDto.getId() != authUser.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.updateUser(authUser, userDto);
        return ResponseEntity.ok(userDto);
    }
    
    @Operation(
            tags = "Пользователи",
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setUserPassword(@RequestBody NewPasswordDto newPasswordDto, Authentication authentication) {
        UserModel user = userService.getUser(authentication.getName());
        
        if (!userService.isPasswordCorrect(user, newPasswordDto.currentPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        UserDto modifiedUserDto = userService.setUserPassword(user, newPasswordDto);
        return ResponseEntity.ok(modifiedUserDto);
    }
    
    @Operation(
            tags = "Пользователи",
            summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @SneakyThrows
    @Transactional
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> loadUserImage(@RequestPart("image") MultipartFile image, Authentication authentication) {
        UserModel authUser = userService.getUser(authentication.getName());
        UserDto userDto = userService.loadUserImage(authUser, imageService.upload(image, OPTIMIZED_AVA_IMAGE_WIDTH));
        //return ResponseEntity.ok(userDto);
        return ResponseEntity.ok().build();
    }
}
