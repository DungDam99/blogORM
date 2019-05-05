package controller;


import model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.BlogService;

import javax.validation.Valid;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public ModelAndView showAll(Pageable pageable){
        Page<Blog> all = blogService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("blogs", all);
        return modelAndView;
    }

    @GetMapping("/create-blog")
    public String showCreateForm(Model model){
        model.addAttribute("blog", new Blog());
        return "create";
    }

    @PostMapping("/create-blog")
    public String createBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult
            , Model model){
        new Blog().validate(blog, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return "create";
        }else {
            blogService.save(blog);
            model.addAttribute("blog", blog);
            return "redirect:/";
        }
    }


    @RequestMapping(value = "/delete-blog/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Blog blog = blogService.findById(id);
        if (blog != null){
            blogService.remove(blog);
            redirectAttributes.addFlashAttribute("message", "Delete successfully!");
            return "redirect:/";
        }else {
            return "error";
        }
    }

    @GetMapping("/view-blog/{id}")
    public ModelAndView viewBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if (blog != null){
            ModelAndView modelAndView = new ModelAndView("view");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }
}
