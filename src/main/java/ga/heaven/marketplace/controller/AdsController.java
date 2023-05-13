package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.*;
import ga.heaven.marketplace.model.AdsModel;
import ga.heaven.marketplace.service.AdsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins={"http://marketplace.heaven.ga", "http://localhost:3000"})
//@RequestMapping("ads")
public class AdsController {
    private final AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
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
    //@GetMapping
    @GetMapping("/ads")
    public ResponseEntity<?> getAds() {
        /*List<Ads> ads = adsService.getAds();
        return ResponseEntity.ok(ads);*/
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
    // РЕАЛИЗОВАНО ЧАСТИЧНО Спринт4
    @PostMapping(name="/newAd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAds(@RequestPart CreateAds properties, @RequestPart MultipartFile image, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        adsService.addAds(properties, image, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    @GetMapping("/ads/{id}")
    public ResponseEntity<FullAdds> getFullAd(@PathVariable long id, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        FullAdds fullAdds = adsService.getFullAd(id);
        if (fullAdds == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (fullAdds.getEmail() != authentication.getName()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.ok(fullAdds);
        }
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
    @DeleteMapping("/ads/{id}")
    public ResponseEntity<?> removeAds(@PathVariable long id, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        AdsModel adsModel = adsService.getAdsById(id);

        if (adsModel == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else if (adsModel.getUser().getUsername() != authentication.getName()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        switch (adsService.removeAds(id)) {
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            case 0:
                return ResponseEntity.status(HttpStatus.OK).build();
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
    // РЕАЛИЗОВАНО
    @PatchMapping(value = "/ads/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAds(@PathVariable long id, @RequestBody CreateAds createAds, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int result = adsService.updateAds(id, createAds);
        switch (result) {
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            case 0:
                return ResponseEntity.status(HttpStatus.OK).build();
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )

    @GetMapping("me")
    public ResponseEntity<?> getAdsMe(Authentication authentication) {
        List<Ads> ads = adsService.getAdsMe(authentication.getName());
        return ResponseEntity.ok(ads);
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

    // РАБОТА С ККАРТИНКОЙ SPRINT5 ???
    @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable int id, @RequestPart MultipartFile image, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok(null);
    }

    // Поиск объявлений по подстроке в title с IgnoreCase
    // Параметр подстроки передается из формы фронтенд части, поэтому в будущем, @PathVariable скорее всего поменяется
    @GetMapping("/ads/findbytitle/{searchTitle}")
    public ResponseEntity<?> searchAds(@PathVariable String searchTitle, Authentication authentication) {
        if (null == authentication) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                adsService.findByTitleContainingIgnoreCase(searchTitle)
        );
    }

}