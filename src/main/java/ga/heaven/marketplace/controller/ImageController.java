package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ga.heaven.marketplace.config.Constants.*;

@RestController
@CrossOrigin(origins = {"http://marketplace.heaven.ga", "http://localhost:3000"})
@RequestMapping(IMG_RM)
public class ImageController {
    private final ImageService imageService;
    
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    
    @Operation(
            tags = "Картинки",
            summary = "Получить картинку",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(implementation = byte[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NOT_FOUND",
                            content = @Content
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageModel img = imageService.read(id);
        if (null != img) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(img.getMediaType()));
            headers.setContentLength(img.getSize());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(img.getImage());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    
}