package alararestaurant.service.impl;

import alararestaurant.constants.GlobalConstants;
import alararestaurant.domain.dtos.ItemSeedDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.service.ItemService;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static alararestaurant.constants.GlobalConstants.*;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemServiceImpl(FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {

        return this.fileUtil.readFileContinent(ITEM_FILE_PATH);
    }

    @Override
    public String importItems(String items) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        ItemSeedDto[] itemSeedDtos = this.gson.fromJson(new FileReader(ITEM_FILE_PATH), ItemSeedDto[].class);

        Arrays.stream(itemSeedDtos).forEach(itemSeedDto -> {
            if (getItemByName(itemSeedDto.getName())) {
                sb.append("Item already exist").append(System.lineSeparator());
                return;
            }
            if (validationUtil.isValid(itemSeedDto)) {
                Item item = this.modelMapper.map(itemSeedDto, Item.class);

                if (categoryRepository.findFirstByName(itemSeedDto.getCategory()) == null) {
                    Category category = new Category();
                    category.setName(itemSeedDto.getCategory());
                    categoryRepository.saveAndFlush(category);
                }
                Category category = categoryRepository.findFirstByName(itemSeedDto.getCategory());

                item.setCategory(category);


                itemRepository.saveAndFlush(item);
                sb.append(String.format("Record %s successfully imported.", item.getName()));

            } else {
                sb.append("Invalid data");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    private boolean getItemByName(String name) {
        return this.itemRepository.findFirstByName(name) != null;
    }
}
