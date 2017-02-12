package io.github.adraw.framework.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "li_member_stat")
public class MemberStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    /**
     * 评论数
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 私信数量
     */
    @Column(name = "direct_num")
    private Integer directNum;

    /**
     * 关注数量
     */
    @Column(name = "attention_num")
    private Integer attentionNum;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return member_id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取评论数
     *
     * @return comment_num - 评论数
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 设置评论数
     *
     * @param commentNum 评论数
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 获取私信数量
     *
     * @return direct_num - 私信数量
     */
    public Integer getDirectNum() {
        return directNum;
    }

    /**
     * 设置私信数量
     *
     * @param directNum 私信数量
     */
    public void setDirectNum(Integer directNum) {
        this.directNum = directNum;
    }

    /**
     * 获取关注数量
     *
     * @return attention_num - 关注数量
     */
    public Integer getAttentionNum() {
        return attentionNum;
    }

    /**
     * 设置关注数量
     *
     * @param attentionNum 关注数量
     */
    public void setAttentionNum(Integer attentionNum) {
        this.attentionNum = attentionNum;
    }
}