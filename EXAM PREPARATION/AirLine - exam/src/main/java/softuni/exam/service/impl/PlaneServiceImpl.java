package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.GloblalConstants;
import softuni.exam.models.dtos.plane.PlaneSeedRootDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static softuni.exam.constants.GloblalConstants.*;

@Service
@Transactional
public class PlaneServiceImpl implements PlaneService {
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, PlaneRepository planeRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.planeRepository = planeRepository;
    }


    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Paths.get(PLANE_FILE_PATH));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        PlaneSeedRootDto planeSeedRootDto = this.xmlParser.unmarshalFromFile(PLANE_FILE_PATH, PlaneSeedRootDto.class);
        planeSeedRootDto.getPlanes().stream().forEach(planeSeedDto -> {
            if (getPlaneByRegisterNumber(planeSeedDto.getRegisterNumber())) {
                sb.append("Plane already exist").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(planeSeedDto)) {
                Plane plane = this.modelMapper.map(planeSeedDto,Plane.class);

                this.planeRepository.saveAndFlush(plane);
                sb.append(String.format("Successfully imported Plane %s",plane.getRegisterNumber()));
            } else {
                sb.append("Invalid Plane");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString().trim();
    }

    private boolean getPlaneByRegisterNumber(String registerNumber) {

        return this.planeRepository.findFirstByRegisterNumber(registerNumber) != null;
    }
}
