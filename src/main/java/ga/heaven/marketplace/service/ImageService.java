package ga.heaven.marketplace.service;

import ga.heaven.marketplace.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    /**
     * Saving an image to the database
     *
     * @param imageFile multipart image file
     * @param newWidth optimized image to this value
     * @return Images image object
     */
    ImageModel upload(MultipartFile imageFile, Float newWidth) throws IOException;
    
    ImageModel save(ImageModel image);
    
    /**
     * Get image by ID
     *
     * @param id image ID
     * @return Images
     */
    ImageModel read(long id);

}
