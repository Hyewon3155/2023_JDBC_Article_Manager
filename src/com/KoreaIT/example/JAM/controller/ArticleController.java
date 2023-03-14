package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleController{
	private Connection conn;
	private Scanner sc;
	
	public ArticleController(Connection conn, Scanner sc) {
		this.conn = conn;
	}
	public void showList() {
		System.out.println("== 게시물 목록 ==");

		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");
		
		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
		
		for(Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		
		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return; 
		}

		System.out.println("번호	|	제목");

		for (Article article : articles) {
			System.out.printf("%d	|	%s\n", article.id, article.title);
		}
	}
	public void showWrite() {
		System.out.println("== 게시물 작성 ==");

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		int id = DBUtil.insert(conn, sql);
		
		System.out.printf("%d번 글이 생성되었습니다\n", id);
		
	}
	public void showModify(String cmd) {
		SecSql sql = new SecSql();
        int id = Integer.parseInt(cmd.split(" ")[2]);
        
        sql.append("SELECT COUNT(*)");
        sql.append("FROM article");
        sql.append("WHERE id = ?",id);
        
        int articlesCount = DBUtil.selectRowIntValue(conn, sql);
        if(articlesCount == 0) {
        	System.out.printf("%d번 글은 존재하지 않습니다.\n", id);
        	return;
        }
        
        System.out.println("== 게시물 수정 ==");
        
       

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();
        
 
        sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(" WHERE = id  ?", id);
		
		DBUtil.update(conn, sql);
		
		System.out.printf("%d번 글이 수정되었습니다\n", id);
		
	}
	public void showDelete(String cmd) {
		SecSql sql = new SecSql();
		int id = Integer.parseInt(cmd.split(" ")[2]);
		 sql.append("SELECT COUNT(*)");
         sql.append("FROM article");
         sql.append("WHERE id = ?",id);
            
         int articlesCount = DBUtil.selectRowIntValue(conn, sql);
         if(articlesCount == 0) {
            	System.out.printf("%d번 글은 존재하지 않습니다.\n", id);
            	return;
         }
         System.out.println("== 게시물 삭제 ==");
         sql.append("DELTE FROM article");
         sql.append("WHERE id = ?", id);     
         
         DBUtil.delete(conn, sql);
         System.out.printf("%d번 글이 삭제되었습니다\n", id);
		
	}
	public void showDetail(String cmd) {
		SecSql sql = new SecSql();
		int id = Integer.parseInt(cmd.split(" ")[2]);

        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("WHERE id = ?", id);
        
        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
        
        if(articleMap.isEmpty()) {
        	System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        	return;
        }
       
        System.out.printf("== %d번 게시물 상세보기 ==", id);
        
        Article article = new Article(articleMap);
        
        System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성 날짜 : %s\n", article.regDate);
		System.out.printf("수정 날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		
	
		
	}
}


