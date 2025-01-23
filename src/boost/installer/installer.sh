#!/bin/bash
# 安装路径
BOOST_INSTALL_DIR="/usr/local/boost"
BIN_DIR="/usr/local/bin"

# 下载 Boost 安装包
wget https://lucheshidi.github.io/boost/boost-1.0.tar.gz
# 解压 Boost 安装包
tar -xzvf boost-1.0.tar.gz -C /usr/local/
rm -rf ./boost-1.0.tar.gz

# 为 boost 创建全局命令（软链接）
ln -sf "$BOOST_INSTALL_DIR/bin/boost" "$BIN_DIR/boost"
echo "Boost has been installed globally. Use 'boost <command>' to run."
