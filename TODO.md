This is the game-plan. Much further below is some spitballing I did to figure out what I wanted.

V0.0.1B
- [x] Barebones annotation processor that parses values from annotations.
    - [x] Annotation Name
    - [x] Methods:
        - [x] Name
        - [x] Return type
            - [x] String
            - [x] int
            - [x] bool
- [x] Lookup configuration via System.env
    - [x] probably encapsulate this in a "value lookup strategy"
- [x] Codegen
    - [x] Instantiate annotation
    - [x] Equality / hashcode
    - [x] Generate methods
- [ ] Release
    - [ ] Deploy to maven central
    - [ ] Create Tag on GH.

V0.1.0
- [ ] Implementation of Annotation is a top-level class, which is instantiated by `populate()`.
- [ ] Runtime lookup
- [ ] Optional/required types
- [ ] Support default values
- [ ] Travis build env
- [ ] Release
    - [ ] Deploy to maven central
    - [ ] Create Tag on GH.

V0.1.1
- [ ] Extract optional & Function logic to new library
    - [ ] FunctionX, where X is a primitive.
    - [ ] OptionalX, where X is a primitive.
    - [ ] Optionals
        - [ ] Optionals.toOptionalX(Optional<X> a, XGeneratingFunction)
- [ ] Release
    - [ ] Deploy to maven central
    - [ ] Create Tag on GH.

V0.2.0

- [ ] Named aliases
- [ ] Arrays
- [ ] Nested annotations
   - [ ] A nested annotation MUST be annotated with @Configuration (or maybe something else?)
- [ ] Release
    - [ ] Deploy to maven central
    - [ ] Create Tag on GH.

V1.0.0
- [ ] Allow users to choose Yaml over System.env
- [ ] Release
    - [ ] Deploy to maven central
    - [ ] Create Tag on GH.

V1.1.0
- [ ] Allow users to implement their own resolution providers?
    - [ ] During runtime.
    - [ ] During compilation.
    - [ ] Can we do it with the same implementation?
- [ ] Release
    - [ ] Deploy to maven central
    - [ ] Create Tag on GH.


------

Unknowns:

- Resolution:
    - Compilation-time resolution (via constants)
        - Exposed via jmx?

    - Runtime (e.g. ports/secrets)
        - Application startup should assert these are set.

- Optionality:
    - Optional
        - "Default" required
    - Required
        - "Default" not allowed


- Resolution:
    - System.env
        - Initially, everything will be from this
    - Yaml
        - V1.0?
    - Custom providers
        - V1.0?

- Frills:
    - Alias names (e.g. attribute in yaml/etc is named something different)
    - Arrays
        - V0.2
    - Re-triggering acquisition?
        - V1.5/V2.0 (It's very messy to work around this)

- Long-term:
    - JMX? Is it a good idea to re-configure at runtime?
    - Custom providers?
        - How would this work?
        -
