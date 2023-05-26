package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.RequestWrapperCommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperCommentsDto;

public interface CommentService {
    ResponseWrapperCommentsDto getComments(RequestWrapperCommentDto rq);
    
    CommentDto addComment(RequestWrapperCommentDto rq);
    
    boolean isMine(RequestWrapperCommentDto rq);
    
    CommentDto deleteComment(RequestWrapperCommentDto rq);

    CommentDto updateComment(RequestWrapperCommentDto rq);

}
