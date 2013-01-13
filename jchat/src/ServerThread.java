/*
 *  Simple Chat room Server written by Aviad Golan...
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


public class ServerThread extends Thread {
	//Data members:
	public Socket socket;			//Client socket
	public String input;			//Input string
	public JTalkServer server;		//Main server class
	private PrintWriter outBuff;	//Buffer (write)
	private BufferedReader inBuff; 	//Buffer (read)
	private String name;			//User Name
	
	//Constructor
	public ServerThread(JTalkServer server, Socket socket) {
		this.socket = socket;
		this.server = server;
	}
	
	//Here every thread is assigned to a user, created in JTalkServer.
	public void run() {
		Random r = new Random();
		int t;
		boolean cmdFlag;
		
		//Set first all buffers (in and out) of a socket:
		try {
			outBuff = new PrintWriter(socket.getOutputStream(),true);
			inBuff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Check if server is full(we leave 1 inbound entry open for doing that):
			if (server.isServerFull()) {
				outBuff.println("\nServer is full at the moment please try again later...");
				outBuff.flush();
				outBuff.println("//diss");
				outBuff.flush();
				return;
			}
			
			//Assign general name for newcomer
			name = "guest" + r.nextInt(9999);
			
			//Greet user:
			outBuff.println("\nWelcome " + name + "\nTo change your name type '/nick' YourName.\nTo join a room type '/join' RoomName or '/j' RoomName.\nTo see all available command type '/cmd'...\n[ you are now connected to 'Lobby' ]");
			outBuff.flush();
			
			//Tell everybody a new friend has come to block:
			server.msgBrodcastRoom("Lobby", "[ User " + name + " Has Joined the chat ]");
			
			//add user to list
			server.addClient(socket, name);
			
			//update friends list of all members
			server.friendsRoomBrodcast("Lobby");

			//main loop to deal with client (send, get, receive, send, get, etc...)
			while (socket.isConnected()) {
				cmdFlag = false;
				
				//Read from buffer (get)
				String line = inBuff.readLine();
				
				//Split line and check for commands(user switches):
				if ((line != null) && (line.charAt(0) != '\0') && (line.charAt(0) != '\n')) {
					String tmpStr[] = line.split(" ");
					
					//NickName switch (user wants to change name)
					if (tmpStr[0].equals("/nick")) {
						if (tmpStr.length > 1) {
							if (!server.isNameTaken(tmpStr[1])) {
								//Set new name and update friends list.
								server.setClientName(socket, name, tmpStr[1]);
								server.roomMgr.changeMemberName(server.getRoomName(socket), name, tmpStr[1]);
								server.friendsRoomBrodcast(server.getRoomName(socket));
								server.msgBrodcastRoom(server.getRoomName(socket), "[ User " + name + "is now known as " + tmpStr[1] + " ]");
								name = tmpStr[1];
							}
							else {
								outBuff.println("[ Name is already taken ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ You didn't specify a new nickname ]");
							outBuff.flush();
						}
						
						cmdFlag = true;
					}
					
					//disconnect switch:
					if (tmpStr[0].equals("//diss")) {
						//close socket, update friends, end...
						outBuff.println("//diss");
						outBuff.flush();
						String tmpRoom = server.getRoomName(socket);
						server.removeClient(socket, name);
						server.msgBrodcastRoom(tmpRoom, "[ User " + name + " Disconnected ]");
						server.friendsRoomBrodcast(tmpRoom);
						socket.close();
						this.stop();
						cmdFlag = true;
						return;
					}
					
					//Join switch:
					if (tmpStr[0].equals("/join") || tmpStr[0].equals("/j")) {
						if (tmpStr[1] != null) {
							String oldRoom = server.getRoomName(socket);
							
							//check for password
							if (tmpStr.length >= 3) {
								//set room with password
								t = server.roomMgr.memberJoin(name, tmpStr[1], tmpStr[2]);
							}
							else {
								//no password
								t = server.roomMgr.memberJoin(name, tmpStr[1], null);
							}
							if (t == 0) {
								//Tell everyone in the old room and new room about the connection:
								server.msgBrodcastRoom(tmpStr[1], "[ User " + name + " joined the room ]");
								server.setClientRoom(socket, tmpStr[1]);
								server.msgBrodcastRoom(oldRoom, "[ User " + name + " has left the room ]");
								server.roomMgr.memberLeave(name, oldRoom);
								
								//Update room friends list:
								server.friendsRoomBrodcast(tmpStr[1]);
								server.friendsRoomBrodcast(oldRoom);
								
								outBuff.println("[ You are now in room " + server.getRoomName(socket)+ ", " + server.roomMgr.getRoomWelcome(server.getRoomName(socket)) + "]");
								outBuff.flush();
							}
							if (t == 1) {
								//Room is full:
								outBuff.println("[ Room is full, try again later ]");
								outBuff.flush();
							}
							if (t == 2) {
								//Member already in this room:
								outBuff.println("[ You are already in '" + server.getRoomName(socket) + "' room ]");
								outBuff.flush();
							}
							if (t == 3) {
								//password incorrect:
								outBuff.println("[ Incorrect room password ]");
								outBuff.flush();
							}
							if (t == 4) {
								//password incorrect:
								outBuff.println("[ There are too many rooms open at the moment, try again later ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Invalid Room Name ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//Exit switch:
					if (tmpStr[0].equals("/exit")) {
						String oldRoom = server.getRoomName(socket);
						
						//no password
						t = server.roomMgr.memberJoin(name, "Lobby", null);
						
						if (t == 0) {
							//Tell everyone in the old room and new room about the connection:
							server.msgBrodcastRoom("Lobby", "[ User " + name + " joined the room ]");
							server.setClientRoom(socket, "Lobby");
							server.msgBrodcastRoom(oldRoom, "[ User " + name + " has left the room ]");
							server.roomMgr.memberLeave(name, oldRoom);
							
							//Update room friends list:
							server.friendsRoomBrodcast("Lobby");
							server.friendsRoomBrodcast(oldRoom);
							
							outBuff.println("[ You are now in Lobby, " + server.roomMgr.getRoomWelcome(server.getRoomName(socket)) + " ]");
							outBuff.flush();
						}
						if (t == 1) {
							//Room is full:
							outBuff.println("[ Room is full, try again later ]");
							outBuff.flush();
						}
						if (t == 2) {
							//Member already in this room:
							outBuff.println("[ You are already in Lobby ]");
							outBuff.flush();
						}
						if (t == 3) {
							//password incorrect:
							outBuff.println("[ Incorrect room password ]");
							outBuff.flush();
						}
						if (t == 4) {
							//password incorrect:
							outBuff.println("[ There are too many rooms open at the moment, try again later ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//Rooms list switch:
					if (tmpStr[0].equals("/rooms")) {
						outBuff.println(server.roomMgr.getRoomsList());
						outBuff.flush();
						cmdFlag = true;
					}
					
					//Welcome switch:
					if (tmpStr[0].equals("/welcome")) {
						//Check permissions:
						if (server.roomMgr.isAdmin(name, server.getRoomName(socket))) {
							//check validity of parameters:
							if (tmpStr.length > 1) {
								StringBuffer welcomeMsg = new StringBuffer();
								int i;
								for (i=1 ; i<tmpStr.length ; i++) {
									welcomeMsg.append(tmpStr[i]);
									welcomeMsg.append(" ");
								}
								server.roomMgr.getChatRoom(server.getRoomName(socket)).setWelcomeMessage(welcomeMsg.toString());
								outBuff.println("[ Welcome message is now: "+ welcomeMsg.toString() +" ]");
								outBuff.flush();
							}
							else {
								server.roomMgr.getChatRoom(server.getRoomName(socket)).setWelcomeMessage("Welcome!");
								outBuff.println("[ Welcome message cleared ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can set welcome message ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//Private message to user switch:
					if (tmpStr[0].equals("/msg")) {
						//check validity of parameters:
						if (tmpStr.length > 2) {
							if (server.isClientExists(tmpStr[1])) {
								StringBuffer msg = new StringBuffer();
								msg.append("[ Private Message from " + name + " ] ");
								int i;
								for (i=2 ; i<tmpStr.length ; i++) {
									msg.append(tmpStr[i]);
									msg.append(" ");
								}
								server.msgSendMember(tmpStr[1], msg.toString());
								outBuff.println("[ Message sent to " + tmpStr[1] + " ]");
								outBuff.flush();
							}
							else {
								outBuff.println("[ No user by that name ]");
								outBuff.flush();
							}
						}
						cmdFlag = true;
					}
					
					//Kick switch:
					if (tmpStr[0].equals("/kick")) {
						//check permissions:
						if (server.roomMgr.getChatRoom(server.getRoomName(socket)).isAdminPermission(name)) {
							//check validity of parameters:
							if (tmpStr.length > 1) {
								if (!tmpStr[1].equals(name)) {
								if (server.roomMgr.isMemberInRoom(tmpStr[1], server.getRoomName(socket))) {
									server.msgSendMember(tmpStr[1], "/" + line);
								}
								else {
									outBuff.println("[ No user by that name ]");
									outBuff.flush();
								}
								}
								else {
									outBuff.println("[ You can't kick yourself.. type '/exit' to leave room... ]");
									outBuff.flush();
								}
							}
							else {
								outBuff.println("[ No one to kick ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can kick ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//Set or Remove room password switch: 
					if (tmpStr[0].equals("/pass")) {
						//check permissions:
						if (server.roomMgr.getChatRoom(server.getRoomName(socket)).isAdminPermission(name)) {
							//check validity of parameters:
							if (tmpStr.length > 1) {
								server.roomMgr.setRoomPassword(server.getRoomName(socket), tmpStr[1]);
								outBuff.println("[ Password set ]");
								outBuff.flush();
							}
							else {
								server.roomMgr.setRoomPassword(server.getRoomName(socket), null);
								outBuff.println("[ Password cleared ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can set/clear password ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//Set admin permission to friend switch:
					if (tmpStr[0].equals("/op")) {
						//check permissions:
						if (server.roomMgr.getChatRoom(server.getRoomName(socket)).isAdminPermission(name)) {
							//check validity of parameters:
							if (tmpStr[1] != null) {
								if (server.roomMgr.isMemberInRoom(tmpStr[1], server.getRoomName(socket))) {
									if (server.roomMgr.setMemberPermissions(server.getRoomName(socket), tmpStr[1], 0)) {
										outBuff.println("[ User " + tmpStr[1] + " had been opped ]");
										outBuff.flush();
										server.friendsRoomBrodcast(server.getRoomName(socket));
									}
									else {
										//This will happen only if member leave at same time as being opped...
										outBuff.println("[ Member is not in this room anymore ]");
										outBuff.flush();
									}
								}
								else {
									outBuff.println("[ No user by that name ]");
									outBuff.flush();
								}
							}
							else {
								outBuff.println("[ No one to op ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can give op ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//Revoke admin permission to friend switch:
					if (tmpStr[0].equals("/deop")) {
						//check permissions:
						if (server.roomMgr.getChatRoom(server.getRoomName(socket)).isAdminPermission(name)) {
							//check validity of parameters:
							if (tmpStr[1] != null) {
								if (!tmpStr[0].equals(name)) {
									if (server.roomMgr.isMemberInRoom(tmpStr[1], server.getRoomName(socket))) {
										if (server.roomMgr.setMemberPermissions(server.getRoomName(socket), tmpStr[1], 1)) {
											outBuff.println("[ User " + tmpStr[1] + " had been opped ]");
											outBuff.flush();
											server.friendsRoomBrodcast(server.getRoomName(socket));
										}
										else {
											//This will happen only if member leave at same time as being opped...
											outBuff.println("[ Member is not in this room anymore ]");
											outBuff.flush();
										}
									}
									else {
										outBuff.println("[ No user by that name ]");
										outBuff.flush();
									}
								}
								else {
									outBuff.println("[ You cannot deop yourself ]");
									outBuff.flush();
								}
							}
							else {
								outBuff.println("[ No one to deop ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can deop ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//silence switch
					if (tmpStr[0].equals("/sil")) {
						//check permissions:
						if (server.roomMgr.getChatRoom(server.getRoomName(socket)).isAdminPermission(name)) {
							//check validity of parameters:
							if (tmpStr[1] != null) {
								if (!tmpStr[0].equals(name)) {
									if (server.roomMgr.isMemberInRoom(tmpStr[1], server.getRoomName(socket))) {
										if (server.roomMgr.setMemberPermissions(server.getRoomName(socket), tmpStr[1], 2)) {
											outBuff.println("[ User " + tmpStr[1] + " had been Silenced ]");
											server.msgSendMember(tmpStr[1], "[ You have been silenced by admins ]");
											outBuff.flush();
											server.friendsRoomBrodcast(server.getRoomName(socket));
										}
										else {
											//This will happen only if member leave at same time as being opped...
											outBuff.println("[ Member is not in this room anymore ]");
											outBuff.flush();
										}
									}
									else {
										outBuff.println("[ No user by that name ]");
										outBuff.flush();
									}
								}
								else {
									outBuff.println("[ You cannot silence yourself ]");
									outBuff.flush();
								}
							}
							else {
								outBuff.println("[ No one to silence ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can silence ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					//UnSilence switch
					if (tmpStr[0].equals("/unsil")) {
						//check permissions:
						if (server.roomMgr.getChatRoom(server.getRoomName(socket)).isAdminPermission(name)) {
							//check validity of parameters:
							if (tmpStr[1] != null) {
								if (!tmpStr[0].equals(name)) {
									if (server.roomMgr.isMemberInRoom(tmpStr[1], server.getRoomName(socket))) {
										if (server.roomMgr.setMemberPermissions(server.getRoomName(socket), tmpStr[1], 1)) {
											outBuff.println("[ User " + tmpStr[1] + " had been UnSilenced ]");
											server.msgSendMember(tmpStr[1], "[ You have been UnSilenced by admins (You can speak now!) ]");
											outBuff.flush();
											server.friendsRoomBrodcast(server.getRoomName(socket));
										}
										else {
											//This will happen only if member leave at same time as being opped...
											outBuff.println("[ Member is not in this room anymore ]");
											outBuff.flush();
										}
									}
									else {
										outBuff.println("[ No user by that name ]");
										outBuff.flush();
									}
								}
								else {
									outBuff.println("[ You cannot UnSilence yourself ]");
									outBuff.flush();
								}
							}
							else {
								outBuff.println("[ No one to UnSilence ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Only Admins can UnSilence ]");
							outBuff.flush();
						}
						cmdFlag = true;
					}
					
					if (tmpStr[0].equals("/cmd")) {
						outBuff.println("[ Commands: ");
						outBuff.println("  ---------------- ");
						outBuff.println("    /cmd	-This Message.");
						outBuff.println("    /rooms	-Display list of open rooms.");
						outBuff.println("    /nick NewName	-Change your nickname.");
						outBuff.println("    /j RoomName [Password]	-join a room.");
						outBuff.println("    /msg UserName	-Private message to another member (All rooms).");
						outBuff.println("    /kick UserName	-Kick a member from room.");
						outBuff.println("    /op UserName	-Give admin rights to another user.");
						outBuff.println("    /deop UserName	-Revoke admin rights from user.");
						outBuff.println("    /sil UserName	-Silence a user.");
						outBuff.println("    /unsil UserName	-UnSilence a user.");
						outBuff.println("    /welcome [MsgBody]	-Set Room welcome message. ]");
						outBuff.println("    /pass [Password]	-Sets or removes Room password. ]");
						outBuff.println("    /exit	-Exit current room and joins Lobby. ]");
						outBuff.flush();
						cmdFlag = true;
					}
					
					//if no switch was issued then send the msg for all the rest:
					if (!cmdFlag) {
						if (line.charAt(0) != '/') {
							if (server.roomMgr.isWritePermission(name, server.getRoomName(socket))) {
								server.msgBrodcastRoom(server.getRoomName(socket), name + " (" + server.getRoomName(socket) + "): " + line);
							}
							else {
								outBuff.println("[ you do not have permission to write in this room ]");
								outBuff.flush();
							}
						}
						else {
							outBuff.println("[ Invalid command, type '/cmd' to view all available commands ]");
							outBuff.flush();
						}
					}
				}
			}
			this.stop();
		} 	
		catch (IOException e) {
			// TODO Auto-generated catch block
			server.output(e.getMessage());
		}
	}
}

/* EOF */
