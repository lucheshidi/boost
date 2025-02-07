package com.boost;

public class DSLParserUtils {

    // 提取顶级块内容，支持嵌套 {}
    public static String extractBlock(String content, String blockName) {
        int startIdx = content.indexOf(blockName + " {");
        if (startIdx == -1) {
            return null; // 未找到块
        }

        // 定位块开始的第一个 '{'
        int blockStart = content.indexOf("{", startIdx);
        if (blockStart == -1) {
            return null; // 未找到 '{'
        }

        // 使用计数器提取匹配的 { ... }
        int openBraces = 0;
        int blockEnd = blockStart;
        for (int i = blockStart; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '{') {
                openBraces++;
            } else if (c == '}') {
                openBraces--;
                if (openBraces == 0) {
                    blockEnd = i;
                    break;
                }
            }
        }

        if (openBraces != 0) {
            throw new IllegalStateException("Unmatched braces in block: " + blockName);
        }

        // 返回完整块内容，包括 { 和 }
        return content.substring(blockStart + 1, blockEnd).trim();
    }
}
