@echo off
:: boost.bat - Command Line Bootstrapper for Windows

:: 定义 Boost 核心路径
set BOOST_HOME=C:\Program Files\Boost
set BOOST_CORE=%BOOST_HOME%\lib\boost-core.jar

:: 确保核心文件存在
if not exist "%BOOST_CORE%" (
    echo Error: Boost core not found in "%BOOST_CORE%"
    exit /b 1
)

:: 执行 Java 核心程序
java -jar "%BOOST_CORE%" %*