# Motivation

I really liked the clean design of Ruby's [Configatron](https://github.com/markbates/configatron), and wanted to play around with implementing the same thing in Java.

This pulls variables/configuration into generated source-code at compile-time, so you don't need to check in your configuration variables.

At one point in time, I looked into allowing you to pull in the variables at runtime too, but haven't implemented it yet.

In either case, there should be (is?) a check to see if all the required variables are present.

# Status

- Don't use this in production, I wrote it for fun over a ~weekend.
- Right now, this will inline ENV variables into the configuration at compile-time.
- At some point in time, this will pull from a YAML. That day is not today.
- At some point in time, this will let you differentiate from compile-time variables (e.g. "database URL", etc) and runtime variables (e.g. "database_password")

# Testing

For now, you can run tests with:

```bash
NAME='hi' FOO='1' BAR='true' mvn clean verify
```

# Usage

1. Write a file annotated with @Configuration - [MyConfiguration.java](https://github.com/shalecraig/configur8r/blob/master/sample/src/main/java/com/shale/sample/MyConfiguration.java) is a good example.
2. Compile with environment variables that match the attributes in your configuration file - `NAME='hi' FOO='1' BAR='true' mvn install`
3. Use the generated instance of the configuration: `MyConfiguration generated = MyConfiguration__Generated.populate();`

TODO: improve the documentation
