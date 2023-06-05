# SugarBag

## Welcome to **SugarBag**
## The shorter, faster-Javac annotation extra pack for syntactic sugar!

## Contents
#### 1. What is Sugarbag?
#### 2. What is the Purpose of development?
#### 3. Getting Started
#### 4. Running Program
#### 5. Contributing Changes

------

## 1. What is Sugarbag?
Novice developers have a lot of difficulties in development. One of them would be allowing unnecessary overhead due to errors in syntax or lack of understanding of algorithms. Sugarbag is javac plug-in for providing help to the developers.

------

## 2. What is the Purpose of development?
1. Help developers debug easier and faster<br>
Our plug-in can help developers debug by supporting parameter checking, tracing variables, etc. 
2. Free developers from chores<br>
Our plug-in can free developers from chores by defining useful annotations like @Getter and @Setter that prevent developers from writing getter and setter methods with their own hands.

---

## 3. Getting Started
### Setup
* jdk-11.X.X
* gradle 8.1.1

### Build
You would need three files for running the program.
- plugin.jar 
- application.jar
- build.json

### Creating plugin.jar
* Start at the **plugin/** directory. 
```
~:$ cd plugin
```
* Compile **plugin** necessary files.
```
~/plugin:$ javac -d bin/main --add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED src/main/java/edu/handong/csee/se/sugarbag/plugin/*.java src/main/java/edu/handong/csee/se/sugarbag/plugin/treescanner/*.java src/main/java/edu/handong/csee/se/sugarbag/plugin/annotations/*.java src/main/java/edu/handong/csee/se/sugarbag/plugin/utils/*.java
```
* Create the **jar** file.
    * In the **plugin/bin/main/** directory.
```
~/plugin:$ cd bin/main
~/plugin/bin/main:$ jar -cf plugin.jar edu META-INF
```

### Creating application.jar
* start at the application/ directory
```
~:$ cd application
~/application:$ gradle jar
```
the output jar will be located at <code>~/application/build/libs</code>

### Writing build.json
You can write build.json to configure your environment paths.
- mainClass: your main class to be executed
- plugin: path to <code>plugin.jar</code> 
- dependencies: external libraries  
- classFileDir: <code>.class</code> file output directory
- dependencyDir: dependency directory for searching the dependencies

## 4. Running
1. Place <code>application.jar</code> at your root directory.
2. Place <code>plugin.jar</code> directory where you setted in <code>build.json</code>.
```
~workspace:$ java -jar application.jar
```

## 5. Contributing Changes
1. Open an issue of your proposal to our [Issues](https://github.com/hahyun8587/sugarbag/issues) tab.
2. Make sure duplicate patches aren't listed.
3. Fork the repository to apply and test your code.
4. Follow the style of which is already present in the code.
5. Create matching unit test cases for your changes.
6. Submit a pull request to the [Pull requests](https://github.com/hahyun8587/sugarbag/pulls) tab.

---

### Related Projects
- [Creating a Java Compiler Plugin - baeldung](https://www.baeldung.com/java-build-compiler-plugin)
    - [Github](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-sun)

---
