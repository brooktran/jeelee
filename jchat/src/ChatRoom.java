/*
 *  Simple Chat room Server written by Aviad Golan...
 */

import java.util.Vector;

public class ChatRoom {
	String name;				//Room Name.
	String password;			//Room password(optional).
	String welcomeMessage;		//Room Welcome by admins.
	int conCnt;					//Number of connections.
	int maxCon;					//Maximum members.
    private Vector members;		//Administrators.
    private Vector permissions;	//Permissions of chat room. (0- admin, 1- normal 2- readOnly).
 
    //Constructor
	public ChatRoom(String name, int maxCon) {
		this.name = name;
		this.maxCon = maxCon;
		password = null;
		welcomeMessage = new String("Welcome!");
		conCnt = 0;
		members = new Vector(maxCon);
		permissions = new Vector(maxCon);
	}
	
	//Adds a member to room:
	public void addMember(String name) {
		//Check if first member, if so he is the admin(Same as IRC protocol)...
		if (conCnt == 0) {
			members.add(name);
			permissions.add("0");	
		}
		else {
			members.add(name);
			permissions.add("1");
		}
		conCnt++;
	}
	
	//Removes a member from room:
	public void removeMember(String name) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(name)) {
				permissions.removeElementAt(i);
				members.removeElementAt(i);
				conCnt--;
				return;
			}
		}
	}
	
	//Member changes his nickname:
	public void changeMemberName(String oldName, String newName) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(oldName)) {
				members.set(i, newName);
				return;
			}
		}
	}
	
	//Get member permissions:
	public int getPermissions(String name) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(name)) {
				return Integer.parseInt((String)permissions.elementAt(i));
			}
		}
		
		return -1;
	}
	
	//Set member permissions:
	public boolean setPermissions(String memberName, String permissions) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(memberName)) {
				this.permissions.set(i, permissions);
				return true;
			}
		}
		
		return false;
	}
	
	//Set room welcome message:
	public void setWelcomeMessage(String message) {
		if (message != null) {
			welcomeMessage = new String(message);
		}
	}
	
	//Clear welcome message:
	public void clearWelcomeMessage() {
		welcomeMessage = new String("Welcome!");
	}
	
	//Get welcome message:
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	//Set password:
	public void setPassword(String password) {
		this.password = password;
	}
	
	//Clear password:
	public void clearPassword() {
		password = null;
	}
	
	//Check password:
	public boolean isPasswordCorrect(String password) {
		if (this.password == null) {
			return true;
		}
		if (this.password.equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Check if member is in room:
	public boolean isMember(String memberName) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(memberName)) {
				return true;
			}
		}
		
		return false;
	}
	
	//Check if admin:
	public boolean isAdminPermission(String memberName) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(memberName)) {
				if (permissions.elementAt(i).equals("0")) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		return false;
	}
	
	//Check if can send messages:
	public boolean isWritePermission(String memberName) {
		int i;
		
		for (i=0 ; i<members.size() ; i++) {
			if (members.elementAt(i).equals(memberName)) {
				if ((permissions.elementAt(i).equals("0")) || (permissions.elementAt(i).equals("1"))) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	//Comparer this.room1.equals(room2):
	public boolean equals(String roomName) {
		return this.name.equals(roomName);
	}
	
	//Count members in room;
	public int membersCount() {
		return conCnt;
	}
}

/* EOF */
