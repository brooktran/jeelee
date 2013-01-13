/*
 *  Simple Chat room Server written by Aviad Golan...
 */

public class ChatRoomsManager {
	ChatRoom chatRooms[];	//ChatRooms array.
	int roomCnt;			//Room Counter.
	int maxInRoom;			//Maximum members in a room.
	int maxRooms;			//Maximum rooms.
	
	//Constructor:
	public ChatRoomsManager(int maxRooms, int maxInRoom) {
		this.maxInRoom = maxInRoom;
		this.maxRooms = maxRooms;
		
		//We add the default chat room know as "Lobby" as first.
		chatRooms = new ChatRoom[1];
		
		//since the first room will acquire all members, maxRooms is applied instead of a normal maxInRoom
		chatRooms[0] = new ChatRoom("Lobby", maxRooms);
		chatRooms[0].setWelcomeMessage("Welcome!");
		roomCnt = 1;
	}
	
	//Member joining Logic:
	public int memberJoin(String memberName, String roomName, String password) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index >= 0) {
			if (!chatRooms[index].isMember(memberName)) {
				if (chatRooms[index].membersCount() < maxInRoom) {
					if (chatRooms[index].isPasswordCorrect(password)) {
						chatRooms[index].addMember(memberName);
					}
					else {
						//password incorrect:
						return 3;
					}
					return 0;
				}
				else {
					//Room is full:
					return 1;
				}
			}
			else {
				//Member already in this room:
				return 2;
			}
		}
		else {
			if (addRoom(roomName)) {
				index = getRoomIndex(roomName);
				chatRooms[index].addMember(memberName);
				if (password != null) {
					chatRooms[index].setPassword(password);
				}
				return 0;
			}
			else {
				//Too many rooms...
				return 4;
			}
		}
	}
	
	//Member leaving logic:
	public void memberLeave(String memberName, String roomName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index >= 0) {
			chatRooms[index].removeMember(memberName);
			if ((chatRooms[index].membersCount() == 0) && (index > 0)) {
				removeRoom(roomName);
			}
		}
	}
	
	//Removes a room:
	public void removeRoom(String roomName) {
		int index, i;
		ChatRoom tmpCR[];
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			tmpCR = new ChatRoom[roomCnt - 1];
			for (i=0 ; i<roomCnt ; i++) {
				if (i < index) {
					tmpCR[i] = chatRooms[i];
				}
				if (i > index) {
					tmpCR[i - 1] = chatRooms[i];
				}
			}
			chatRooms = tmpCR;
			roomCnt--;
		}
	}
	
	//Add a room:
	public boolean addRoom(String roomName) {
		int i;
		ChatRoom tmpCR[];
		
		tmpCR = new ChatRoom[roomCnt + 1];
		if (roomCnt < maxRooms) {
			for (i=0 ; i<roomCnt ; i++) {
				tmpCR[i] = chatRooms[i];
			}
			tmpCR[roomCnt] = new ChatRoom(roomName, maxInRoom);
			roomCnt++;
			chatRooms = tmpCR;
			return true;
		}
		else {
			return false;
		}
	}
	
	//Get rooms list:
	public String getRoomsList() {
		int i;
		StringBuffer tmpBuff = new StringBuffer("[ Rooms List:\n");
		for (i=0 ; i<roomCnt ; i++) {
			tmpBuff.append((i + 1) + ". " + chatRooms[i].name + " (" + chatRooms[i].conCnt + ")");
			if (chatRooms[i].password != null) {
				tmpBuff.append(" -Password protected.");
			}
			tmpBuff.append("\n");
		}
		tmpBuff.append("List End ]");
		return tmpBuff.toString();
	}
	
	//Returns the index of a room:
	public int getRoomIndex(String roomName) {
		int i;
		
		for (i=0 ; i<roomCnt ; i++) {
			if (chatRooms[i].equals(roomName)) {
				//Return Index:
				return i;
			}
		}
		//Room does not exist:
		return (-1);
	}
	
	//returns room element for general use:
	public ChatRoom getChatRoom(String roomName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			return chatRooms[index];
		}
		//Room does not exist:
		return null;
	}
	
	//Check for admin rights:
	public boolean isAdmin(String memberName, String roomName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			return chatRooms[index].isAdminPermission(memberName);
		}
		
		return false;
	}
	
	//Check for normal right:
	public boolean isWritePermission(String memberName, String roomName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			return chatRooms[index].isWritePermission(memberName);
		}
		
		return false;
	}
	
	//Get room welcome message:
	public String getRoomWelcome(String roomName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			return chatRooms[index].getWelcomeMessage();
		}
		
		return " ";
	}
	
	//Member Changes NickName:
	public void changeMemberName(String roomName, String oldName, String newName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index >= 0) {
			chatRooms[index].changeMemberName(oldName, newName);
		}
	}
	
	//Set permissions for member:
	public boolean setMemberPermissions(String roomName, String memberName, int permissions) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index >= 0) {
			return chatRooms[index].setPermissions(memberName, String.valueOf(permissions));
		}
		
		return false;
	}
	
	//Set room welcome message:
	public void setRoomWelcome(String roomName, String message) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			chatRooms[index].setWelcomeMessage(message);
		}
	}
	
	//Set room Password:
	public void setRoomPassword(String roomName, String password) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index > 0) {
			chatRooms[index].setPassword(password);
		}
	}
	
	//Check if a member is in room:
	public boolean isMemberInRoom(String memberName, String roomName) {
		int index;
		
		index = getRoomIndex(roomName);
		if (index >= 0) {
			return chatRooms[index].isMember(memberName);
		}
		return false;
	}
}

/* EOF */
