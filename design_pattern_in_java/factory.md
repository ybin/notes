#### Factory pattern ####
Factory，工厂。

工厂的作用就是生产。有个工厂大而全，可以生产各种各样的东西，一个工厂全搞定；
而有的工厂小而精，只能生产一种东西，如果想生产多种东西就要创建多种工厂。

据此工厂又分为普通工厂、工厂方法和抽象工厂。比如我们想创建`MachineFactory`，既可以生产
`Car`，又可以生产`Moto`，但是不论如何，它们都必须是同一类产品`Machine`，

	interface Machine {
		void run();
	}

毕竟，我们的机械工厂无论如何都生产不出蛋糕来。

##### 普通工厂模式 #####
普通工厂就是一个大而全的工厂，啥都可以生产，

	class MachineFactory {
		public Machine produce(String type) {
			if("car".equals(type)) {
				return new Car();
			} else if("moto".equals(type)) {
				return new Moto();
			} else {
				System.out.println(
					"Sorry, I can not produce " + type);
				return null;
			}
		}
	}

这个工厂很牛叉，但是它有一个很严重的问题，那就是直到开工生产的那一刻，我们
才知道，有些`Machine`它是生产不出来的，也就是说编译期我们无法知道某一种
产品能否生产，这要到运行时才能明确知晓，这很不好。

于是，我们来改造一下这个工厂，

	class MachineFactory {
		public Machine produceCar() {
			return new Car();
		}

		public Machine produceMoto() {
			return new Moto();
		}
	}

这样一来，当我们试图__计划__生产卡车的时候就会发现这是不可能的，于是赶紧
修改计划，而不用等到开工的那一刻，即编译期我们就知道工厂无法生产卡车，不用
苦等到运行期。看起来不错！

##### 工厂方法模式 #####
突然某一天战争开始了，我们接到命令要开拓新业务，我们要生产坦克！当然我们
可以继续改造工厂让它可以生产坦克，但是这样真的好吗，打仗要改，不打仗又要改，
改来改去什么时候是个头？是时候设计新型工厂了。

	interface Factory {
		Machine produce();
	}

	class CarFactory implements Factory {
		public Machine produce() {
			return new Car();
		}
	}

	class MotoFactory implements Factory {
		public Machine produce() {
			return new Moto();
		}
	}

	// 战时就开工，和平时期就停工，其他工厂不受影响
	class TankFactory implements Factory {
		public Machine produce() {
			return new Tank();
		}
	}

抽象工厂是抽象的，它自己怎么会生产东西呢，
	
	Factory factory;

	// 如果想生产汽车
	factory = new CarFactory();
	// 如果想生产坦克
	factory = new TankFactory();

	// 但是，无论生产什么，都是一样的生产方式，抽象的生产方式
	Machine m = factory.produce();
	m.run();

看起来实际搞生产的工厂就像是生产线，某一条生产线可以随时开启、关闭，
甚至创建新的生产线，这些都不会影响其他生产线，这实在是太好了！

##### 抽象工厂模式 #####
无论生产Car还是Tank，我们的产品都不可能千篇一律，Car有高配和低配之分，
Moto等也是如此，现在我们要生产一整套奢侈品，包括高配的Car、高配的
Moto，于是我们重新整合工厂，有的工厂生产普通系列，有的工厂生产奢侈系列，

	interface AbstractFactory {
		Moto produceMoto();
		Car  produceCar();
	}

	class CommonFactory implements AbstractFactory {
		@Override
		Moto produceMoto() {
			return new Moto();
		}
		@Override
		Car produceCar() {
			return new Car();
		}
	}

	class LuxuryFactory implements AbstractFactory {
		@Override
		Moto produceMoto() {
			return new LuxuryMoto();
		}
		@Override
		Car produceCar() {
			return new LuxuryCar();
		}
	}

	// test
	AbstractFactory aFactory = new CommonFactory();
	Moto commonMoto = aFactory.produceMoto();
	Car commonCar = aFactory.produceCar();

	aFactory = new LuxuryFactory();
	LuxuryMoto luxuryMoto = aFactory.produceMoto();
	LuxuryCar luxuryCar = aFactory.produceCar();

![抽象工厂模式](Abstract_factory_UML.png "抽象工厂模式UML图")

##### 总结 #####
1.	普通工厂模式
	-	优点： 简单、直观
	-	缺点： 违反open-close principle，要想添加产品种类(如Tank)
		需要修改工厂类。
2.	工厂方法模式
	-	优点： 符合open-close principle(扩展产品种类，即纵向扩展)
	-	缺点： 扩展产品种类很方便(如Tank)，但是增加样式(如LuxuryCar)
		则很困难。
3.	抽象工厂模式
	-	优点： 符合open-close principle(扩展产品样式，即横向扩展)
	-	缺点： 扩展产品种类很困难(如Tank)，但是增加产品样式很方便。

比较工厂方法模式和抽象工厂模式，两个一个注重横向扩展，一个注重纵向扩展，
能否将两者统一起来呢？！

另外，工厂方法模式跟桥接模式，在架构上很相似，只是一个用于创建对象一个用于
对象行为。
