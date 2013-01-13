/*
 *  Simple Chat room Server written by Aviad Golan...
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Button;
import java.awt.Panel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.TextArea;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Vector;


public class JTalkServer extends Frame implements ActionListener, Runnable {
	//Data members:
	private static final long serialVersionUID = 1L;	//Remove nag Eclipse warning.
	private Panel inPanel, outPanel;					//Panels
	private TextArea output;							//Output messages
	private int outCnt, maxCon,conTot;					//Server properties
	private Button btnStart, btnExit;					//Buttons
    private TextField status, port;						//Editable text fields
    private Label lblPort;								//Port label
	private ServerSocket inSock;						//Server main socket mgr
	private Socket socket;								//Temp Socket
    private boolean running;							//Run flag
    private Thread thListen;							//Listening thread
    private Vector cliSocks;							//Clients sockets list 
    private Vector cliNames;							//Clients names list
    private Vector cliRoom;								//Clients rooms list
    public ChatRoomsManager roomMgr;					//Class Room Manager
  
    //Constructor
	public JTalkServer(String caption) {
		super(caption);
		setSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		
		//Set Starting values:
		maxCon = 200;
		conTot = 0;
		inSock = null;
		outCnt = 1;
		running = false;
		cliSocks = new Vector(maxCon);
		cliNames = new Vector(maxCon);
		cliRoom = new Vector(maxCon);
		roomMgr = new ChatRoomsManager(maxCon, 20);
		
		//Create the panels and buttons:
		outPanel = new Panel(new BorderLayout());
		inPanel = new Panel(new FlowLayout());
		
		inPanel.setBackground(Color.WHITE);
		outPanel.setBackground(Color.WHITE);
		
		output = new TextArea();
		//output.setEnabled(false);
		output.setEditable(false);
		output.setBackground(Color.WHITE);
		outPanel.add(output, BorderLayout.CENTER);

		
		status = new TextField(" - To start server press 'Start Server' button... - ");
		status.setBackground(Color.PINK);
		status.setEditable(false);
		outPanel.add(status, BorderLayout.NORTH);
		
		btnStart = new Button("Start Server");
		btnStart.addActionListener(this);
		btnStart.setBackground(Color.WHITE);
		inPanel.add(btnStart);
		
		btnExit = new Button("Exit");
		btnExit.addActionListener(this);
		btnExit.setBackground(Color.BLACK);
		btnExit.setForeground(Color.WHITE);
		inPanel.add(btnExit);
		
		lblPort = new Label("Port:");
		inPanel.add(lblPort);
		
		port = new TextField("3333");
		inPanel.add(port);
		
		add(outPanel,BorderLayout.CENTER);
		add(inPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	//Buttons buttons and more buttons:
	public void actionPerformed(ActionEvent e) {
		//Start server button:
		if (e.getSource() == btnStart) {
			if (e.getActionCommand() == "Start Server") {
				btnStart.setLabel("Stop Server");
				startServer();
			}
			else {
				btnStart.setLabel("Start Server");
				stopServer();
			}
		}
		
		//Exit button:
		if (e.getSource() == btnExit) {
			stopServer();
			System.exit(0);
		}
	}

	
	//Start server method:
	public void startServer() {
		//Tell admin we are starting.
		status("Starting Server please wait...\n", Color.YELLOW);
		
		//Set the listening socket:
		if (inSock == null) {
			try {
				inSock = new ServerSocket(Integer.parseInt(port.getText()));
			}
			catch (IOException e) {
				output("Error opening Socket: " + e.getMessage() + "\n");
				stopServer();
			}
			try {
				//We wait a bit here (for the flow)...
				Thread.sleep(1000);
			}
			catch (Exception e){
				output(e.getMessage());
			}
			output("Listening to port:" + port.getText() + "\n");
		}
		else {
			output("Server is already running...\n");
		}
		
		//We got here (phew) all is good so far...
		running = true;
		
		//redirect the main to the listening thread
		thListen = new Thread(this);
		thListen.start();
		
		//show it and be proud:
		status("Server is Running...\n", Color.GREEN);
	}
	
	//The listening thread (will dispatch new thread at each connection -TCP)
	//I know UDP is a better approach here(for efficiency not security) but this will do just fine...
	public void run() {
		//run forever(or until you die muhahaha....):
		while (running){
			
			//Accept any in bound connection request (to the limit):
        	try {
        		if (inSock != null) {
        			socket = inSock.accept();
        			new ServerThread(this, socket).start();
        			
        			//document connected peers:
        			output("connected local port=" + socket.getLocalPort() + "remote port=" + socket.getPort() + "\n");
        		}
        	}
        	catch (IOException e) {
        		output("Closing Sockets:\n");
        		output(e.getMessage() + "\n");
        		break;
        	}
        }
		
		//kill listening thread: 
		stopServer();
		thListen.stop();
		thListen.destroy();
	}
	
	//Name check method (only 1 unique name for each user):
	synchronized boolean isNameTaken(String name) {
		int i;
		
		//check for names:
		for (i=0 ; i<cliNames.size() ; i++) {
			if (cliNames.get(i).equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	//Add a client to client list:
	synchronized void addClient(Socket socket, String name) {
		cliSocks.add(socket);
		cliNames.add(name);
		cliRoom.add("Lobby");
		roomMgr.memberJoin(name, "Lobby", null);
		output("User:" + name + " in Socket:" + socket.toString() + "Connected...\n");
		conTot++;
	}
	
	//Remove a client from clients list:
	synchronized void removeClient(Socket socket, String name) {
		int i;
		
		for (i=0 ; i<cliNames.size() ; i++) {
			if ((cliNames.get(i) == name) && (cliSocks.get(i) == socket)) {
				roomMgr.memberLeave(name, getRoomName(socket));
				cliRoom.remove(i);
				break;
			}
		}
		cliNames.remove(name);
		cliSocks.remove(socket);
		output("User:" + name + " in Socket:" + socket.toString() + " Disconnected...\n");
		conTot--;
	}
	
	//Client wants to change his name (who am i to stop him?)
	synchronized void setClientName(Socket socket, String oldName, String newName) {
		int i;
		
		for (i=0 ; i<cliNames.size() ; i++) {
			if ((cliNames.get(i) == oldName) && (cliSocks.get(i) == socket)) {
				cliNames.set(i, newName);
				break;
			}
		}
	}
	
	//Clients room indexes
	synchronized void setClientRoom(Socket socket, String roomName) {
		int i;
		
		for (i=0 ; i<cliNames.size() ; i++) {
			if (cliSocks.elementAt(i).equals(socket)) {
				cliRoom.set(i, roomName);
				return;
			}
		}
	}
	
	//get the room name associated with socket
	public String getRoomName(Socket socket) {
		int i;
		
		for (i=0 ; i<cliNames.size() ; i++) {
			if (cliSocks.get(i) == socket) {
				return (String)cliRoom.get(i);
			}
		}
		//if something is wrong kick user to lobby
		//We should never get here...
		return "Lobby";
	}
	
	//Stop server logic:
	public void stopServer() {
		status("Stopping Server please wait...\n", Color.YELLOW);
		if (inSock != null) {
			try {
				//Tell everyone server is going down:
				msgBrodcast("[ Server is now shutting down, sorry for that :) ]");
				msgBrodcast("//diss");
				inSock.close();
			}
			catch (IOException e) {
				output("Error Closing Socket: " + e.getMessage() + "\n");
				return;
			}
			inSock = null;
			try {
				//We wait a bit here...
				Thread.sleep(1200);
			}
			catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		
		//Set status to idle
		running = false;
		status("Server stopped...\n", Color.RED);
	}
	
	//This method will broadcast too all a message:
	synchronized void msgBrodcast(String msg) throws IOException {
		int i;
		PrintWriter tmpPw;
		Socket tmpSock;
    	for(i=0 ; i<cliSocks.size() ; i++) {
			tmpSock = (Socket)cliSocks.elementAt(i);
			tmpPw = new PrintWriter(tmpSock.getOutputStream(),true);
			tmpPw.println(msg);
			tmpPw.flush();
    	}
	}

	//This method will broadcast to members in a room a message:
	synchronized void msgBrodcastRoom(String roomName, String msg) throws IOException {
		int i;
		PrintWriter tmpPw;
		Socket tmpSock;
		
    	for(i=0 ; i<cliSocks.size() ; i++) {
    		if (cliRoom.elementAt(i).equals(roomName)) {
				tmpSock = (Socket)cliSocks.elementAt(i);
				tmpPw = new PrintWriter(tmpSock.getOutputStream(),true);
				tmpPw.println(msg);
				tmpPw.flush();
    		}
    	}
	}
	
	//This method will send a message to a specific user:
	synchronized void msgSendMember(String memberName, String msg) throws IOException {
		int i;
		PrintWriter tmpPw;
		Socket tmpSock;
		
    	for(i=0 ; i<cliSocks.size() ; i++) {
    		if (cliNames.elementAt(i).equals(memberName)) {
				tmpSock = (Socket)cliSocks.elementAt(i);
				tmpPw = new PrintWriter(tmpSock.getOutputStream(),true);
				tmpPw.println(msg);
				tmpPw.flush();
    		}
    	}
	}
	
	//This method will update all users their friends lists:
	public void friendsBrodcast() {
		StringBuffer friendsList = new StringBuffer("//fr ");
		int i;
		
		for (i=0 ; i<cliNames.size() ; i++) {
			if (cliNames.get(i) != null)
			friendsList.append(cliNames.get(i));
			if (i != cliNames.size()) {
				friendsList.append(",");
			}
		}
		try {
			msgBrodcast(friendsList.toString());
		}
		catch (Exception e) {
			output("Error: " + e.getMessage());
		}
	}

	//This method will update all users in a room their friends list:
	public void friendsRoomBrodcast(String roomName) {
		StringBuffer friendsList = new StringBuffer("//fr ");
		int i;
		
		for (i=0 ; i<cliSocks.size() ; i++) {
			if (cliRoom.elementAt(i).equals(roomName)) {
				if (cliNames.elementAt(i) != null) {
					if (roomMgr.isAdmin((String)cliNames.elementAt(i), (String)cliRoom.elementAt(i))) {
						friendsList.append("@" + cliNames.elementAt(i));
						friendsList.append(",");
					}
					else {
						if (roomMgr.isWritePermission((String)cliNames.elementAt(i), (String)cliRoom.elementAt(i))) {
							friendsList.append(cliNames.elementAt(i));
							friendsList.append(",");
						}
						else {
							friendsList.append("(-)" + cliNames.elementAt(i));
							friendsList.append(",");
						}
					}
				}
			}
		}
		try {
			msgBrodcastRoom(roomName, friendsList.toString());
		}
		catch (Exception e) {
			output("Error: " + e.getMessage());
		}
	}
	
	//This method will check available user:
	synchronized boolean isClientExists(String clientName) throws IOException {
		int i;
		
    	for(i=0 ; i<cliSocks.size() ; i++) {
    		if (cliNames.elementAt(i).equals(clientName)) {
				return true;
    		}
    	}
    	return false;
	}
	
	//Test if server is full:
	public boolean isServerFull() {
		if (conTot < maxCon) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//Output method will append to log:
	public void output(String msg) {
		output.append("[" + outCnt + "] " + msg);
		outCnt++;
	}
	
	//Status will set the current server status:
	public void status(String msg, Color c) {
		status.setText(msg);
		status.setForeground(Color.BLACK);
		status.setBackground(c);
		output.append("[" + outCnt + "] " + msg);
		outCnt++;
	}

	//Finalize, if all goes as planned exit nicely...
	protected void finalize() throws Throwable {
		if (running) {
			stopServer();
		}
		super.finalize();
	}
}

/* EOF */
