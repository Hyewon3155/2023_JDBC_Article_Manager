package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.example.JAM.dto.Article;
import com.KoreaIT.example.JAM.service.ArticleService;
import com.KoreaIT.example.JAM.session.Session;

public class ArticleController {
	
	private ArticleService articleService;
	private Scanner sc;
	
	public ArticleController(Connection conn, Scanner sc) {
		this.articleService = new ArticleService(conn);
		this.sc = sc;
	}

	public void doWrite() {
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
		    return;
		}
		System.out.println("== 게시물 작성 ==");

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int id = articleService.doWrite(title, body, Session.loginedMember.id);

		System.out.printf("%d번 글이 생성되었습니다\n", id);
	}
	
	public void showList(String cmd) {
		System.out.println("== 게시물 목록 ==");

		String searchKeyword = cmd.substring("article list".length()).trim();
		
		List<Article> articles = articleService.getArticles(searchKeyword);
	
		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}
		
	    if(searchKeyword.length() > 0) {
	    	System.out.printf("검색어 : " + searchKeyword);
	    }

	System.out.println("번호	|	제목	|	작성날짜	|	작성자	|	조회수	");

		for (Article article : articles) {
			System.out.printf("%d	|	%s 	|	%s	|	%s	|	%s	\n", article.id, article.title, article.regDate, article.writerName, article.vCnt);
		}
	}

	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		int affectRows = articleService.increaseVCnt(id);
		
		if (affectRows == 0) {
			System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
			return;
		}
		
		Article article = articleService.getArticle(id);

		System.out.printf("== %d번 게시물 상세보기 ==\n", id);
		
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		System.out.printf("작성자 : %d\n", article.writerName);
		System.out.printf("조회수 : %d\n", article.vCnt);
	}

	public void doModify(String cmd) {
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
		    return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticle(id);
		
		if (article == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
			return;
		}
		
		// if(article.writerName == Session.loginedMember.name){
		//      이것도 가능하지만, 이름을 이용해 비교를 하면,
		//      다른 회원이 글을 수정할 수 있게 됨 PK가 아니라서
		//}
		if(article.memberId != Session.loginedMemberId) {
			System.out.println("해당 게시글에 대한 권한이 없습니다");
			return;
		}

		System.out.println("== 게시물 수정 ==");

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		articleService.doModify(title, body, id);

		System.out.printf("%d번 글이 수정되었습니다\n", id);
	}

	public void doDelete(String cmd) {
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
		    return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);

Article article = articleService.getArticle(id);
		
		if (article == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
			return;
		}

		
		if(article.memberId != Session.loginedMemberId) {
			System.out.println("해당 게시글에 대한 권한이 없습니다");
			return;
		}
		System.out.println("== 게시물 삭제 ==");

		articleService.doDelete(id);

		System.out.printf("%d번 글이 삭제되었습니다\n", id);
	}

}