/*
 * ?寶擔婜 2005-7-8
 *
 * TODO 梫峏夵崯惗惉揑暥審揑柾斅丆??帄
 * 鈞岥 亅 庱?? 亅 Java 亅 戙??幃 亅 戙?柾斅
 */
package notepad;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author zhh
 *
 * TODO 梫峏夵崯惗惉揑?宆拲?揑柾斅丆??帄
 * 鈞岥 亅 庱?? 亅 Java 亅 戙??幃 亅 戙?柾斅
 */
public class N_color extends Dialog {
    private Button OK,Cancel; 
   // private I_Method mp; 
    private Rectangle test; 

    private Color current= Color.black; 
    private Color []colors; 
    private Color []colors1; 
    private Color []colors2; 
    private int strlen= 250; 

    public N_color(Frame fr,int x,int y) 
    { 
    super(fr,"?怓斅",true); 
  //  this.mp= mp; 
    colors= new Color[strlen]; 
    colors1= new Color[strlen]; 
    colors2= new Color[strlen]; 
    for (int i = 0; i < strlen; i++) 
    { 
    float h = ((float)i)/((float)strlen); 
    colors[i] = new Color(Color.HSBtoRGB(h,1.0f,1.0f)); 
    } 
    for (int i = 0; i < strlen; i++) 
    { 
    float h = ((float)i)/((float)strlen); 
    colors1[i] = new Color(Color.HSBtoRGB(1.0f,h,1.0f)); 
    } 
    for (int i = 0; i < strlen; i++) 
    { 
    float h = ((float)i)/((float)strlen); 
    colors2[i] = new Color(Color.HSBtoRGB(1.0f,1.0f,h)); 
    } 

    setLayout(null); 
    OK= new Button("?掕"); 
    Cancel= new Button("庢徚"); 
    OK.reshape(320,100,80,30); 
    add(OK); 
    Cancel.reshape(320,150,80,30); 
    add(Cancel); 
    test= new Rectangle(0,0,300,250); 
    reshape(x/2-210,y/2-140,420,280); 
    show(); 
    } 

    public void update(Graphics g) 
    { 
    int y= 0; 
    for (int i = 0; i < strlen; i++) 
    { 
    g.setColor(colors[i]); 
    g.fillRect(0,y,100,1); 
    y+=1; 
    } 
    y= 0; 
    for (int i = 0; i < strlen; i++) 
    { 
    g.setColor(colors1[i]); 
    g.fillRect(100,y,100,1); 
    y+=1; 
    } 
    y= 0; 
    for (int i = 0; i < strlen; i++) 
    { 
    g.setColor(colors2[i]); 
    g.fillRect(200,y,100,1); 
    y+=1; 
    } 
    g.setColor(current); 
    g.fillRect(330,30,60,60); 
    } 

    public void paint(Graphics g) 
    { 
    update(g); 
    } 

    public boolean mouseMove(Event evt,int x,int y) 
    { 
    if(test.inside(x,y)) 
    { 
    Graphics g1= getGraphics(); 
//    g1.drawImage(back,oldx,oldy); 
//    g1.clipRect(x-3,y-3,6,6); 
    g1.setColor(Color.white); 
    g1.drawArc(x-3,y-3,6,6,60,360); 
    g1.drawLine(x-3,y,x+3,y); 
    g1.drawLine(x,y-3,x,y+3); 
    repaint(); 
//    oldx= x-3; 
//    oldy= y-3; 
    return true; 
    } 
    return false; 
    } 


    public boolean mouseDown(Event evt,int x,int y) 
    { 
    if((x>=0)&&(x<=100)) 
    { 
    current= colors[y]; 
    System.out.println(y); 
    getGraphics().setColor(current); 
    getGraphics().fillRect(330,30,60,60); 
    return true; 
    } 
    if((x>100)&&(x<=200)) 
    { 
    current= colors1[y]; 
    getGraphics().setColor(current); 
    getGraphics().fillRect(330,30,60,60); 
    return true; 
    } 
    if((x>200)&&(x<=300)) 
    { 
    current= colors2[y]; 
    getGraphics().setColor(current); 
    getGraphics().fillRect(330,30,60,60); 
    return true; 
    } 
    return false; 
    } 

    public boolean action(Event evt,Object arg) 
    { 
    if(evt.target instanceof Button) 
    { 
    if(evt.target== OK) 
    { 
   // mp.setcolor(current); 
    dispose(); 
    return true; 
    } 
    if(evt.target== Cancel) 
    { 
    dispose(); 
    return true; 
    } 
    } 
    return super.action(evt,arg); 
    } 
}
