package com.boost;

public interface BuildFile {
    void build(BuildContext context);

    void dependencies(BuildContext context);

    void test(BuildContext context);
}
