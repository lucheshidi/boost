@echo off
SET BOOST_INSTALL_DIR="C:\Program Files\boost"

rem download
echo "Downloading ..."
timeout 3 /nobreak
curl -O "https://lucheshidi.github.io/boost/boost-1.0.tar.gz"
echo "Creating files ..."
mkdir %BOOST_INSTALL_DIR%
copy boost-1.0.tar.gz %BOOST_INSTALL_DIR%

cd %BOOST_INSTALL_DIR%
echo "Unpacking ..."
tar -xf boost-1.0.tar.gz -C %BOOST_INSTALL_DIR%
rem copy files to ./
xcopy .\boost\* %CD% /E /I
del .\boost\
rem create variables
SETX BOOST_HOME "C:\Program Files\boost" /M
SETX Path "%Path%;%BOOST_HOME%\bin" /M

rem successful
echo "Boost has been installed globally. Use 'boost <command>' to run."