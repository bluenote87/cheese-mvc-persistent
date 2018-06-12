package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping("")
    public String index(Model model) {
        Iterable<Category> cheeseCollection = categoryDao.findAll();
        model.addAttribute("title", "Categories of Cheese");
        model.addAttribute("categories", cheeseCollection);

        return "category/index";
    }
}
