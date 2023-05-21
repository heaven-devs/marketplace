package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.service.AdsService;
import ga.heaven.marketplace.service.ImageService;
import ga.heaven.marketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://marketplace.heaven.ga", "http://localhost:3000"})
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;
    private final ImageService imageService;
    private final UserService userService;
    
    public AdsController(AdsService adsService, ImageService imageService, UserService userService) {
        this.adsService = adsService;
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
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    )
            }
    )
    // -------------------------------------------------------------------------
    // РЕАЛИЗОВАНО
    @GetMapping
    public ResponseEntity<?> getAds() {
        ResponseWrapperAds rs = adsService.getAds();
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
                                    schema = @Schema(implementation = Ads.class)
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
    public ResponseEntity<?> addAds(@RequestPart("properties") CreateAds properties,
                                    @RequestPart("image") MultipartFile imageFile,
                                    Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    
        ImageModel uploadedImage = imageService.upload(imageFile);
        ImageModel savedImage = imageService.save(uploadedImage);
        
        Ads ads = adsService.addAds(properties, savedImage, authentication.getName());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ads);
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
                                    schema = @Schema(implementation = FullAdds.class)
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
    public ResponseEntity<FullAdds> getFullAd(@PathVariable long id, Authentication authentication) {
        if (null == authentication) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        FullAdds fullAdds = adsService.getFullAd(id);
        return ResponseEntity.ok(fullAdds);


        // Хотелось сделать так, чтобы не позволять смотреть чужие объявления. В спецификации стоит статус OK
        /*if (fullAdds == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (fullAdds.getEmail() != authentication.getName()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok(fullAdds);
        }*/
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
    public ResponseEntity<?> removeAds(@PathVariable long id, Authentication authentication) {
        ResponseEntity<?> response = accessResponse(authentication, id);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return response; // UNAUTHORIZED, FORBIDDEN or NO_CONTENT
        } else {
            adsService.removeAds(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        /*switch (adsService.removeAds(id)) {
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            case 0:
                return ResponseEntity.status(HttpStatus.OK).build();
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }*/
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
                                    schema = @Schema(implementation = Ads.class)
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
    public ResponseEntity<?> updateAds(@PathVariable long id, @RequestBody CreateAds createAds, Authentication authentication) {

        ResponseEntity<?> response = accessResponse(authentication, id);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            //return response; // UNAUTHORIZED or FORBIDDEN   // в этом случае подхватывается и NO_CONTENT
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            Ads ads = adsService.updateAds(id, createAds);
            return ResponseEntity.ok(ads);
        }
    }
    // -------------------------------------------------------------------------

    private ResponseEntity<?> accessResponse(Authentication authentication, Long id) {
        if (null == authentication) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AdsModel adsModel = adsService.getAdsById(id);

        if (adsModel == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else if (adsModel.getUser().getUsername().equals(authentication.getName()) ||
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
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
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
            ResponseWrapperAds rs = adsService.getAdsMe(authentication.getName());
//            List<Ads> ads = adsService.getAdsMe(authentication.getName());
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
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Byte.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @SneakyThrows
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable int id,
                                            @RequestPart("image") MultipartFile imageFile,
                                            Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        ImageModel image = imageService.upload(imageFile);
        AdsModel ads = adsService.getAdsById(id);
        return ResponseEntity.ok(adsService.updateAdsImage(ads, image).getImage().getImage());
    }
    /*@PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable int id, @RequestPart MultipartFile image, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok(null);
    }*/

    // Поиск объявлений по подстроке в title с IgnoreCase
    // Параметр подстроки передается из формы фронтенд части, поэтому в будущем, @PathVariable скорее всего поменяется
    @GetMapping("/findbytitle/{searchTitle}")
    public ResponseEntity<?> searchAds(@PathVariable String searchTitle) {
        return ResponseEntity.ok(
                adsService.findByTitleContainingIgnoreCase(searchTitle)
        );
    }

}