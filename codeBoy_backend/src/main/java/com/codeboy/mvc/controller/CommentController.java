package com.codeboy.mvc.controller;

import java.util.List;

import com.codeboy.mvc.model.requestDto.CommentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeboy.mvc.model.dto.Comment;
import com.codeboy.mvc.model.service.CommentService;

@RestController
@RequestMapping("/api/user-problem-sets")
public class CommentController {
	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	@GetMapping("{userProblemSetId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsById(@PathVariable("userProblemSetId") long userProblemSetId){
		List<Comment> comments = commentService.getAllCommentsById(userProblemSetId);
		if(!comments.isEmpty()) {
			//성공적으로 댓글들을 조회한 경우 
			return new ResponseEntity<List<Comment>>(comments, HttpStatusCode.valueOf(200));
		}
		//댓글 조회에 실패한 경우 
		return new ResponseEntity<List<Comment>>(HttpStatusCode.valueOf(404));
		
	}
	
	@PostMapping("{userProblemSetId}/comments")
	public ResponseEntity<String> addComment(@PathVariable("userProblemSetId") long userProblemSetId, @RequestBody Comment comment){
		int result = commentService.addComment(userProblemSetId, comment);
		if(result == 1) {
			//sql의 반환값이 1개면 댓글 추가에 성공한 경우 
			return new ResponseEntity<String>("댓글 추가 성공", HttpStatusCode.valueOf(200));
		}
		//서버 오류로 댓글 추가에 실패한 경우 
		return new ResponseEntity<String>("댓글 추가 실패", HttpStatusCode.valueOf(500));
		
	}
	
    //리소스의 일부(content)만 수정하므로 패치매핑
	@PatchMapping("{userProblemSetId}/comments")
	public ResponseEntity<String> updateComment(@PathVariable("commentId") long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest){
        Comment comment = new Comment();
        //Dto를 통해서 받음
        comment.setContent(commentUpdateRequest.getContent());
        int result = commentService.updateComment(commentId, comment);
		if(result == 1) {
			//sql의 반환값이 1개면 댓글 업데이트에 성공
			return new ResponseEntity<String>("댓글 수정 성공", HttpStatusCode.valueOf(200));
		}
		//댓글 아이디가 존재하지 않을 경우, 업데이트 실패 
		return new ResponseEntity<String>("존재 하지 않는 댓글입니다.", HttpStatusCode.valueOf(404));
		
	}
	
	@DeleteMapping("{userProblemSetId}/comments")
	public ResponseEntity<String> deleteComment(@PathVariable("commentId") long commentId){
		int result = commentService.deleteComment(commentId);
		if(result == 1) {
			//sql의 반환값이 1개면 댓글 삭제에 성공
			return new ResponseEntity<String>("댓글 삭제 성공", HttpStatusCode.valueOf(200));
		}
		//댓글 아이디가 존재하지 않을 경우, 삭제에 실패 
		return new ResponseEntity<String>("존재 하지 않는 댓글입니다.", HttpStatusCode.valueOf(404));
		
	}
	
}
