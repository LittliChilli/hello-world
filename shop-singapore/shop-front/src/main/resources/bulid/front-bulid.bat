
@echo.
@echo    -------------------------------------------
@echo.
@echo	将front工程和 api工程 打包到tomcat并启动.
@echo.  按任意键执行
@echo.
@echo    -------------------------------------------
@echo.
@echo.
@echo           Powered By XXX
@echo.
@echo.
@echo.
@echo.
@pause
@echo off

@echo. 关闭tomcat
call D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\bin\shutdown.bat

@echo. 复制front工程
rmdir /s/q D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\ROOT

xcopy E:\workspaces\mmfq\fenqi\fenqi-front\target\fenqi-front\* D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\ROOT /e/i

@echo. 复制pc工程
rmdir /s/q D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\pc

xcopy E:\workspaces\mmfq\fenqi\fenqi-computer\target\fenqi-computer\* D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\pc /e/i

@echo. 复制api工程
@echo.rmdir /s/q D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\api

@echo.xcopy E:\workspaces\mmfq\fenqi\fenqi-api\target\fenqi-api\* D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\api /e/i

@echo. 复制static工程
rmdir /s/q D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\static

xcopy E:\workspaces\mmfq\fenqi\fenqi-statics\target\fenqi-statics\* D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\webapps\static /e/i

@echo. 启动tomcat
call D:\apache-tomcat-8.0.36-windows-x64\apache-tomcat-8.0.36\bin\startup.bat

@echo. 结束