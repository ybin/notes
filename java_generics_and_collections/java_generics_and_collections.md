### Generics ###

#### Wildcard ####
Java generics中的wildcard(通配符)用问号("?")来表示，它表示任意的引用类型。
通配符进一步分为两类：wildcard with extends 和 wildcard with super。
使用原则：__只是读取数据时使用extends，只是写入数据时使用super，既读又写时
不要使用wildcard。

##### wildcard with extends #####
形如`<? extends TYPE>`这样的表达方式叫做extends类型通配符，它表示任意TYPE
的子类型，例如`List<? extends Number> nums;`，它意在告诉人们：
__`nums`这个List里面保存的都是Number类型数据或者Number的子类型数据。__
所以，我们__通过Number类的接口__访问`nums`里面的元素肯定是没有问题的。
例如，

	void dump(List<? extends Number> list) {
		// 通过Number类的接口访问list的元素
		for(Number n : list) {
			System.out.println(n);
		}
	}

##### wildcard with super #####
形如`<? super TYPE>`这样的表达方式叫做super类型通配符，它表示任意TYPE
的父类型，例如`List<? super Number> nums`，它意在告诉人们：
__`nums`这个List里面保存的都是Number类型数据或者Number的父类型数据。__
所以，我们往里写入Number类型的数据肯定是没有问题的。
例如，

	// in class MyGenerics
	// 限制src中的数据类型不超过T，以便读取；
	// 限制dst必须接受T类型，以便写入。
	static void copy(List<? extends T> src, List<? super T> dst) {
		for(int i = 0; i < src.size(); i++) {
			dst.set(src.get(i));
		}
	}

	List<Integer> ints = Arrays.asList(5, 6);
	List<Object> objs = Arrays.<Object>asList(1, 2, 3, "four");
	// 以下四种调用等效
	MyGenerics.copy(ints, objs); // 类型自动推断
	MyGenerics.<Object>copy(ints, objs); // T设置为Object
	MyGenerics.<Number>copy(ints, objs); // T设置为Number
	MyGenerics.<Integer>copy(ints, objs); // T设置为Integer

