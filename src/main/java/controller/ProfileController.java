package controller;

public class ProfileController
{
    private static ProfileController instance = null;
    
    public static ProfileController getInstance()
    {
        if (instance == null){
            instance = new ProfileController();
        }
        return instance;
    }		

    public void changeNickname(String nickname) 		
    {
        
    }		
    
    public void changePassword(String currentPassword, String newPassword) 		
    {
        
    }		
}
