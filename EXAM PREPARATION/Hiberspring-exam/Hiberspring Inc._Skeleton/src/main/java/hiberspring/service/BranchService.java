package hiberspring.service;


import hiberspring.domain.entities.Branch;

import java.io.FileNotFoundException;
import java.io.IOException;

//TODO
public interface BranchService {

    Boolean branchesAreImported();

    String readBranchesJsonFile() throws IOException;

    String importBranches(String branchesFileContent) throws IOException;

    Branch getBranchByName(String name);
}
