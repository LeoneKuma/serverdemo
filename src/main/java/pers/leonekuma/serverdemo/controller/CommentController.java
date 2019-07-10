package pers.leonekuma.serverdemo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.leonekuma.serverdemo.entity.Comment;
import pers.leonekuma.serverdemo.repository.CommentRepository;
import pers.leonekuma.serverdemo.repository.DynamicRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PictureController pictureController;
    @Autowired
    private DynamicRepository dynamicRepository;

    @PostMapping(value = "/send_comment")
    public Map sendComment(
            @RequestParam("comment")String commentStr
    ){
        Map resultMap=new HashMap();
        Comment comment;
        comment=JSON.parseObject(commentStr,Comment.class);
        commentRepository.save(comment);

        resultMap.put("isSendCommentSuccessful",true);
        return resultMap;

    }
    @PostMapping(value = "/get_comments")
    public Map getComments(
            @RequestParam("dynamicId")Integer dynamicId
    )throws Exception {
        Map resultMap = new HashMap();
        Sort s = new Sort(Sort.Direction.DESC, "dynamicId");
        Pageable p = PageRequest.of(0, 100, s);
        List<Comment> commentList = commentRepository.findByDynamicId(dynamicId, p);

        String commentListStr = JSON.toJSONString(commentList);
        String portraitsStr=pictureController.get_comments_portraits(commentList);
        resultMap.put("isGetCommentsSuccessful", true);
        resultMap.put("main_comment_list", commentListStr);
        resultMap.put("portraitsList",portraitsStr);
        return resultMap;
    }

}
