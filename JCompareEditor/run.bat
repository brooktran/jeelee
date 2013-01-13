@echo off
:: ----------------------------------------------------------------------------
::
::   Licensed----------------------------------------------------------------------------


:: ----- Set Up The Runtime Classpath -----------------------------------------
set cp=.;\lib\jdom\jdom.jar \lib\jdom\jaxen.jar \lib\jdom\saxpath.jar \lib\jdom\xalan.jar \lib\jdom\xerces.jar \lib\jdom\xml-apis.jar \lib\Application.jar 




cd src

javac -d ..\bin -classpath %cp% -sourcepath org org/compare/action/*.java org/compare/*.java 

cd ..\bin
java -cp=%cp% org.compare.Comparer

goto clearup

:clearup
set cp=
pause