#### Singleton ####

##### Singleton in single thread #####
Singleton，单例模式，正如其名字看上去那样，单例的目的在于
__有且仅有一个实例存在__。最普通的单例模式实现如下，

	// version-1

	public class Singleton {
		private static Singleton instance;
		// 禁用new操作符
		private Singleton() { /* initialization */ }
		// 获取单例实例
		public static Singleton getInstance() {
			if(instance == null) {
				instance = new Singleton();
			}
			return instance;
		}
	}

##### Singleton in multi-thread 1. #####
这在单线程时没有问题，多线程时，`getInstance()`函数中的`if`语句就
可能出问题，考虑下面的场景，
> 假如有A、B两个线程，分别按照下面的步骤调用`getInstance()`函数：
> 
> 1. A线程首先执行`if(instance == null)`判断，发现`install == null`，
> 2. 此时，B线程抢的时间片，在A还没有创建instance对象的情况下，也执行`if`判断，
> 并成功创建了instance对象，
> 3. 此时A又拿到时间片，并再次创建instance对象。

可以看到这种情况下，单例模式被破坏了。既然如此，为了线程安全，那就同步吧！

	// version-2

	public class Singleton {
		private static Singleton instance;
		private Singleton() { /* initialization */ }
		// 仅仅只是添加了一个synchronized而已
		synchronized public static Singleton getInstance() {
			if(instance == null) {
				instance = new Singleton();
			}
			return instance;
		}
	}

安全是安全了，但是效率不高，毕竟`getInstance()`，绝大多数时间而是__读__，
而只有在第一次调用时才会__写__（即创建实例，为`instance`变量赋值），而
一个synchronized关键字就把多个线程并行给关闭了，效率大打折扣，所以我们不妨
只在第一次调用创建实例时做同步，

	// version-3

	public class Singleton {
		// volatile 保证先写后读(happens-before)
		private volatile static Singleton instance;

		private Singleton() { /* initialization */ }
		public static Singleton getInstance() {
			// 这个检查，防止每次调用都同步，
			// 即只在需要创建实例的时候才同步
			if(instance == null) {
				synchronized(Singleton.class) {
					// 这个检查，防止实例重复创建
					if(instance == null) {
						instance = new Singleton();
					}
				}
			}
			return instance;
		}
	}

##### Singleton in multi-thread 2. #####
上面的单例实现是多线程安全的，还有没有别的实现呢，毕竟我们只需在创建
实例时需要同步，何不利用__类加载__时的多线程安全性呢，

	// version-4

	public class Singleton {
		private static Singleton instance = new Singleton();
		private Singleton() { /* initialization */ }
		public static Singleton getInstance() {
			return instance;
		}
	}

static变量在类加载时被初始化，而在Java中类加载过程是多线程安全的，
这就解决了我们的问题。不过，如此简介实现也是有损失的，我们丢掉了
__惰性加载__，以前都是在调用`getInstance()`函数时才创建实例的，现在
把创建实例的时机提前到类加载过程了，但是似乎并没有什么损失。

##### Serializable Singleton #####
单例的序列化和反序列化实在是没有必要，但是有些时候还真的需要(我不知道什么时候)，
所以再次说明一下，要想序列化只需implements Serializable接口即可，单例也不
例外，下面的例子中我们关注序列化，暂且忽略多线程安全性，

	// version-5, wrong!!!

	public class Singleton implements Serializable {
		private static Singleton instance;
		// 禁用new操作符
		private Singleton() { /* initialization */ }
		// 获取单例实例
		public static Singleton getInstance() {
			if(instance == null) {
				instance = new Singleton();
			}
			return instance;
		}
	}

只是增加一个接口还不行，因为这会导致每次反序列化时都产生一个新的实例，
并且还不止于此，`static`属性是不被实例化的(默认实例化方式下)，这使得
`instance`这个类属性始终为`null`，除非`getInstance()`方法被调用。

	Object s1 = object_input_stream1.readObject();
	Object s2 = object_input_stream2.readObject();

	s1 == s2; // false

	// true, 可以修改instance为public看一下
	s1.instance == s2.instance == null; 
	
	// true, getInstance()时重新创建一个对象，
	// 它既不等于s1，也不等于s2
	s1.getInstance() == s2.getInstance() != null;

反序列化N次就有N的新的实例产生，而且调用`getInstance()`又会产生
新的实例，好乱。为此我们要制止那N个实例的产生，而是只产生一个实例，

	// version-6, wrong too!!!

	public class Singleton implements Serializable {
		private static Singleton instance;
		// 禁用new操作符
		private Singleton() { /* initialization */ }
		// 获取单例实例
		public static Singleton getInstance() {
			if(instance == null) {
				instance = new Singleton();
			}
			return instance;
		}

		// 这个方法在反序列化时会被调用到，如果它存在的话，
		// 当然这要使用反射机制，它一定要是private的，为啥？
		// 为啥？！你说为啥！
		private Object readResolve() {
			//assert(instance != null);
			return instance;
		}
	}

