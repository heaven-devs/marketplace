package ga.heaven.marketplace.service.impl;

import ga.heaven.marketplace.model.ImageModel;
import ga.heaven.marketplace.repository.ImageRepository;
import ga.heaven.marketplace.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//@Transactional
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    
    private final ImageRepository imageRepository;
    
    @Override
    public ImageModel upload(MultipartFile imageFile, Float newWidth) throws IOException {
        BufferedImage originalBufferedImage = ImageIO.read(imageFile.getInputStream());
        //int height = originalBufferedImage.getHeight() / (originalBufferedImage.getWidth() / OPTIMIZED_IMAGE_WIDTH);
        double trans = 1.0 / (originalBufferedImage.getWidth() / newWidth);
        AffineTransform tr = new AffineTransform();
        tr.scale(trans, trans);
        AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
        double w = originalBufferedImage.getWidth() * trans;
        double h = originalBufferedImage.getHeight() * trans;
        BufferedImage optimizedBufferedImage = new BufferedImage((int) w, (int) h, originalBufferedImage.getType());
        op.filter(originalBufferedImage, optimizedBufferedImage);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Tika tika = new Tika();
        String detectedType = tika.detect(imageFile.getInputStream());
        String fileExtension = MimeTypeUtils.parseMimeType(detectedType).getSubtype();
        ImageIO.write(optimizedBufferedImage,fileExtension , byteArrayOutputStream);
        byteArrayOutputStream.flush();
        ImageModel imageModel = new ImageModel();
        imageModel.setImage(byteArrayOutputStream.toByteArray());
        //imageModel.setSize((long) byteArrayOutputStream.size());
        byteArrayOutputStream.close();
        imageModel.setMediaType(detectedType);
        return imageModel;
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