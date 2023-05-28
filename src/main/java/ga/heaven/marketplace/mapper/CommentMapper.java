package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.dto.RequestWrapperCommentDto;
import ga.heaven.marketplace.model.CommentModel;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.model.UserModel;
import ga.heaven.marketplace.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;


@Component
public class CommentMapper {
    protected UserRepository userRepository;
    
    public CommentMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get ad id from RequestWrapperCommentDto
     * @param requestWrapperCommentDto original dto
     * @return ad id from RequestWrapperCommentDto
     */
    public Long adIdFromRequestWrapperDto(RequestWrapperCommentDto requestWrapperCommentDto) {
        return requestWrapperCommentDto.getAdId().longValue();
    }

    /**
     * Mapping RequestWrapperCommentDto to Comment model
     * @param requestWrapperCommentDto original dto
     * @return resulting CommentModel of type requestWrapperCommentDto
     */
    public CommentModel commentModelFromRequestWrapperDto(RequestWrapperCommentDto requestWrapperCommentDto) {
        return mapCommentDtoToCommentModel(requestWrapperCommentDto.getData());
    }

    /**
     * Mapping Comment model to Comment dto
     * @param commentModel original model
     * @return resulting Comment dto
     */
    public CommentDto mapCommentModelToCommentDto(CommentModel commentModel) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(Math.toIntExact(commentModel.getId()));
        commentDto.setAuthor(Math.toIntExact(commentModel.getUser().getId()));
        commentDto.setAuthorImage(Optional.ofNullable(commentModel.getUser())
                        .map(UserModel::getImage)
                        .map(ImageModel::getPath)
                        .orElse(null));
        commentDto.setAuthorFirstName(commentModel.getUser().getFirstName());

//        commentDto.setCreatedAt(Timestamp.valueOf(commentModel.getCreatedAt()).getTime());
//        commentDto.setCreatedAt(commentModel.getCreatedAt().atZone(ZoneId.of("Etc/GMT-3")).toInstant().toEpochMilli());
        commentDto.setCreatedAt(commentModel.getCreatedAt().toInstant(ZoneOffset.ofHours(3)).toEpochMilli());
        commentDto.setText(commentModel.getText());
        return commentDto;
    }

    /**
     * Mapping Comment dto to Comment model
     * @param commentDto original dto
     * @return resulting Comment model
     */
    public CommentModel mapCommentDtoToCommentModel(CommentDto commentDto) {
        CommentModel commentModel = new CommentModel();
        if (null != commentDto.getPk()) {
            commentModel.setId(commentDto.getPk().longValue());
        }
        if (null != commentDto.getCreatedAt()) {
            commentModel.setCreatedAt(LocalDateTime.ofEpochSecond(commentDto.getCreatedAt(),0,ZoneOffset.ofHours(3)));
        }
        
//        commentModel.setId(commentDto.getPk().longValue());
//        commentModel.setCreatedAt(new Timestamp(commentDto.getCreatedAt()).toLocalDateTime());
//        commentModel.setCreatedAt(LocalDateTime.ofEpochSecond(commentDto.getCreatedAt(),0,ZoneOffset.ofHours(3)));
        if (null != commentDto.getText()) {
            commentModel.setText(commentDto.getText());
        }
        
        if (null != commentDto.getAuthor()) {
        commentModel.setUser(userRepository
                .findById(commentDto.getAuthor().longValue())
                .orElse(null))
        ;
        }
        return commentModel;
    }
    
}