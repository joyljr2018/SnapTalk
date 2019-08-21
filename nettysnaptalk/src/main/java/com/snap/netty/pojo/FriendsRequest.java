package com.snap.netty.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "friends_request")
public class FriendsRequest {
    @Id
    private String id;

    @Column(name = "request_user_id")
    private String requestUserId;

    @Column(name = "accept_user_id")
    private Integer acceptUserId;

    @Column(name = "requst_date_time")
    private Date requstDateTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return request_user_id
     */
    public String getRequestUserId() {
        return requestUserId;
    }

    /**
     * @param requestUserId
     */
    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    /**
     * @return accept_user_id
     */
    public Integer getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * @param acceptUserId
     */
    public void setAcceptUserId(Integer acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * @return requst_date_time
     */
    public Date getRequstDateTime() {
        return requstDateTime;
    }

    /**
     * @param requstDateTime
     */
    public void setRequstDateTime(Date requstDateTime) {
        this.requstDateTime = requstDateTime;
    }
}