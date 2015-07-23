For now, you can run tests with:

```bash
NAME='hi' FOO='1' BAR='true' mvn clean verify
```

# Usage

1. Write a file annotated with @Configuration - [MyConfiguration.java](https://github.com/shalecraig/configur8r/blob/master/sample/src/main/java/com/shale/sample/MyConfiguration.java) is a good example.
2. Compile with environment variables - `NAME='hi' FOO='1' BAR='true'`
3. Use the generated file, like `MyConfiguration generated = MyConfiguration__Generated.populate();`

TODO: improve the documentation
