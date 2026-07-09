package haui.fit.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluser")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_pass", nullable = false, length = 100)
    private String userPass;

    @Column(name = "user_fullname", length = 100)
    private String userFullname;

    @Column(name = "user_email", length = 100)
    private String userEmail;

    @Column(name = "user_created_date", length = 20)
    private String createdDate;

    @Column(name = "user_parent_id")
    private Integer userParentId;

    @Column(name = "user_deleted")
    private Boolean userDeleted;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(Integer userParentId) {
        this.userParentId = userParentId;
    }

    public Boolean getUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userFullname='" + userFullname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userParentId=" + userParentId +
                '}';
    }
}
