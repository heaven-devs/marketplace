package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.RequestWrapperCommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperCommentDto;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.model.CommentModel;
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
@CrossOrigin(origins = {"http://marketplace.heaven.ga", "http://localhost:3000"})
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
                                    schema = @Schema(implementation = ResponseWrapperCommentDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @GetMapping("{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Integer id, Authentication authentication) {
        checkAuth(authentication);
        return ResponseEntity.ok(commentService.getComments(rq().setAdId(id)));
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
                    )
            }
    )
    @PostMapping(value = "{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@PathVariable Integer id, @RequestBody CommentDto comment, Authentication authentication) {
        checkAuth(authentication);
        return ResponseEntity.ok(commentService
                .addComment(
                        rq()
                                .setAdId(id)
                                .setData(comment)
                                .setUsername(authentication.getName())
                )
        );
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
                    )
            }
    )
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId, Authentication authentication) {
        checkAuth(authentication);
        CommentDto comment = new CommentDto();
        comment.setPk(commentId);
        RequestWrapperCommentDto rq = rq()
                .setAdId(adId)
                .setData(comment)
                .setUsername(authentication.getName());
        comment = commentService.isMine(rq) ? commentService.deleteComment(rq) : null;
        checkResult(comment);
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
                    )
            }
    )
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CommentDto comment, Authentication authentication) {
        checkAuth(authentication);
        comment.setPk(commentId);
        RequestWrapperCommentDto rq = rq()
                .setAdId(adId)
                .setData(comment)
                .setUsername(authentication.getName());
        comment = commentService.isMine(rq) ? commentService.updateComment(rq) : null;
        checkResult(comment);
        return ResponseEntity.ok(comment);
    }
    
    private RequestWrapperCommentDto rq() {
        return new RequestWrapperCommentDto();
    }
    
    private void checkAuth(Authentication auth) {
        if (null == auth) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
    
    private void checkResult(CommentDto comment) {
        if (null == comment) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
    
}