package com.infoshare.webapp.service;

import com.infoshare.webapp.model.Category;

import java.util.Arrays;
import java.util.List;

public class CategoryService {
    private static List<Category> categoryList = Arrays.asList(Category.values());

    public static List<Category> getAllCategories() {
        return categoryList;
    }

}
