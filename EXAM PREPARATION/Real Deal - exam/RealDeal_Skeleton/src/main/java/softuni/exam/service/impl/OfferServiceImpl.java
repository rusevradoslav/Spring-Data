package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.SeedOffer.OfferSeedRootDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Picture;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.PictureService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static softuni.exam.constants.GlobalConstants.OFFER_FILE_PATH;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;
    private final CarService carService;
    private final SellerService sellerService;
    private final PictureService pictureService;

    public OfferServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, OfferRepository offerRepository, CarService carService, SellerService sellerService, PictureRepository pictureRepository, PictureService pictureService) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.sellerService = sellerService;
        this.pictureService = pictureService;

    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        OfferSeedRootDto offerSeedRootDto = this.xmlParser.unmarshalFromFile(OFFER_FILE_PATH, OfferSeedRootDto.class);

        offerSeedRootDto.getOffers().stream().forEach(offerSeedDto -> {
            if (getOfferByDescriptionAndAddedOn(offerSeedDto.getDescription(), offerSeedDto.getAddedOn())) {
                sb.append("Offer is already in DB").append(System.lineSeparator());
                return;
            }

            if (validationUtil.isValid(offerSeedDto)) {
                if (carService.findCarById(offerSeedDto.getCar().getId()) != null) {
                    if (sellerService.getSellerById(offerSeedDto.getSeller().getId()) != null) {
                        Offer offer = this.modelMapper.map(offerSeedDto, Offer.class);

                        Car car = carService.findCarById(offerSeedDto.getCar().getId());
                        offer.setCar(car);
                        Seller seller = sellerService.getSellerById(offerSeedDto.getSeller().getId());
                        offer.setSeller(seller);


                        List<Picture> pictures = new ArrayList<>(car.getPictures());

                        offer.setPictures(pictures);

                        this.offerRepository.saveAndFlush(offer);

                        sb.append(String.format("Successfully import offer %s - %s", offer.getAddedOn(), offer.isHasGoldStatus()));

                    } else {
                        sb.append("Invalid offer").append(System.lineSeparator());
                        return;
                    }
                } else {
                    sb.append("Invalid offer").append(System.lineSeparator());
                    return;
                }
            } else {
                sb.append("Invalid offer");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    private boolean getOfferByDescriptionAndAddedOn(String description, LocalDateTime addedOn) {
        return this.offerRepository.findFirstByDescriptionAndAddedOn(description, addedOn) != null;
    }
}
