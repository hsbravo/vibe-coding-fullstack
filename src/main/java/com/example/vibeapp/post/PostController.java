package com.example.vibeapp.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        model.addAttribute("page", postService.findAllPaged(page, 5));
        return "post/posts";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createPostForm() {
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String createPost(@RequestParam("title") String title, @RequestParam("content") String content) {
        postService.create(title, content);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String updatePostForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "post/post_edit_form";
    }

    @PostMapping("/posts/{id}/save")
    public String updatePost(@PathVariable("id") Long id, @RequestParam("title") String title,
            @RequestParam("content") String content) {
        postService.update(id, title, content);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}
