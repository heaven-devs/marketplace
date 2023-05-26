package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.model.AdModel;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.service.AdService;
import ga.heaven.marketplace.service.ImageService;
import ga.heaven.marketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import static ga.heaven.marketplace.config.Constants.*;

@RestController
@CrossOrigin(origins = {"http://marketplace.heaven.ga", "http://localhost:3000"})
@RequestMapping(AD_RM)
public class AdController {
    private final AdService adService;
    private final ImageService imageService;
    private final UserService userService;
    
    public AdController(AdService adService, ImageService imageService, UserService userService) {
        this.adService = adService;
        this.imageService = imageService;
        this.userService = userService;
    }
    
    @Operation(
            tags = "Объявления",
            summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAdsDto.class)
                            )
                    )
            }
    )
    // -------------------------------------------------------------------------
    // РЕАЛИЗОВАНО
    @GetMapping
    public ResponseEntity<?> getAds() {
        ResponseWrapperAdsDto rs = adService.getAds();
        return ResponseEntity.ok(rs);
    }
    // ---------------------------------------------------------------------------
    
    @Operation(
            tags = "Объявления",
            summary = "Добавить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    
    // -------------------------------------------------------------------------
    @SneakyThrows
    @Transactional
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAds(@RequestPart("properties") CreateAdDto properties,
                                    @RequestPart("image") MultipartFile imageFile,
                                    Authentication authentication) {
    
        ImageModel uploadedImage = imageService.upload(imageFile);
        ImageModel savedImage = imageService.save(uploadedImage);
        
        AdDto adDto = adService.addAds(properties, savedImage, authentication.getName());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(adDto);
    }
    // -------------------------------------------------------------------------

    @Operation(
            tags = "Объявления",
            summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FullAdDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    // -------------------------------------------------------------------------
    // РЕАЛИЗОВАНО. Спринт 4
    @GetMapping("/{id}")
    public ResponseEntity<FullAdDto> getFullAd(@PathVariable long id, Authentication authentication) {
        if (null == authentication) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        FullAdDto fullAdDto = adService.getFullAd(id);
        return ResponseEntity.ok(fullAdDto);

    }
    // -------------------------------------------------------------------------

    @Operation(
            tags = "Объявления",
            summary = "Удалить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )

    // -------------------------------------------------------------------------
    //РЕАЛИЗОВАНО Спрринт4 (ПРОВЕРИТЬ УДАЛЕНИЕ, FORBIDDEN РАБОТАЕТ)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable long id, Authentication authentication) {
        ResponseEntity<?> response = accessResponse(authentication, id);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            adService.removeAd(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    // -------------------------------------------------------------------------

    @Operation(
            tags = "Объявления",
            summary = "Обновить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )
    // -------------------------------------------------------------------------
    // РЕАЛИЗОВАНО Спринт4
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAd(@PathVariable long id, @RequestBody CreateAdDto createAdDto, Authentication authentication) {

        ResponseEntity<?> response = accessResponse(authentication, id);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            AdDto adDto = adService.updateAd(id, createAdDto);
            return ResponseEntity.ok(adDto);
        }
    }
    // -------------------------------------------------------------------------

    private ResponseEntity<?> accessResponse(Authentication authentication, Long id) {
        AdModel adModel = adService.getAdById(id);

        if (adModel == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else if (adModel.getUser().getUsername().equals(authentication.getName()) ||
                userService.getUser(authentication.getName()).getRole() == Role.ADMIN) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    // -------------------------------------------------------------------------

    @Operation(
            tags = "Объявления",
            summary = "Получить объявления авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAdsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )

    @GetMapping("me")
    public ResponseEntity<?> getAdsMe(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            ResponseWrapperAdsDto rs = adService.getAdsMe(authentication.getName());
            return ResponseEntity.ok(rs);
        }
    }

    @Operation(
            tags = "Объявления",
            summary = "Обновить картинку объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )
    @SneakyThrows
    @Transactional
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdImage(@PathVariable Long id,
                                           @RequestPart("image") MultipartFile imageFile,
                                           Authentication authentication) {
    
        ResponseEntity<?> response = accessResponse(authentication, id);
    
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            ImageModel imageModel = imageService.upload(imageFile);
            
            AdModel adModel = adService.getAdById(id);
            
            //return ResponseEntity.ok(adService.updateAdImage(adModel, imageModel).getImage().getImage());
    
            if (null != imageModel) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(imageModel.getMediaType()));
                headers.setContentLength(imageModel.getSize());
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(adService.updateAdImage(adModel, imageModel).getImage().getImage());
            } else {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
    
    
    
    
        
        
        
        
        
        
        
        
    }

    // Поиск объявлений по подстроке в title с IgnoreCase
    // Параметр подстроки передается из формы фронтенд части, поэтому в будущем, @PathVariable скорее всего поменяется
    @GetMapping("/findbytitle/{searchTitle}")
    public ResponseEntity<?> searchAds(@PathVariable String searchTitle) {
        return ResponseEntity.ok(
                adService.findByTitleContainingIgnoreCase(searchTitle)
        );
    }

}