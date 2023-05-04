package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperComment;
import ga.heaven.marketplace.service.AdsService;
import ga.heaven.marketplace.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
//@CrossOrigin(value = "http://marketplace.heaven.ga")
//@CrossOrigin(value = "http://localhost:3000")
@CrossOrigin(origins={"http://marketplace.heaven.ga", "http://localhost:3000"})
@RequestMapping("ads")
public class CommentController {
    private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            tags = "Комментарии",
            summary = "Получить комментарии объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperComment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @GetMapping("{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Integer id) {
        ResponseWrapperComment comments = commentService.getComments(id.longValue());
        if (null == comments) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comments);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
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
    @PostMapping(value = "{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComments(@PathVariable Integer id, @RequestBody CommentDto comment, Authentication authentication) {
        CommentDto res = commentService.addComments(id, comment, authentication.getName());
        return ResponseEntity.ok(res);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Удалить комментарий",
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
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable int adId, @PathVariable int commentId) {
        CommentDto comment = commentService.deleteComments(adId, commentId).orElse(null);
        return ResponseEntity.ok(comment);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Обновить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
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
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> updateComments(@PathVariable int adId, @PathVariable int commentId) {
        CommentDto comment = commentService.updateComments(adId, commentId).orElse(null);
        return ResponseEntity.ok(null);
    }

}