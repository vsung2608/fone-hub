package com.example.fone_hub.controller.user;


import com.example.fone_hub.entity.Comment;
import com.example.fone_hub.service.CommentService;
import com.example.fone_hub.utils.GetUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private GetUserAuthentication getUserAuthentication;

    @PostMapping("/user/comment/{productId}")
    public String comment(@ModelAttribute("newComment") Comment newComment,
                          @PathVariable Long productId){

        newComment.setUser(getUserAuthentication.getUser());
        commentService.AddComment(newComment, productId);
        
        return "redirect:/home/product/{productId}";
    }
}
