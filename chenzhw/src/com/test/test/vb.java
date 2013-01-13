package com.test.test;

import java.util.List;
import java.util.Random;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-12-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class vb {
		
	public static void main(String[] args) {
		int A[]=new int[20];
		Random rm=new Random();
		for(int i=0;i<20;i++){
			A[i]=Math.abs(rm.nextInt(40)-20);
			System.out.print(" "+A[i]);
		}
		System.out.println();
	}
	

}
/*
Option Base 1
Dim A(20) As Integer

Private Sub Command1_Click()
Dim i As Integer
Dim s As String
For i = 1 To 20
    A(i) = Int((40 - 20 + 1) * Rnd) + 20
    Text1.Text = Text1.Text & Str(A(i))
    If i = 10 Then
        Text1.Text = Text1.Text & vbCrLf
    End If
Next i
End Sub

Private Sub Command2_Click()
Dim B(20 To 40) As Integer
For i = 1 To 20
    B(A(i)) = B(A(i)) + 1
Next i
For i = 1 To 20
    List1.AddItem A(i) & "出现：" & B(A(i)) & "次"
Next i
End Sub

Private Sub Command3_Click()
Text1.Text = ""
List1.Clear
End Sub

Private Sub Command4_Click()
Unload Me
End Sub*/