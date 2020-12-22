# 常见问题解决
###端口专用
报错提示:
Web server failed to start. Port 8080 was already in use.

解决方法:

cmd窗口:

netstat -ano | findstr 8080

taskkill -PID 查到的进程号 -F
