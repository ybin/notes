#### Singleton ####

##### Singleton in single thread #####
Singleton，单例模式，正如其名字看上去那样，单例的目的在于
__有且仅有一个实例存在__。最普通的单例模式实现如下，

	public class Singleton {
		private Singleton instance;
		// 禁用new操作符
		private Singleton() { /* initialization */ }
		// 获取单例实例
		public Singleton getInstance() {
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

	public class Singleton {
		private Singleton instance;
		private Singleton() { /* initialization */ }
		// 仅仅只是添加了一个synchronized而已
		synchronized public Singleton getInstance() {
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

	public class Singleton {
		private Singleton instance;
		private Singleton() { /* initialization */ }
		public Singleton getInstance() {
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

	public class Singleton {
		// static 修饰符至关重要！
		private static Singleton instance = new Singleton();
		private Singleton() { /* initialization */ }
		public Singleton getInstance() {
			return instance;
		}
	}

static变量在类加载时被初始化，而在Java中类加载过程是多线程安全的，
这就解决了我们的问题。不过，如此简介实现也是有损失的，我们丢掉了
__惰性加载__，以前都是在调用`getInstance()`函数时才创建实例的，现在
把创建实例的时机提前到类加载过程了，但是似乎并没有什么损失。