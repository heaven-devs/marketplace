package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperComment;
import ga.heaven.marketplace.mapper.CommentMapper;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.model.CommentModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.AdsRepository;
import ga.heaven.marketplace.repository.CommentRepository;
import ga.heaven.marketplace.repository.UserRepository;
import ga.heaven.marketplace.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    
    public CommentServiceImpl(
            AdsRepository adsRepository,
            CommentRepository commentRepository,
            UserRepository userRepository, CommentMapper commentMapper) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }
    
    
    @Override
    public ResponseWrapperComment getComments(Long id) {
        List<CommentModel> commentList = commentRepository.findAllByAds_Id(id);
        if (commentList.isEmpty()) {
            return null;
        }
        ResponseWrapperComment wrapperComment = new ResponseWrapperComment();
        wrapperComment.setCount(commentList.size());
        wrapperComment.setResults(
                commentList.stream()
                        .map(commentMapper::mapCommentModelToCommentDto)
                        .collect(Collectors.toList())
        );
        return wrapperComment;
    }
    
    @Override
    public CommentDto addComments(Integer id, CommentDto comment, String username) {
        AdsModel adsModel =
                adsRepository.findById(id.longValue())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserModel userModel = userRepository.findUserByUsername(username).orElse(null);
        
        CommentModel commentModel = commentMapper.mapCommentDtoToCommentModel(comment);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUser(userModel);
        commentModel.setAds(adsModel);
        return commentMapper.mapCommentModelToCommentDto(
                commentRepository.save(commentModel)
        );
    }
    
    @Override
    public Optional<CommentDto> deleteComments(int adId, int id) {
        return Optional.empty();
    }
    
    @Override
    public Optional<CommentDto> updateComments(int adId, int commentId) {
        return Optional.empty();
    }
    
}
