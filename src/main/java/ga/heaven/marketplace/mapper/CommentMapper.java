package ga.heaven.marketplace.mapper;

import ga.heaven.marketplace.dto.CommentDto;
import ga.heaven.marketplace.model.CommentModel;
import ga.heaven.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class CommentMapper {
    @Autowired
    protected UserRepository userRepository;
    
    public CommentDto mapCommentModelToCommentDto(CommentModel commentModel) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(Math.toIntExact(commentModel.getId()));
        commentDto.setAuthor(Math.toIntExact(commentModel.getUser().getId()));
        commentDto.setAuthorImage(commentModel.getUser().getImage());
        commentDto.setAuthorFirstName(commentModel.getUser().getFirstName());
        
        commentDto.setCreatedAt(Timestamp.valueOf(commentModel.getCreatedAt()).getTime());
//        commentDto.setCreatedAt(commentModel.getCreatedAt().atZone(ZoneId.of("Etc/GMT-3")).toInstant().toEpochMilli());
        commentDto.setText(commentModel.getText());
        return commentDto;
    }
    
    public CommentModel mapCommentDtoToCommentModel(CommentDto commentDto) {
        CommentModel commentModel = new CommentModel();
        commentModel.setId(commentDto.getPk().longValue());
        commentModel.setCreatedAt(new Timestamp(commentDto.getCreatedAt()).toLocalDateTime());
        commentModel.setText(commentDto.getText());
        commentModel.setUser(userRepository
                .findById(commentDto.getAuthor().longValue())
                .orElse(null))
        ;
        return commentModel;
    }
}