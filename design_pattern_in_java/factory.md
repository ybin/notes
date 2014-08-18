#### Factory pattern ####
Factory，工厂。

工厂的作用就是生产。有个工厂大而全，可以生产各种各样的东西，一个工厂全搞定；
而有的工厂小而精，只能生产一种东西，如果想生产多种东西就要创建多种工厂。

据此工厂又分为普通工厂和抽象工厂。比如我们想创建`MachineFactory`，既可以生产
`Car`，又可以生产`Moto`，但是不论如何，它们都必须是同一类产品`Machine`，

	interface Machine {
		void run();
	}

毕竟，我们的机械工厂无论如何都生产不出蛋糕来。

##### 工厂模式 #####
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

##### 抽象工厂模式 #####
突然某一天战争开始了，我们接到命令要开拓新业务，我们要生产坦克！当然我们
可以继续改造工厂让它可以生产坦克，但是这样真的好吗，打仗要改，不打仗又要改，
改来改去什么时候是个头？是时候设计新型工厂了。

	interface AbstractFactory {
		Machine produce();
	}

	class CarFactory implements AbstractFactory {
		public Machine produce() {
			return new Car();
		}
	}

	class MotoFactory implements AbstractFactory {
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
	
	AbstractFactory factory;

	// 如果想生产汽车
	factory = new CarFactory();
	// 如果想生产坦克
	factory = new TankFactory();

	// 但是，无论生产什么，都是一样的生产方式，抽象的生产方式
	Machine m = factory.produce();
	m.run();

看起来实际搞生产的工厂就像是生产线，某一条生产线可以随时开启、关闭，
甚至创建新的生产线，这些都不会影响其他生产线，这实在是太好了！

	