package com.infoshare.webapp.service;

import com.infoshare.webapp.model.Category;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.Arrays;
import java.util.List;

public class CategoryService {
    private static List<Category> categoryList = Arrays.asList(Category.values());
    public static List<Category> getAll(){return categoryList;}

}