N次反序列化不再产生N个新实例了，而是一个？No，一个都没有！啊？
是的，一个都没有，因为`getInstance()`从来没有被调用过，`instance`
始终等于null，所有N次反序列化每次都得到`null`。虽然如此，当我们调用
`getInstance()`获取单例实例时，便会创建一个实例，而且它是真正唯一的。

万事大吉了？No，`getInstance()`得到的实例是唯一了，但是它并没有获得
序列化时保存下来的状态，我们的序列化被架空了，没起到任何效果。还要再次
改进，

	// version-7, right!!!
	
	public class Singleton implements Serializable {
		private static Singleton instance;
		// 禁用new操作符
		private Singleton() { /* initialization */ }
		// 获取单例实例
		public static Singleton getInstance() {
			if(instance == null) {
				instance = new Singleton();
			}
			return instance;
		}

		// 这个方法在反序列化时会被调用到，如果它存在的话，
		// 当然这要使用反射机制，它一定要是private的，为啥？
		// 为啥？！你说为啥！
		public Object readResolve() {
			//assert(instance != null);
			return instance;
		}

		// 反序列化时会创建一个新的对象(但是并不调用其构造函数)，
		// 默认会调用ObjectInputStream.defaultReadObject()
		// 来初始化该对象，但是如果这个对象(对象的类)有
		// readObject()方法的话，就转而调用该方法，而不再执行默认行为
		private void readObject(ObjectInputStream ois)
				throws ClassNotFoundException, IOException {

			// 使用默认的方法初始化对象(反序列化时创建的对象)
			ois.defaultReadObject();

			synchronized(SingleTon.class) {
				// 这个if判断很重要，它使得只有第一次反序列化时才会
				// 修改instance的值
				if(instance == null) {
					// re-initialize if needed

					instance = this; // IMPORTANT!
				}
			}
		}
	}

至此，完美了！ 每次反序列化时都会调用到`readObject()`方法，它会给
`instance`属性赋值，而后续的反序列化都会返回`instance`指定的对象，于是乎，
每次反序列化都会返回唯一的一个对象，而且该对象在第一次反序列化时已经
获取到了序列化时的状态，目的达成！

注意：`static`和`transient`的变量是无法序列化的！

##### Anti-reflect singleton #####

##### The Enum way #####
Enum，枚举。如，

	public enum Day {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    	THURSDAY, FRIDAY, SATURDAY
	}

	// 使用
	public void printDay(Day d)	 {
		switch(d) {
			case MONDAY:
				System.out.println("MONDAY");
			// ...
		}
	}

在C、C++中枚举类型的值其实就是整型，但是Java中不是，
	
	Day.A instanceof Day; // true
	System.out.println(Day.MONDAY.toString()); // "MONDAY"



Java中的枚举，

1. 它创建了一个新的class，
2. 同时在类载入时就创建了所有的对象(SUNDAY, MONDAY, ...)
3. 而且规定该类型变量只能在已经创建的对象(第2步创建的对象)中取值

如`Day`就是一个新的class，在类加载时就创建了七个值(`Day`类型的七个对象)，
并且所有`Day`类型的变量只能在这七个对象中取值。

既然`enum`其实就是`class`，那么当然可以定义属性和方法，

	public enum Fruit {
		// 定义两个实例，静态变量
		APPLE(1.2, 4.5),	// 1.2 kilograms, 4.5 dollars/kg
		ORANGE(2.3, 3.0);	// 2.3 kilograms, 3.0 dollars/kg
		
		// 下面的代码跟普通类定义一样，有属性有方法，有构造函数等
		private String color;
		private double price;
		private double mass;

		// 只允许private的构造函数，防止创建新的对象
		private Fruit(double mass, double price) {
			this.mass = mass;
			this.price = price;
		}
		
		// 需要花费多少钱
		public double cost() {
			return price * mass;
		}
	}

	// 使用枚举
	Fruit f = Fruit.APPLE; // or Fruit.ORANGE; 没有其他选择

接下来我们利用`enum`定义单例，

	public enum EnumSingleton {
		INSTANCE; // 只有唯一一个实例

		private EnumSingleton() { /* maybe initialize */ }

		public void doSomething() {
			System.out.println("do something...");
		}
	}

	// 使用
	EnumSingleton.INSTANCE.doSomething();

该单例定义极为简单，甚至构造函数都可以省略，但是它却可以做到，

1. 实例的唯一性。无法额外创建枚举实例，所以唯一性有保证。
2. 多线程安全性。枚举实例是在类加载时创建的，所以是多线程安全的，但显然不是延迟加载的。
3. 序列化安全性。枚举类型本身就做了序列化的操作，毕竟不可能同时存在两个`SUNDAY`实例。
4. 实现极其简单。