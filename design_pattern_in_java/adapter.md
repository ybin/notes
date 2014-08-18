#### Adapter ####
Adapter，适配器。

适配器的作用就是适配、转换，最简单直观的例子就是电源适配器，可能是提供的电压
跟需要的电压不同，也可能是提供的插脚数量跟需要的插脚数量不同，总之呢，需要在
中间转换一下才行，适配器就是中间的转换器。

在软件行业，适配器的工作也是如此，只不过提供的和需要的都是接口而已！
首先我们要明确需要的接口，比如一个开源的视频播放器接口，我们只需
调用一下即可播放视频，

	interface OldVideoPlayer {
		public void playBack(long startPosition);
	}

我们在代码中可能这样调用，

	if(mVideoPlayer != null) {
		mVideoPlayer.playBack(1000);
	}

但是某一天这个第三方库突然改变接口了，

	interface NewVideoPlayer {
		public void seek(long startPosition);
		public void play();
	}

此时我们需要增加一个适配器其转换接口，把`NewVideoPlayer`接口转换为`OldVideoPlayer`
接口，转换可以从两个层次上进行，于是适配器模式就有两种形式：类适配器、对象适配器。

##### Class adapter #####
类适配器(class adapter)就是类之间的转换器，它直接把提供的类转换为需要的类，

	// ViewPlayerImpl 实现了NewVideoPlayer接口
	class VideoPlayerAdapter extends ViewPlayerImpl
					implements OldVidePlayer {
		@Override
		public void playBack(long startPosition) {
			// implemented in ViewPlayerImpl class
			seek(startPosition);
			play();
		}
	}

然后我们使用`mVideoPlayer`的地方换做`mVideoPlayerAdapter`即可。

##### Object adapter #####
在对象适配器中，我们不再使用继承而是使用委托，

	class VideoPlayerAdapter implements OldVidePlayer {

		// ViewPlayerImpl 实现了NewVideoPlayer接口
		NewVideoPlayer newVideoPlayer = new ViewPlayerImpl();

		@Override
		public void playBack(long startPosition) {
			// delegate to ViewPlayerImpl object
			newVideoPlayer.seek(startPosition);
			newVideoPlayer.play();
		}
	}

##### The difference #####
从类适配器和对象适配器的实现可以做如下对比：

1. 类适配器调用更直接，只需实例化一个适配器对象，然后作一次函数调用
2. 对象适配器需要实现两个对象（适配器对象和原始对象），然后两次调用
3. 类适配器增加了子类数量，增加了维护成本

由此观之，两者各有利弊，需视情况做取舍。