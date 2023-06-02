# sugarbag

## Welcome to **SugarBag**
## The shorter, faster-Javac annotation extra pack for syntactic sugar!

## Contents
#### 1. What is Sugarbag?
#### 2. What is the Purpose of development?
#### 3. Getting Started
#### 4. Contributing Changes

------

### What is Sugarbag?
Novice developers have a lot of difficulties in development. One of them would be allowing unnecessary overhead due to errors in syntax or lack of understanding of algorithms. Sugarbag is javac plug-in for providing help to the developers.

------

### What is the Purpose of development?
1. Help developers debug easier and faster<br>
Our plug-in can help developers debug by supporting parameter checking, tracing variables, etc. 
2. Free developers from chores<br>
Our plug-in can free developers from chores by defining useful annotations like @Getter and @Setter that prevent developers from writing getter and setter methods with their own hands.

---

### Getting Started
#### Setup
* jdk-11.X.X
* gradle 8.1.1

#### Build
##### In case you need the plugin.jar file
* Start at the **plugin/** directory. 
```
$ cd plugin/
```
* Compile **plugin** necessary files.
```
$ javac -d bin/main --add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED /src/main/java/edu/handong/csee/se/sugarbag/plugin/*.java /src/main/java/edu/handong/csee/se/sugarbag/plugin/treescanner/*.java /src/main/java/edu/handong/csee/se/sugarbag/plugin/annotations/*.java /src/main/java/edu/handong/csee/se/sugarbag/plugin/utils/*.java
```
* Make the **jar** file.
    * In the **plugin/bin/main/** directory.
```
$ cd bin/main/
$ jar -cf plugin.jar edu/
```
* Include **META-INF** contents.
    * In the **plugin/bin/main/** directory.
```
$ jar uf plugin.jar META-INF/services/com.sun.source.util.Plugin
```
##### plugin.jar file created
* Execution of the program
```
$ gradle run
```

---

### Related Projects
- [Creating a Java Compiler Plugin - baeldung](https://www.baeldung.com/java-build-compiler-plugin)
    - [Github](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-sun)

---

### Contributing Changes
1. Open an issue of your proposal to our [Issues](https://github.com/hahyun8587/sugarbag/issues) tab.
2. Make sure duplicate patches aren't listed.
3. Fork the repository to apply and test your code.
4. Follow the style of which is already present in the code.
5. Create matching unit test cases for your changes.
6. Submit a pull request to the [Pull requests](https://github.com/hahyun8587/sugarbag/pulls) tab.
