package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.*;

@Service
@Transactional

public class BranchServiceImpl implements BranchService {
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final BranchRepository branchRepository;
    private final TownService townServicel;

    public BranchServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, BranchRepository branchRepository, TownService townServicel) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.branchRepository = branchRepository;
        this.townServicel = townServicel;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        BranchSeedDto[] branchSeedDtos = this.gson.fromJson(new FileReader(BRANCHES_FILE_PATH), BranchSeedDto[].class);

        Arrays.stream(branchSeedDtos).forEach(branchSeedDto -> {
            if (getBranchByName(branchSeedDto.getName()) != null) {
                sb.append("Already in DB").append(System.lineSeparator());
                return;
            }

            if (this.validationUtil.isValid(branchSeedDto)) {
                if (townServicel.getTownByName(branchSeedDto.getTown()) != null) {
                    Branch branch = this.modelMapper.map(branchSeedDto, Branch.class);

                    Town town = this.townServicel.getTownByName(branchSeedDto.getTown());

                    branch.setTown(town);

                    this.branchRepository.saveAndFlush(branch);
                    sb.append(String.format("Successfully imported %s.", branch.getName()));
                }else {
                    sb.append("Town doesn't exist").append(System.lineSeparator());
                }
            } else {
                sb.append("Error: Invalid data");
            }
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        return Files.readString(Path.of(BRANCHES_FILE_PATH));
    }

    @Override
    public Branch getBranchByName(String name) {
        return this.branchRepository.findFirstByName(name);
    }
}
