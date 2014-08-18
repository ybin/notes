#### Bridge pattern ####
Bridge，桥接。

桥接，就是在两个地点之间牵线搭桥。想象一下数据库连接，JDBC。
数据库又很多，各种各样，为每一个数据库写一套使用代码是不可能的，
没有数据库用户愿意做这样的傻事，于是，Java帮用户做了绝大多数工作，

- 首先要做的就是统一接口，各个数据库厂商都要按照这个接口来实现
- 其次要做一个虚拟的实现(implement)

接下来是具体的实现，

	interface Driver {
		void insert();
		void delete();
		void query();
	}

这里的接口函数定义并不准确，只做演示使用。然后是我们的虚拟实现，
其实也就是我们的bridge，

	class VirtualDB implements Driver {
		private Driver realDB;

		void setDriver(Driver d) {
			realDB = d;
		}

		@Override
		void insert() {
			realDB.insert();
		}

		@Override
		void delete() {
			realDB.delete();
		}

		@Override
		void query() {
			realDB.query();
		}
	}

某个数据库厂商可以这样实现，
	
	class MySQLDB implements Driver {
		// insert()
		// delete();
		// query();
	}

而我们使用时可以这样，
	
	class Test {
		Driver driver = new VirtualDB();
		driver.setDriver(new OracleDB());
		driver.insert();
		// ...

		driver.setDriver(new MySQLDB());
		driver.query();
		// ...

		driver.setDriver(new PosgresDB());
		driver.delete();
		// ...
	}

在各个真实的driver间来回切换，很好。