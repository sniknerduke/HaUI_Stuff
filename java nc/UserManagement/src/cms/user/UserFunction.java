package cms.user;

import objects.UserObject;
import java.util.ArrayList;

public interface UserFunction {
    public boolean addUser(UserObject item); 
    public ArrayList<UserObject> getUsers(UserObject similar, int at, byte total); 
}
