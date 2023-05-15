package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.RequestWrapperCommentDto;
import ga.heaven.marketplace.dto.ResponseWrapperCommentDto;
import ga.heaven.marketplace.mapper.CommentMapper;
import ga.heaven.marketplace.mapper.UserMapper;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.model.CommentModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.AdsRepository;
import ga.heaven.marketplace.repository.CommentRepository;
import ga.heaven.marketplace.repository.UserRepository;
import ga.heaven.marketplace.service.CommentService;
import ga.heaven.marketplace.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final AdsRepository adRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    
    public CommentServiceImpl(
            AdsRepository adRepository,
            CommentRepository commentRepository,
            UserRepository userRepository, UserService userService,
            CommentMapper commentMapper,
            UserMapper userMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
    }
    
    @Override
    public ResponseWrapperCommentDto getComments(RequestWrapperCommentDto rq) {
        List<CommentModel> commentList = commentRepository
                .findAllByAds_Id(commentMapper.adIdFromRequestWrapperDto(rq));
        ResponseWrapperCommentDto wrapperComment = new ResponseWrapperCommentDto();
        wrapperComment.setCount(commentList.size());
        wrapperComment.setResults(
                commentList.stream()
                        .map(commentMapper::mapCommentModelToCommentDto)
                        .collect(Collectors.toList())
        );
        return wrapperComment;
    }
    
    @Override
    public CommentDto addComment(RequestWrapperCommentDto rq) {
        Long adId = commentMapper.adIdFromRequestWrapperDto(rq);
        Optional<AdsModel> adModelOptional =
                adRepository.findById(adId);
        if (adModelOptional.isEmpty()) {
            return null;
        }
        UserModel userModel = userService.getUser(rq.getUsername());
//        UserModel userModel = userMapper.mapUserDtoToUserModel(userService.getCurrentUser());
        CommentModel commentModel = commentMapper.commentModelFromRequestWrapperDto(rq);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUser(userModel);
        commentModel.setAds(adModelOptional.get());
    
/*        Example<CommentModel> c = Example.of(commentModel);
        boolean exists = commentRepository.exists(c);
        if (!exists) {*/
        
        if (null == findComment(commentModel)) {
            return commentMapper.mapCommentModelToCommentDto(
                    commentRepository.save(commentModel)
            );
        }
        return null;
    }
    
    private CommentModel findComment(CommentModel commentModel) {
        return commentRepository.findDistinctFirstByTextEqualsAndUserEqualsAndAdsEquals(commentModel.getText(), commentModel.getUser(), commentModel.getAds()).orElse(null);
    }
    
    private CommentModel findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
    
    @Override
    public boolean isMine(RequestWrapperCommentDto rq) {
        Long id = commentMapper.commentModelFromRequestWrapperDto(rq).getId();
        CommentModel commentModel = findCommentById(id);
        if (null == commentModel) {
            return false;
        }
        return commentModel.getUser().getUsername().equals(rq.getUsername());
    }
    
    @Override
    public CommentDto deleteComment(RequestWrapperCommentDto rq) {
        Long id = commentMapper.commentModelFromRequestWrapperDto(rq).getId();
        CommentModel commentModel = findCommentById(id);
        if (null == commentModel) {
            return null;
        }
        commentRepository.deleteById(id);
        return commentMapper.mapCommentModelToCommentDto(commentModel);
    }
    
    @Override
    public CommentDto updateComment(RequestWrapperCommentDto rq) {
        Long id = commentMapper.commentModelFromRequestWrapperDto(rq).getId();
        CommentModel commentModel = findCommentById(id);
        if (null == commentModel) {
            return null;
        }
        if (commentMapper.commentModelFromRequestWrapperDto(rq).getText().equals(commentModel.getText())) {
            return null;
        }
        commentModel.setText(commentMapper.commentModelFromRequestWrapperDto(rq).getText());
        return commentMapper.mapCommentModelToCommentDto(
                commentRepository.save(commentModel)
        );
    }
}
