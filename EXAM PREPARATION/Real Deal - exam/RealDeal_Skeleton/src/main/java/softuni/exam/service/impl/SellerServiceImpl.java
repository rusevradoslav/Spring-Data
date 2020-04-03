package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.SeedSeller.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.PictureService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.SELLER_FILE_PATH;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;


    public SellerServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, SellerRepository sellerRepository) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.sellerRepository = sellerRepository;

    }

    @Override

    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLER_FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        SellerSeedRootDto sellerSeedRootDto = this.xmlParser.unmarshalFromFile(SELLER_FILE_PATH, SellerSeedRootDto.class);

        sellerSeedRootDto.getSellers().stream().forEach(sellerSeedDto -> {
            if (getSellerByEmail(sellerSeedDto.getEmail())) {
                sb.append("Seller already in DB").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(sellerSeedDto)) {
                if (sellerSeedDto.getEmail().contains("@") && sellerSeedDto.getEmail().contains(".")) {
                    Seller seller = this.modelMapper.map(sellerSeedDto, Seller.class);

                    this.sellerRepository.saveAndFlush(seller);

                    sb.append(String.format("Successfully import seller %s - %s", seller.getLastName(), seller.getEmail()));
                }else {
                    sb.append("Invalid seller");
                }
            } else {
                sb.append("Invalid seller");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public Seller getSellerById(long id) {
        return this.sellerRepository.findFirstById(id);
    }

    private boolean getSellerByEmail(String email) {
        return this.sellerRepository.findFirstByEmail(email) != null;
    }


}
