package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.PICTURE_FILE_PATH;


@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, PictureRepository pictureRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.pictureRepository = pictureRepository;
    }


    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        PictureSeedRootDto pictureSeedRootDto = this.xmlParser.unmarshalFromFile(PICTURE_FILE_PATH, PictureSeedRootDto.class);
        System.out.println();
        pictureSeedRootDto.getPictures().stream().forEach(pictureSeedDto -> {

            if (getPictureByUrl(pictureSeedDto.getUrl()) != null){
                sb.append("Already in DB");
                return;
            }
            if (validatorUtil.isValid(pictureSeedDto)){
                Picture picture = this.modelMapper.map(pictureSeedDto,Picture.class);
                this.pictureRepository.saveAndFlush(picture);
                sb.append(String.format("Successfully imported picture - %s",picture.getUrl()));

            }else {
                sb.append("Invalid picture");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findFirstByUrl(url);
    }


}
