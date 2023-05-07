package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.RequestWrapperCommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperCommentDto;

public interface CommentService {
    ResponseWrapperCommentDto getComments(RequestWrapperCommentDto rq);
    
    CommentDto addComment(RequestWrapperCommentDto rq);
    
    CommentDto deleteComment(RequestWrapperCommentDto rq);

    CommentDto updateComment(RequestWrapperCommentDto rq);

}
