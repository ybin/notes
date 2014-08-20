#### Observer ####
Observer，观察者。

观察者，顾名思义。观察者唯一需要做的事情就是观察到事件发生后执行某些动作，
否则观察了半天啥都不能做，有啥用？！

	interface Observer {
		void execute(/* maybe some message */);
	}

有观察者就有“被观察者”，被观察者其实也就是消息的发布者，

	interface Publisher {
		void publish();
		void register(Observer o);
		void unregister(Observer o);
	}

消息发布者不仅要发布消息，还要允许观察者注册进来，这样发布消息的时候
才知道消息要发布给谁。

下面让他们互动起来，

	class ObserverImpl {
		@Override
		void execute(/* maybe some message */) {
			System.out.println("do execute in Observer.");
		}
	}

	class PublisherImpl {
		private ArrayList<Observer> observers;
		@Override
		register(Observer o) {
			observers.add(o);
		}
		@Override
		unregister(Observer o) {
			observers.remove(o);
		}
		@Override
		void publish() {
			for(Oberver o : observers) {
				o.process(/* maybe some message */);
			}
		}
	}

这里省略了Publisher中消息的发源过程，一旦有消息需要发布，直接调用`publish()`即可。