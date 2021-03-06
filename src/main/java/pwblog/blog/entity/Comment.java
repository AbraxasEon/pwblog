/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwblog.blog.entity;

import pwblog.blog.boundary.dto.CommentCreate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author indra
 */
@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "comment_sequence")
    protected Long id;

    @Column
    private String text;

    @Column
    private Long articleId;

    @Column
    private Long userId;

    @Column
    private int rating;

    @Column
    private Long answersTo;

    @Column
    private boolean deleted = false;

    public Comment() {
    }

    public Comment(CommentCreate c) {
        this.text = c.text;
        this.rating = c.rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getAnswersTo() {
        return answersTo;
    }

    public void setAnswersTo(Long answersTo) {
        this.answersTo = answersTo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
