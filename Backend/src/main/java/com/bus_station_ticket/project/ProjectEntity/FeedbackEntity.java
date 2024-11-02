package com.bus_station_ticket.project.ProjectEntity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback", indexes = @Index(columnList = "feedback_id"))
public class FeedbackEntity {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "feedback_id")
       private Long feedbackId;

       @ManyToOne
       @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
       private AccountEnity accountEnitty;

       @ManyToOne
       @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable = false )
       private TicketEntity ticketEntity;

       @Column(name = "content", columnDefinition = "TEXT")
       private String content;

       @Column(name = "rating")
       private int rating;

       @Column(name = "date-comment", nullable = false, columnDefinition = "DATETIME")
       private LocalDateTime dateComment ;

       @Enumerated(EnumType.STRING)
       @Column(name = "is_delete", nullable = false, columnDefinition = "ENUM('YES','NO') DEFAULT 'NO'")
       private ChoiceEnum isDelete;



       public FeedbackEntity() {
       }

       public FeedbackEntity(AccountEnity accountEnitty, TicketEntity ticketEntity, String content, int rating,
                     LocalDateTime dateComment, ChoiceEnum isDelete) {
              this.accountEnitty = accountEnitty;
              this.ticketEntity = ticketEntity;
              this.content = content;
              this.rating = rating;
              this.dateComment = dateComment;
              this.isDelete = isDelete;
       }

       public Long getFeedbackId() {
              return feedbackId;
       }

       public void setFeedbackId(Long feedbackId) {
              this.feedbackId = feedbackId;
       }

       public AccountEnity getAccountEnitty() {
              return accountEnitty;
       }

       public void setAccountEnitty(AccountEnity accountEnitty) {
              this.accountEnitty = accountEnitty;
       }

       public TicketEntity getTicketEntity() {
              return ticketEntity;
       }

       public void setTicketEntity(TicketEntity ticketEntity) {
              this.ticketEntity = ticketEntity;
       }

       public String getContent() {
              return content;
       }

       public void setContent(String content) {
              this.content = content;
       }

       public int getRating() {
              return rating;
       }

       public void setRating(int rating) {
              this.rating = rating;
       }

       public LocalDateTime getDateComment() {
              return dateComment;
       }

       public void setDateComment(LocalDateTime dateComment) {
              this.dateComment = dateComment;
       }

       public ChoiceEnum getIsDelete() {
              return isDelete;
       }

       public void setIsDelete(ChoiceEnum isDelete) {
              this.isDelete = isDelete;
       }

       @Override
       public String toString() {
              return "FeedbackEntity [feedbackId=" + feedbackId + ", accountEnitty=" + accountEnitty + ", ticketEntity="
                            + ticketEntity + ", content=" + content + ", rating=" + rating + ", dateComment="
                            + dateComment + ", isDelete=" + isDelete + "]";
       }

}