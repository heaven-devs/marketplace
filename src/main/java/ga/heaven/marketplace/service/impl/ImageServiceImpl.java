package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.repository.ImageRepository;
import ga.heaven.marketplace.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ImageModel upload(MultipartFile imageFile) throws IOException {
        ImageModel image = new ImageModel();
        image.setImage(imageFile.getBytes());
        image.setSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setImage(imageFile.getBytes());
        return image;
    }
    
    @Override
    public ImageModel save(ImageModel image) {
        return imageRepository.saveAndFlush(image);
    }

    @Transactional(readOnly = true)
    @Override
    public ImageModel read(long id) {
        return imageRepository.findById(id)
                .orElse(null);
    }
}