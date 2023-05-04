package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperComment;

import java.util.Optional;

public interface CommentService {
    ResponseWrapperComment getComments(Long id);
    
    CommentDto addComments(Integer id, CommentDto comment, String username);
    
    Optional<CommentDto> deleteComments(int adId, int id);

    Optional<CommentDto> updateComments(int adId, int commentId);

}
