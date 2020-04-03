package hiberspring.service.impl;

import hiberspring.domain.dtos.ProductSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static hiberspring.common.GlobalConstants.PRODUCTS_FILE_PATH;

@Service
@Transactional

public class ProductServiceImpl implements ProductService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final ProductRepository productRepository;
    private final BranchService branchService;

    public ProductServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, ProductRepository productRepository, BranchService branchService) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.productRepository = productRepository;
        this.branchService = branchService;
    }


    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_FILE_PATH));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        ProductSeedRootDto productSeedRootDto = this.xmlParser.unmarshalFromFile(PRODUCTS_FILE_PATH, ProductSeedRootDto.class);

        productSeedRootDto.getProducts().stream().forEach(productSeedDto -> {
            if (getProductByNameAndClients(productSeedDto.getName(), productSeedDto.getClients()) != null) {
                sb.append("Already in DB").append(System.lineSeparator());
                return;
            }
            if (this.validationUtil.isValid(productSeedDto)) {
                if (branchService.getBranchByName(productSeedDto.getBranch()) != null) {
                    Product product = this.modelMapper.map(productSeedDto, Product.class);

                    Branch branch = this.branchService.getBranchByName(productSeedDto.getBranch());

                    product.setBranch(branch);

                    this.productRepository.saveAndFlush(product);
                    sb.append(String.format("Successfully imported %s.", product.getName()));
                }else {
                    sb.append("Branch doesn't exist").append(System.lineSeparator());
                }
            } else {
                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public Product getProductByNameAndClients(String name, Integer clients) {
        return this.productRepository.findFirstByNameAndClients(name, clients);
    }
}
