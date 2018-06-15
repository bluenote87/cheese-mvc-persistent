package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class ManuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "List of Menus");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Create a Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(@ModelAttribute @Valid Menu newMenu,
                             Errors errors,
                             Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId) {

        Menu aMenu = menuDao.findOne(menuId);
        model.addAttribute("title", aMenu.getName());
        model.addAttribute("menu", aMenu);
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {
        Menu aMenu = menuDao.findOne(menuId);
        model.addAttribute("title", "Add an item to your " + aMenu.getName() + " Menu");
        AddMenuItemForm aForm = new AddMenuItemForm(aMenu, cheeseDao.findAll());
        model.addAttribute("form", aForm);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String processAddItem(@ModelAttribute @Valid AddMenuItemForm theForm,
                                 @RequestParam int cheeseId,
                                 @RequestParam int menuId,
                                 Errors errors,
                                 Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add an item to your " + theForm.getMenu().getName() + " menu");
            return "menu/add-item";
        }
        Cheese newCheese = cheeseDao.findOne(cheeseId);
        Menu aMenu = menuDao.findOne(menuId);
        aMenu.addItem(newCheese);
        menuDao.save(aMenu);

        return "redirect:view/" + aMenu.getId();
    }
}
