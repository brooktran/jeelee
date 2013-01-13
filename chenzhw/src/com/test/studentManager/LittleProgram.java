/*import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;


* Created on 2005-1-11


*//**
* @author 让炜
* @since 1.0
*
* TODO 学生成绩管理系统
* 通过学号查找,修改,删除数据
* 
*//*
public class LittleProgram 
{
  static boolean isDelete = true;
  static boolean isFind = true;
  public static void main(String [] args)//主方法,程序从这里开始运行 
  throws IOException,NumberNotFoundException
  {
    int choice=-1;
    do{
        LittleProgram lp = new LittleProgram();
        System.out.println();
        System.out.println("\t####################################");
        System.out.println();
        System.out.println("\t\t Java学生成绩管理系统1.1");
        System.out.println("\t\t请用学号查找,修改,删除数据");
        System.out.println();
        System.out.println("\t####################################\n");
        System.out.print("1.增加数据:\n"+
            "2.查找数据:\n"+
            "3.删除数据:\n"+
            "4.清除所有数据:\n"+
            "5.把数据全部打印到屏幕\n"+
            "6.把成绩按学号排序\n"+
            "7.修改数据\n"+
            "8.统计已记录成绩学生数\n"+
            "9.关于作者\n"+
            "0.退出程序.\n" +
            "输入:");
        BufferedReader in =                   //从终
          new BufferedReader(               //端接
                new InputStreamReader(System.in));//收数
        String inputLine = in.readLine();         //字选
        choice= Integer.valueOf(inputLine).intValue();//项;
        switch(choice)
        {
        case 1: {//1.增加数据             
          String str = lp.inputData();
          lp.addData(str);
          System.out.println("增加数据成功.");
          timeOut(1);
        }break;
        case 2: {//2.查找数据
          long find = 0;
          System.out.print("请输入你要查找的学生学号:");
          BufferedReader inn =
            new BufferedReader(
                  new InputStreamReader(System.in));
          String inputLi = inn.readLine();
          find = Integer.valueOf(inputLi).longValue();
          lp.findData(find);
          
          timeOut(2);
        }break;
        case 3: {//3.删除数据
          long deleteNumber = 0;
          System.out.print("请输入你想删除的同学的学号:");
          BufferedReader bf =
            new BufferedReader (
                  new InputStreamReader(System.in));
          String inputL = bf.readLine();
          deleteNumber = Integer.valueOf(inputL).longValue();
          lp.deleteData(deleteNumber);
          if(isDelete)
          System.out.println("删除数据成功!");
          timeOut(1);
        }break;
        case 4: {   
          lp.clearData();//4.清除所有数据
          timeOut(1);
        }break;
        case 5: {
          print();//5.把数据全部打印到屏幕
          timeOut(2);
        }break;
        case 6: {
          lp.numSort();//6.把成绩按学号排序 
          System.out.println("按照学号从小到大排序成功!\n"+
          "排序后:\n");
          print();
          timeOut(2);
        }break;
        case 7: {
          lp.rewrite();//7.修改数据
          timeOut(2);
        }break;
        case 8: {
          int count = lp.count();
          System.out.println("共有"+count+"个学生已经记录.");
          timeOut(2);
        }break;
        case 9: {
          System.out.print("\t\t让炜\n"+
                "\t\t上海电力学院通信工程系\n"+
                "\t\tQQ:254482170\n");
          timeOut(4);
        }break;
    }while (choice != 0);
    System.out.println("Bye! ^-^");
    System.exit(0);
  }
  public String inputData()//从终端接收数据的方法,返回字符串
  throws IOException,NumberFormatException
  {
        System.out.print("请依次输入 :学号 姓名 性别 成绩\n" +
      "每项数据请用空格隔开:");
        String all = "";
    try{
        BufferedReader in =                     //从终
          new BufferedReader (                 //端接
                new InputStreamReader(System.in));   //收数   
          String inputLine = in.readLine();         //据
          StringTokenizer str =                             
            new StringTokenizer(inputLine," ");//接收的数据用空格隔开,这个类用来提取每个字符串                 
          long num = Integer.valueOf(str.nextToken()).longValue();//学号       
          String name = (String)str.nextToken();             //姓名
          String sex = (String)str.nextToken();             //性别 
          double mark = Integer.valueOf(str.nextToken()).doubleValue();//分数 
          all = String.valueOf(num) +" , "+
          name +" , "+
          sex +" , "+
          String.valueOf(mark);//把所有的数据用" , "隔开然后在连起来放进字符串all         
    }catch (IOException e){}
      catch (NumberFormatException e){}
    return all;//返回字符串all
  }
  public void addData(String str)//增加数据的方法
  throws IOException
  {
    String s1 ="",s2="" ,s3= "";
    File file = new File("data.txt");
    if (file.exists())//如果文件data.txt存在
    {
    try{
        BufferedReader in =
          new BufferedReader(
                new FileReader("data.txt"));
        while ((s1=in.readLine())!=null)
          s2+=s1+"\n";//把文件中的每行数据全部放进一个字符串s2                 
        s2+=str+"\n";   //再把s2于形参str相连放进s2
        BufferedReader in2 =                       //把字符
          new BufferedReader(                     //串s2也 
                new StringReader(s2));             //就是原
        PrintWriter out =                         //文件+
          new PrintWriter(                       //形参str(新输入的一行数据)
                new BufferedWriter(               //重新写进data.txt
                    new FileWriter("data.txt")));   //覆盖原来的数据 
        while ((s3=in2.readLine())!= null)
        {
          out.println(s3);
        }
        out.close();
        //System.out.println("write data true.");
    }catch (IOException e){}
    }else{
        System.err.println("File \"data\" Missing!");
    }
  }
  public void clearData()//清除data.txt的所有数据的方法
  throws IOException
  {
    File file = new File("data.txt");
    if(file.exists())//如果文件在 
    {
        try{
          PrintWriter out = 
            new PrintWriter(
                  new BufferedWriter(
                        new FileWriter(file)));
          out.print("");//在文件data.txt里写进一个空字符,所以清除了原来的内容
          out.close(); //关闭文件
          System.out.println("clear data true!");
        }catch(IOException e){}
      }else{
          System.err.println("File \"data\" Missing!");
      }
  }
  public void deleteData(long deleteNumber)//删除某条数据
  throws IOException,FileNotFoundException
  {
    isDelete = true;
    try{
    DataMap mp = new DataMap();//生成一个自己编写的容器
    long j=0;
    String s1="",s2="",s3="";
        BufferedReader in =
          new BufferedReader(
                new FileReader("data.txt"));
        while ((s1=in.readLine())!=null)
        {
          j=numberTokenizer(s1);
          mp.put(j,s1);
        }
        try{
          if(mp.containsKey( String.valueOf(deleteNumber).toString()))
          {
            mp.remove(deleteNumber);
          }else 
            throw new NumberNotFoundException();
      Collection c = mp.values();
        Iterator iter = c.iterator();
        while(iter.hasNext())
        {
          s1 = (String)iter.next();
          s3 +=s1+"\n";
        }
        BufferedReader in2 =
          new BufferedReader(
                new StringReader(s3));
        PrintWriter out =
          new PrintWriter(
                new BufferedWriter(
                    new FileWriter("data.txt")));
        //System.out.println("delete No"+deleteNumber);
        while( (s1 = in2.readLine())!=null)
        {
          out.println(s1);
        }
        out.close();
        }catch (NumberNotFoundException e)
        {
          isDelete = false;
          System.out.println(deleteNumber+" no found :(");   
        }
    }catch(IOException e){}
  }
  public long numberTokenizer(String s)
  throws IOException
  {
    StringTokenizer st = 
        new StringTokenizer(s," ");
    return Integer.valueOf((st.nextToken())).longValue();
  }
  public void findData(long find)//查找数据 
  throws IOException,NumberNotFoundException
  {
    isFind = true;
    String s = "",findString ="";
    long i;
    DataMap dm = new DataMap();
    BufferedReader in =
        new BufferedReader(
            new FileReader("data.txt"));
    while ((s=in.readLine())!=null)
    {
        i=numberTokenizer(s);
        dm.put(i,s);
    }
    //in.close();
    try{
        if(dm.containsKey( String.valueOf(find).toString()))
        {
          findString = dm.get(find);
          System.out.println("学号"+find+"学生的资料是:");
          System.out.println(findString);
        }else 
          throw new NumberNotFoundException();
    }catch (NumberNotFoundException e){
        System.out.println(find+" no found :(");
        isFind = false;
    }     
  }
  public static void print()//读取文本文件把数据打印到终端的方法
  throws IOException
  {
    try{
        BufferedReader in =
          new BufferedReader(
                new FileReader("data.txt"));
        String read = "";
        while ((read = in.readLine())!=null)
          System.out.println(read);
    }catch(IOException e){}
  }
  public static void timeOut(double sec)//停顿短暂时间的一个方法完全可以不要这个功能
  {
    double seconds = sec;
    long t = System.currentTimeMillis()+(int)(seconds*1000);
    while ((System.currentTimeMillis())<t)
        ;
  }
  public void numSort()//按学号排序
  throws IOException
  {
    long i = 0;
    String s = "";
    try{
        DataArrayList dal = new DataArrayList();
        BufferedReader in =
          new BufferedReader(
                new FileReader("data.txt"));
        while ((s=in.readLine())!=null)
        {
          i=numberTokenizer(s);
          dal.add(i);
        }
        Collections.sort(dal);
        DataMap dm = new DataMap();
        BufferedReader in2 =
          new BufferedReader(
                new FileReader("data.txt"));
        while ((s=in2.readLine())!=null)
        {
          i=numberTokenizer(s);
          dm.put(i,s);
        }
        PrintWriter out =
          new PrintWriter (
                new BufferedWriter(
                    new FileWriter("data.txt")));
        Iterator it = dal.iterator();
        long temp = 0;
        String tempStr = "";
        while (it.hasNext())
        {
          temp = Integer.valueOf((String)it.next()).longValue();
          tempStr = dm.get(temp);
          out.println(tempStr);
        }
        out.close();
    }catch(IOException e){}
  }
  public void rewrite()
  throws IOException,NumberNotFoundException
  {
    try{
        System.out.print("请输入你要修改的学生学号:");
        BufferedReader in =
          new BufferedReader (
                new InputStreamReader(System.in));
        String inputLine = in.readLine();
        long num = Integer.valueOf(inputLine).longValue();
        findData(num);
        if(isFind)
        {
        deleteData(num);
        System.out.print("请重新输入该学生的资料:");
        String str = inputData();
        addData(str);
        System.out.println("rewrite true!");
        }         
    }catch(IOException e){}
    catch(NumberNotFoundException e){}
  }
  public int count()
  throws IOException 
  {
    
    DataArrayList dal = new DataArrayList();
    try{
        String s = "";
        long i =0;
        BufferedReader in =
          new BufferedReader(
                new FileReader("data.txt"));
        while ((s=in.readLine())!=null)
        {
          i=numberTokenizer(s);
          dal.add(i);
        }
    }catch(IOException e){}
    return dal.size();
  }
}

* 
* @author RangWei
* TODO 这是个我们写的一个容器,继承公共类HashMap
* 大概的功能就相当一个数组
* 

class DataMap extends HashMap//一个存储数据的Map             
{
  public void put(long i,String str)//把学号和数据放进这个Map
  {                       //以后一个学号(key)对应的是一个人的数据(value)
    put(String.valueOf(i).toString(),str);
  }
  public void remove(long i)//接收学号,然后删除学号(key)和它对应的数据(value)
  {
    remove(String.valueOf(i).toString().toString());
  }
  public String get(long i)//接收一个学号,然后返回这个key对应的value
  {
    String s = String.valueOf(i).toString();
    if (!containsKey(s))
    {
        System.err.println("Not found Key: "+s);
    }
    return (String)get(s);
  }
}

* 
* @author RangWei
*
* TODO 这个类继承ArrayList
* 用来按数字排序,在用学号排序时要用到它
* 

class DataArrayList extends ArrayList
{
  public void add(long num)
  {
    String numToString = String.valueOf(num).toString();
    add(numToString);
  }
}

* 
* @author RangWei
*
* TODO 增加的一个Exception,主要是在文件里没有要找
* 的学号就抛出
* 

class NumberNotFoundException extends Exception
{                                       
  public NumberNotFoundException()
  {}
}*/