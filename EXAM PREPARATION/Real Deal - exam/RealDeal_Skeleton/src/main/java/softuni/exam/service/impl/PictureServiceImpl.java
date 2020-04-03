package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static softuni.exam.constants.GlobalConstants.PICTURE_FILE_PATH;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;
    private final CarService carService;

    public PictureServiceImpl(Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, PictureRepository pictureRepository, CarService carService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        PictureSeedDto[] pictureSeedDtos = this.gson.fromJson(new FileReader(PICTURE_FILE_PATH), PictureSeedDto[].class);

        Arrays.stream(pictureSeedDtos).forEach(pictureSeedDto -> {
            if (getPictureByName(pictureSeedDto.getName())) {
                sb.append("Picture already in DB").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(pictureSeedDto)) {

                Picture picture = this.modelMapper.map(pictureSeedDto, Picture.class);

                picture.setCar(carService.findCarById(pictureSeedDto.getCar()));

                Car car = carService.findCarById(pictureSeedDto.getCar());
                // tuk vzimam kolata koqto e s id ot xml file - a
                car.getPictures().add(picture);
                // tuk dobavqm kolata kum kolekciqa sus snimki na kolata syotwetnata snimka dobavq adekwatno zashtot pri debug prosledih kato wzema id na kola
                //poveche ot vednuj size se uvelichava no kogato se opitam da vzema kolata v OfferServiceImp mi dawa size = 0


                pictureRepository.saveAndFlush(picture);

                sb.append(String.format("Successfully import picture - %s", picture.getName()));
            } else {
                sb.append("Invalid picture");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }





    private boolean getPictureByName(String name) {
        return this.pictureRepository.findFirstByName(name) != null;
    }

}
