package lk.luminex.asset.item.brand.controller;



import lk.luminex.asset.item.brand.entity.Brand;
import lk.luminex.asset.item.brand.service.BrandService;
import lk.luminex.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/brand")
public class BrandController implements AbstractController<Brand, Integer> {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    private String commonThings(Model model, Brand brand, Boolean addState) {
        /*model.addAttribute("mainCategories", MainCategory.values());*/
        model.addAttribute("brand", brand);
        model.addAttribute("addStatus", addState);
        return "brand/addBrand";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brand/brand";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        return commonThings(model, new Brand(), true);
    }

    @PostMapping( value = {"/add", "/update"} )
    public String persist(Brand brand, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if ( bindingResult.hasErrors() ) {
            return commonThings(model, brand, true);
        }

        brandService.persist(brand);
        return "redirect:/brand";
    }

    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable Integer id, Model model) {
        return commonThings(model, brandService.findById(id), false);
    }

    @GetMapping( "/delete/{id}" )
    public String delete(@PathVariable Integer id, Model model) {
        brandService.delete(id);
        return "redirect:/brand";
    }

    @GetMapping( "/{id}" )
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("brandDetail", brandService.findById(id));
        return "brand/brand-detail";
    }
}