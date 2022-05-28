package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.model.Comment;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    List<Comment> comments = new ArrayList<>();
    final PurchaseHistoryService purchaseHistoryService;

    public CommentService(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
        this.comments.addAll(
                List.of(
                        new Comment("101","101","message","1"),
                        new Comment("102","101","message","1"),
                        new Comment("103","101","message","1"),
                        new Comment("104","101","message","1")
                ));
    }
    public List<Comment> getComments(){
        return comments;
    }

    public void updateComment(Comment comment, Comment newComment){
        comment.setId(newComment.getId());
        comment.setMessage(newComment.getMessage());
    }

    public boolean deleteComment(String id){

        if (isCommentByID(id)){
            Comment comment = getById(id);
            getComments().remove(comment);
            return true;
        }
        return false;
    }

    public boolean isCommentByID(String id){
        int checkForWork = -1;
        Comment comment = getById(id);
        if (comment != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? false :  true;
    }


    public boolean addComment(Comment comment){
        return comments.add(comment);
    }

    public  Comment getById(String id){
        for (Comment comment: this.comments) {
            if (comment.getId().equals(id)){
                return comment;
            }
        }
        return null;
    }

}
