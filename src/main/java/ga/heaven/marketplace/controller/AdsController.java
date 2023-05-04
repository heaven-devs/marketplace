package ga.heaven.marketplace.controller;

import ga.heaven.marketplace.dto.Ads;
import ga.heaven.marketplace.dto.CreateAds;
import ga.heaven.marketplace.dto.FullAdds;
import ga.heaven.marketplace.dto.ResponseWrapperAds;
import ga.heaven.marketplace.service.AdsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin(value = "http://marketplace.heaven.ga")
//@CrossOrigin(value = "http://localhost:3000")
@CrossOrigin(origins={"http://marketplace.heaven.ga", "http://localhost:3000"})
@RequestMapping("ads")
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
    // РЕАЛИЗОВАНО
    @GetMapping
    public ResponseEntity<?> getAds() {
        List<Ads> ads = adsService.getAds();
        return ResponseEntity.ok(ads);
    }

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

    // РЕАЛИЗОВАНО ЧАСТИЧНО
    @PostMapping(name="/newAd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addAds(@RequestPart CreateAds properties, @RequestPart MultipartFile image) {
        adsService.addAds(properties, image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

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
    // РЕАЛИЗОВАНО
    @GetMapping("/{id}")
    public ResponseEntity<FullAdds> getFullAd(@PathVariable long id) {
        FullAdds fullAdds = adsService.getFullAd(id);
        if (fullAdds == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(fullAdds);
        }
    }

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
    @DeleteMapping("{id}")
    public ResponseEntity<?> removeAds(@PathVariable long id) {
        adsService.removeAds(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

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
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAds(@PathVariable int id, @RequestBody CreateAds createAds) {
        adsService.updateAds(id, createAds);
        return ResponseEntity.ok(null);
    }

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
    public ResponseEntity<?> getAdsMe() {
        List<Ads> ads = adsService.getAdsMe();
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
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable int id, @RequestPart MultipartFile image) {
        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok(null);
    }

}