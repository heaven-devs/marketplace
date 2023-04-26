package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.NewPassword;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://marketplace.heaven.ga")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        UserDto userDto = userService.getCurrentUser();
        if (null == userDto) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
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
                            responseCode = "204",
                            description = "No Content",
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        if (null == userDto) {
            return ResponseEntity.noContent().build();
        }

        UserDto editedUserDto = userService.updateUser(userDto);
        if (null == editedUserDto) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editedUserDto);
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setUserPassword(@RequestBody NewPassword newPassword) {
        UserDto editedUserDto = userService.setUserPassword(newPassword);
        if (null == editedUserDto) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
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
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/me/image")
    public ResponseEntity<?> loadUserImage(@RequestPart MultipartFile image) {
        UserDto editedUserDto = userService.loadUserImage(image);
        if (null == editedUserDto) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
