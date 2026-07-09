package haui.fit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluser")
public class UserObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "user_last_modified", length = 20)
    private String modifiedDate;

    @Column(name = "user_parent_id")
    private Long userParentId;

    @Column(name = "user_deleted")
    private Boolean userDeleted = false;

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

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(Long userParentId) {
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
        return "UserObject{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userFullname='" + userFullname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userParentId=" + userParentId +
                '}';
    }
}
