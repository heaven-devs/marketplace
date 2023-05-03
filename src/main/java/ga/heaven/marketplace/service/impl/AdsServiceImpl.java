package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.mapper.AdsMapper;
import ga.heaven.marketplace.mapper.CommentMapper;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.model.CommentModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.AdsRepository;
import ga.heaven.marketplace.repository.CommentRepository;
import ga.heaven.marketplace.repository.UserRepository;
import ga.heaven.marketplace.service.AdsService;
import ga.heaven.marketplace.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    
    public AdsServiceImpl(
            AdsRepository adsRepository,
            CommentRepository commentRepository,
            UserRepository userRepository, CommentMapper commentMapper,
            UserService userService) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }
    
    
    public List<Ads> getAds() {
        //List<Ads> ads = new ArrayList<>();
         List<AdsModel> adsModels = adsRepository.findAll();
        List<Ads> ads = adsModels.stream().map(a-> {
            //return AdsMapper.INSTANCE.adsModelToAds(a);
            return AdsMapper.adsModelToAds(a);
        }).collect(Collectors.toList());
        return ads;
    }
    
    public void addAds(CreateAds properties, MultipartFile image) {
        AdsModel adsModel = AdsMapper.CreateAdsToAdsModel(properties);
        // ДОБАВИТЬ ЛОГИКУ ПО IMAGE и по USER
        adsRepository.save(adsModel);
    }
    
    
    @Override
    public ResponseWrapperComment getComments(Long id) {
        List<CommentModel> commentList = commentRepository.findAllByAds_Id(id);
        ResponseWrapperComment wrapperComment = new ResponseWrapperComment();
        
        if (commentList.isEmpty()) {
            wrapperComment.setCount(0);
            wrapperComment.setResults(new ArrayList<>() {
            });
        } else {
            wrapperComment.setCount(commentList.size());
            wrapperComment.setResults(
                    commentList.stream()
                            .map(commentMapper::mapCommentModelToCommentDto)
                            .collect(Collectors.toList())
            );
            
        }
        
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
    public FullAdds getFullAd(long id) {
        AdsModel adsModel = adsRepository.findById(id).orElse(null);
        return AdsMapper.adsModelToFullAdds(adsModel);
    }
    
    @Override
    public int removeAds(long id) {
        if (adsRepository.findById(id).isEmpty()) {
            return 204; // не найден
        } else {
            adsRepository.deleteById(id);
            return 0; // запись удалена
        }
    }
    
    @Override
    public int updateAds(int id, CreateAds createAds) {
        return 0;
    }
    
    @Override
    public Optional<CommentDto> deleteComments(int adId, int id) {
        return Optional.empty();
    }
    
    @Override
    public Optional<CommentDto> updateComments(int adId, int commentId) {
        return Optional.empty();
    }
    
    @Override
    public List<Ads> getAdsMe() {
        return null;
    }
    
    @Override
    public int updateAdsImage(int id, MultipartFile image) {
        return 0;
    }
}
