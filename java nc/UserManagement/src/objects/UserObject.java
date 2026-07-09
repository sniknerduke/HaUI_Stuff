package objects;

public class UserObject {
    private int user_id; 
    private String user_name; 
    private String user_pass; 
    private String user_fullname; 
    private String user_email; 
    private String user_created_date; 
    private int user_parent_id;
    private boolean user_deleted; 

    // Getter và Setter 
    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public String getUser_name() { return user_name; }
    public void setUser_name(String user_name) { this.user_name = user_name; }
    public String getUser_pass() { return user_pass; }
    public void setUser_pass(String user_pass) { this.user_pass = user_pass; }
    public String getUser_fullname() { return user_fullname; }
    public void setUser_fullname(String user_fullname) { this.user_fullname = user_fullname; }
    public String getUser_email() { return user_email; }
    public void setUser_email(String user_email) { this.user_email = user_email; }
    public String getUser_created_date() { return user_created_date; }
    public void setUser_created_date(String user_created_date) { this.user_created_date = user_created_date; }
    public int getUser_parent_id() { return user_parent_id; }
    public void setUser_parent_id(int user_parent_id) { this.user_parent_id = user_parent_id; }
    public boolean isUser_deleted() { return user_deleted; }
    public void setUser_deleted(boolean user_deleted) { this.user_deleted = user_deleted; }

    @Override
    public String toString() {
        return "UserObject{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_fullname='" + user_fullname + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_parent_id=" + user_parent_id +
                ", user_created_date='" + user_created_date + '\'' +
                '}';
    }
}
