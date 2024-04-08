package entities;

import entities.User;

import java.util.Date;

public class Invoice {
    private Integer id;
    private User user;
    private Date startTime;
    private Date endTime;
    private Double amount;

    public Invoice(User user, Date startTime) {
        this.user = user;
        this.startTime = startTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
