package com.techelevator.controller;

import com.techelevator.dao.JdbcPostDao;
import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Post;
import com.techelevator.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PostController {

    private PostDao postDao;
    private PostService postService;

    PostController(PostDao postDao, PostService postService){
        this.postDao = postDao;
        this.postService =  postService;
    }

    @RequestMapping (path = "/posts", method = RequestMethod.GET)
    public List<Post> getPosts (){
        return postDao.getPosts();
    }

    @RequestMapping (path = "/posts/{id}", method = RequestMethod.GET)
    public Post getPostBYId(@PathVariable int id){
        return postDao.getPostById(id);
    }

    @RequestMapping (path = "/forums/{id}/posts", method = RequestMethod.GET)
    public List<Post> getPostsBYForumId(@PathVariable int id){
        return postDao.getPostsByForumId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping (path = "/posts", method = RequestMethod.POST)
    public void createPost(@RequestBody Post post){
        postDao.createPost(post);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/posts", method = RequestMethod.DELETE)
    public void deletePost(@RequestBody Post post, Principal principal) {
        postService.deletePostService(post, principal.getName());
    }
}
