Array list operations
========

###What's it?
It provides a simple framework of Java list array operations.
<br>You can easily customize it through relevant interface for more operations,
but it's not strictly verified even reviewed in public project, you should know it.

###For waht?
Actually, as a Java beginning developer, I didn't find a very convenience and pretty interface for array's operation in JDK, such as add, subtract, filter. 
<br>You might think of the `Arrays` utilities, but it still can not satisfy above requirement.
So I try to create this project for my daily work and share it for people who have the same meet.

### NOTE
Again, I don't have any evidence to prove it's a right implementation or design for Java's list array operation.
<br>You'd better look for some more official and reviewed code, It's just used in my daily work, and share it just for studying concern.

###How to use
Coding like this:
```
import static net.haibo.utils.ArrayOps.*;


String s = "no news is a good news";
List<String> the = new ArrayList<String>(Arrays.asList(s.split("[\\s]")));
System.out.println(the);

List<String> filter = arrayBuilder(the).filter(sMatchWith("^.*s.*$")).build();
List<String> sub = arrayBuilder(the).subtract(filter).build();
List<String> add = arrayBuilder(sub).add(filter).build();
System.out.println(filter); System.out.println(sub); System.out.println(add);

```

<p>
THE OUTPUT SHOULD BE:
```
[no, news, is, a, good, news]
[news, is, news]
[no, a, good]
[no, a, good, news, is, news]
```
