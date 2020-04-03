package alararestaurant.service.impl;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();
        List<Category> categories = this.categoryRepository.findAll();
        categories.stream().sorted((a, b) -> {
            double sort = b.getItems().size() - a.getItems().size();
            if (sort == 0) {
                sort = this.totalSumOfPriceItems(b.getItems()) -
                        this.totalSumOfPriceItems(a.getItems());
            }
            return (int) (sort * 100);

        }).forEach(category -> {
            sb.append(String.format("Category: %s%n", category.getName()));
            for (Item item : category.getItems()) {
                sb.append(String.format("--- Item Name: %s%n" +
                                "--- Item Price: %.2f%n",
                        item.getName(),
                        item.getPrice())).append(System.lineSeparator());
            }
        });
        return sb.toString().trim();

    }


    private double totalSumOfPriceItems(List<Item> items) {
        return items.stream().map(Item::getPrice).mapToDouble(BigDecimal::doubleValue).sum();
    }

    @Override
    public Category getCategoryByName(String name) {
        return this.categoryRepository.findFirstByName(name);
    }

}
