## Standard Markdown Syntax ##

参考[网页](http://daringfireball.net/projects/markdown/syntax)

### Paragraphs ###

1. 段落之间的间隔是“空行”(只包括空格、制表符等空白字符的行)
2. 强制换行(不分段)：至少两个空格加换行符

### Headers ###

1. 用`#`的数量表示标题的层级

### Blockquote ###

1. 引用用`"<"`来表示
2. 引用可以嵌套，只需多写几个`">"`即可
3. 引用中可以包含markdown语法

> This is blockquotes paragraph
> 
> > and this nested quotes paragraph
> #### This is H4 ####
> `and this is code`
> 
> - list item 1
> - list item 2

### Lists ###

1. list有两种：ordered list和unordered list
2. ordered list用数字+`"."`开始(数字顺序没有关系)
3. unordered list可以用`"+"`、`"-"`、`"*"`之一开始，均可
4. list中可以包含paragraph，但是必须缩进4个空格或者1个Tab
5. list中可以包含blockquote，但是必须缩进4个空格或者1个Tab
6. list中可以包含code，但是必须缩进8个空格或者2个Tab
7. 防止出现莫名其妙的list，可以把数字后的“点”转义一下

--------------------------

*示例：*

	1. ordered list item 1
	2. ordered list item 2
	3. ordered list item 3

输出：

1. ordered list item 1
2. ordered list item 2
3. ordered list item 3

--------------------------

*示例：*

	+ unordered list item 1
	+ unordered list item 2
	+ unordered list item 3

输出：

+ unordered list item 1
+ unordered list item 2
+ unordered list item 3

--------------------------

*示例：*

	1. Windows
	   + DOS
	   + Windows NT
	     - XP
	     - Vista
	     - Win 7
	     - Win 8
	2. Linux
	   + Ubuntu
	     - Ubuntu Education
	     - Mint
	   + Debian
	     - Debian 5
	     - Debian 6
	   + Red Hat
	     - Red Hat Enterprise
	     - Fedora
	     - CentOS

输出：

1. Windows
   + DOS
   + Windows NT
     - XP
     - Vista
     - Win 7
     - Win 8
2. Linux
   + Ubuntu
     - Ubuntu Education
     - Mint
   + Debian
     - Debian 5
     - Debian 6
   + Red Hat
     - Red Hat Enterprise
     - Fedora
     - CentOS

--------------------------

*示例：*

	1.	paragraph 1 in list item 1
	
		paragraph 2 in list item 1
	2.	paragraph 1 in list item 2
	
		patagraph 2 in list item 2

输出：

1.	paragraph 1 in list item 1

	paragraph 2 in list item 1
2.	paragraph 1 in list item 2

	patagraph 2 in list item 2

--------------------------

*示例：*

	1. paragraph
	
		> quote
		> 
		> quote

输出：

1. paragraph

	> quote
	> 
	> quote

--------------------------

*示例：*

	1. paragraph
	
			if(true) {
			  printf("Code in list.\n");
			}

输出：

1. paragraph

		if(true) {
		  printf("Code in list.\n");
		}

--------------------------

*示例：*

	> 1985\. 神奇的一个年份。。。
	>
	> 1985. 神奇的一个年份。。。

输出：

> 1985\. 神奇的一个年份。。。
>
> 1985. 神奇的一个年份。。。

--------------------------

### Links ###

1.	两种link方式可用：inline link(行内链接) 和 reference link(引用链接)
2.	inline link: `[link text](url "optional title")`
3.	reference link:
	+	定义： `[id] url "optional title"`
	+	使用： `[link text][id]`
	+	`id`可以省略，此时使用`link text`作为`id`

-------------------------

*示例：*

	[Google](https://www.google.com/ "Google Home")

输出：

[Google](https://www.google.com/ "Google Home")

-------------------------

*示例：*

	Google Home: [Google][1] & Github Home: [Github][]
	
[1]: https://www.google.com/ "Google Home"
	[Github]: https://github.com/ "Github Home"

输出：

Google Home: [Google][1] & Github Home: [Github][]

[1]: https://www.google.com/ "Google Home"
[Github]: https://github.com/ "Github Home"

-------------------------

### Emphasis ###

1. 使用( `*` )或者( `_` )作为强调(emphasis)
2. 使用( `**` )或者( `__` )作为更加严厉的强调(strong)

*示例：*

	This is *emphasis* word.
	
	This is **strong** word.

输出：

This is *emphasis* word.

This is **strong** word.

-------------------------

### Code ###

1. 行内code：使用反引号(`)
2. code区块：缩进4个空格或者1个Tab
3. List中的code区块：缩进8个空格或者2个Tab
4. 如果code中本身就包含反引号(`)，code使用两个连续的反引号

*示例：*

	``wc -l `cat adb.log` ``

输出：

``wc -l `cat adb.log` ``

-------------------------

### Image ###

1. 跟Link类似，只是在前面加一个(`!`)而已
2. reference image的定义处不用增加(`!`)

-------------------------

*示例：*

	![A cat](img/cat.jpg "Cat")
	
	![A cat too][1]
	
	[1]: imge/cat.jpg "Cat"

输出：

![A cat](img/cat.png "Cat")

![A cat too][1]

[1]: img/cat.png "Cat"