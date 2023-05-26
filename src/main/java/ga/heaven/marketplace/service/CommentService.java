package ga.heaven.marketplace.service;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.RequestWrapperCommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperCommentsDto;

public interface CommentService {

    /**
     * Get comment for current ad
     * @param rq current ad
     * @return list of comments
     */
    ResponseWrapperCommentsDto getComments(RequestWrapperCommentDto rq);

    /**
     * Add comment
     * @param rq current ad and new comment
     * @return edited comment
     */
    CommentDto addComment(RequestWrapperCommentDto rq);
    
    boolean isMine(RequestWrapperCommentDto rq);
    
    CommentDto deleteComment(RequestWrapperCommentDto rq);

    CommentDto updateComment(RequestWrapperCommentDto rq);

}
