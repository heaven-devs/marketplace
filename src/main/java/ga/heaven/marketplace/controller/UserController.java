package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.SetPasswordDto;
import ga.heaven.marketplace.dto.UserDto;
import ga.heaven.marketplace.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://locallhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private UserServiceImpl userService;

    @Operation(
            tags = "Пользователи",
            summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован (unauthorized)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен (forbidden)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден (not found)",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        UserDto user = userService.getCurrentUser();
        if (null == user) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи",
            summary = "Обновить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь обнавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Пустой запрос (no content)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован (unauthorized)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен (forbidden)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден (not found)",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDto user) {
        if (null == user) {
            return ResponseEntity.noContent().build();
        }

        UserDto editedUser = userService.updateUser(user);
        if (null == editedUser) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editedUser);
    }

    @Operation(
            tags = "Пользователи",
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пароль обновлен",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован (unauthorized)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен (forbidden)",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден (not found)",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setUserPassword(@RequestBody SetPasswordDto passwordDto) {
        UserDto editedUser = userService.setUserPassword(passwordDto);
        if (null == editedUser) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


}
