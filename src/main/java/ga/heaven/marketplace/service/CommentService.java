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
     * Add new comment
     * @param rq current ad and new comment
     * @return edited comment
     */
    CommentDto addComment(RequestWrapperCommentDto rq);

    /**
     * Checks if I am the owner of the comment
     * @param rq verifiable comment
     * @return true if that comment is mine
     */
    boolean isMine(RequestWrapperCommentDto rq);

    /**
     * Delete comment
     * @param rq deleted comment
     * @return deleted comment
     */
    CommentDto deleteComment(RequestWrapperCommentDto rq);

    /**
     * Update comment
     * @param rq updated comment
     * @return updated comment
     */
    CommentDto updateComment(RequestWrapperCommentDto rq);

}
