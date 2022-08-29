
下面列出了 `@Pattern` 各项代表的代码模板.

The code templates represented by `@Pattern` are listed below.

### `EagerLoading`

```java
public class $Skeleton
{
    private static final Skeleton _instance = new Skeleton();
    public static Skeleton instance()
    {
        return _instance;
    }
}
```

### `LazyLoading`

```java
public class $Skeleton
{
    private static Skeleton _instance = null;
    public static Skeleton instance()
    {
        if(_instance == null)
        {
            _instance = new Skeleton();
        }
        return _instance;
    }
}
```

### `Synchronized`

```java
public class $Skeleton
{
    private static Skeleton _instance;
    public static synchronized Skeleton getInstance()
    {
        if(_instance == null)
            _instance = new Skeleton();
        return _instance;
    }
}
```

### `DoubleCheckLocking`

```java
public class $Skeleton
{
    private volatile static Skeleton _instance;
    public static Skeleton instance()
    {
        if(_instance == null)
        {
            synchronized (Skeleton.class)
            {
                if(_instance == null)
                    _instance = new Skeleton();
            }
        }
        return _instance;
    }
}
```

### `BillPugh`

```java
public class $Skeleton
{
    private static class $$Skeleton
    {
        private static final Skeleton instance = new Skeleton();
    }

    public static Skeleton instance()
    {
        return $$Skeleton.instance;
    }
}
```
