# Linked List Problem

## 1. Merge Two Sorted Lists (21)

You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists in a one **sorted** list. The list should be made by splicing together the nodes of the first two lists.

[合并两个有序链表 Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)

### 1.1 Iteration approach:

- Time complexity : O(n + m)

  Because exactly one of `l1` and `l2` is incremented on each loop iteration, the `while` loop runs for a number of iterations equal to the sum of the lengths of the two lists. All other work is constant, so the overall complexity is linear.

- Space complexity : O(1)

  The iterative approach only allocates a few pointers, so it has a constant overall memory footprint.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode myListNode = new ListNode(-1);
        ListNode dummy = myListNode;
        ListNode l1 = list1, l2 = list2;
        
        while(l1 != null && l2 != null){       
            if(l1.val >= l2.val){
            myListNode.next = l2;
            l2 = l2.next;
            }else{
            myListNode.next = l1;
            l1 = l1.next;
            }
            myListNode = myListNode.next;          
        }       
        while(l1 != null){
            myListNode.next = l1;
            l1 = l1.next;
            myListNode = myListNode.next;
        }
        while(l2 != null){
            myListNode.next = l2;
            l2 = l2.next;
            myListNode = myListNode.next;
        }
        return dummy.next;      
    }
}

/////////////// LeetCode Solution ///////////////////
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // maintain an unchanging reference to node ahead of the return node.
        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // At least one of l1 and l2 can still have nodes at this point, so connect
        // the non-null list to the end of the merged list.
        prev.next = l1 == null ? l2 : l1;
        return prehead.next;
    }
}
```


### 1.2 Recursion


- Time complexity : O(n + m)

  Because each recursive call increments the pointer to `l1` or `l2` by one (approaching the dangling `null` at the end of each list), there will be exactly one call to `mergeTwoLists` per element in each list. Therefore, the time complexity is linear in the combined size of the lists.

- Space complexity : O(n + m)

​		The first call to `mergeTwoLists` does not return until the ends of both `l1` and `l2` have been reached, 		so n+m stack frames consume O(n + m)space.

```java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // maintain an unchanging reference to node ahead of the return node.
        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // At least one of l1 and l2 can still have nodes at this point, so connect
        // the non-null list to the end of the merged list.
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;
    }
}
```



## 2. Merge k Sorted Lists (23)

You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

### 2.1 Binary Heap 二叉堆

二叉堆（Binary Heap）其主要操作就两个，`sink`（下沉）和 `swim`（上浮），用以维护二叉堆的性质。

其主要应用有两个，首先是一种排序方法「堆排序」，第二是一种很有用的数据结构「优先级队列」。

[二叉堆实现优先级队列](https://labuladong.gitee.io/algo/2/21/62/)

### 2.2 Priority Queue 优先级队列

优先级队列这种数据结构有一个很有用的功能，你插入或者删除元素的时候，元素会自动排序，这底层的原理就是二叉堆的操作。

数据结构的功能无非增删查改，优先级队列有两个主要 API，分别是 `insert` 插入一个元素和 `delMax` 删除最大元素（如果底层用最小堆，那么就是 `delMin`）。

这两个方法就是建立在 `swim` 和 `sink` 上的。

**`insert` 方法先把要插入的元素添加到堆底的最后，然后让其上浮到正确位置**。

**`delMax` 方法先把堆顶元素 `A` 和堆底最后的元素 `B` 对调，然后删除 `A`，最后让 `B` 下沉到正确位置**。

插入和删除元素的时间复杂度为 `O(logK)`，`K` 为当前二叉堆（优先级队列）中的元素总数。因为我们时间复杂度主要花费在 `sink` 或者 `swim` 上，而不管上浮还是下沉，最多也就树（堆）的高度，也就是 log 级别。

### 2.3 Lambda Function For Comparator

[Sorting with Lambda Function](https://mkyong.com/java8/java-8-lambda-comparator-example/)

[Comparator API](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Comparator.html)

#### a. Sort without Lambda Function

```java
package com.mkyong.java8;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestSorting {

	public static void main(String[] args) {

		List<Developer> listDevs = getDevelopers();

		System.out.println("Before Sort");
		for (Developer developer : listDevs) {
			System.out.println(developer);
		}
		
		//sort by age
		Collections.sort(listDevs, new Comparator<Developer>() {
			@Override
			public int compare(Developer o1, Developer o2) {
				return o1.getAge() - o2.getAge();
			}
		});
	
		System.out.println("After Sort");
		for (Developer developer : listDevs) {
			System.out.println(developer);
		}
		
	}

	private static List<Developer> getDevelopers() {

		List<Developer> result = new ArrayList<Developer>();

		result.add(new Developer("mkyong", new BigDecimal("70000"), 33));
		result.add(new Developer("alvin", new BigDecimal("80000"), 20));
		result.add(new Developer("jason", new BigDecimal("100000"), 10));
		result.add(new Developer("iris", new BigDecimal("170000"), 55));
		
		return result;

	}
	
}
Output

Before Sort
Developer [name=mkyong, salary=70000, age=33]
Developer [name=alvin, salary=80000, age=20]
Developer [name=jason, salary=100000, age=10]
Developer [name=iris, salary=170000, age=55]

After Sort
Developer [name=jason, salary=100000, age=10]
Developer [name=alvin, salary=80000, age=20]
Developer [name=mkyong, salary=70000, age=33]
Developer [name=iris, salary=170000, age=55]
  
  	//sort by age
	Collections.sort(listDevs, new Comparator<Developer>() {
		@Override
		public int compare(Developer o1, Developer o2) {
			return o1.getAge() - o2.getAge();
		}
	});

	////When the sorting requirement is changed, you just pass in another new anonymous Comparator class :


	//sort by name	
	Collections.sort(listDevs, new Comparator<Developer>() {
		@Override
		public int compare(Developer o1, Developer o2) {
			return o1.getName().compareTo(o2.getName());
		}
	});
				
	//sort by salary
	Collections.sort(listDevs, new Comparator<Developer>() {
		@Override
		public int compare(Developer o1, Developer o2) {
			return o1.getSalary().compareTo(o2.getSalary());
		}
	});	
```

#### b. Sort with Lambda Function

```java
public class TestSorting {

	public static void main(String[] args) {

...		
		//lambda here!
		listDevs.sort((Developer o1, Developer o2)->o1.getAge()-o2.getAge());
	
		//java 8 only, lambda also, to print the List
		listDevs.forEach((developer)->System.out.println(developer));
	}

	private static List<Developer> getDevelopers() {
...
	}
}
```



### 2.4 Class PriorityQueue

[PriorityQueue API](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/PriorityQueue.html)

`PriorityQueue(int initialCapacity, Comparator<? super E> comparator)`
Creates a PriorityQueue with the specified initial capacity that orders its elements according to the specified comparator.

`pp.poll()` return the head of this queue

```java
ListNode mergeKLists(ListNode[] lists) {
    if (lists.length == 0) return null;
    // 虚拟头结点
    ListNode dummy = new ListNode(-1);
    ListNode p = dummy;
    // 优先级队列，最小堆
    PriorityQueue<ListNode> pq = new PriorityQueue<>(
        lists.length, (a, b)->(a.val - b.val));
    // 将 k 个链表的头结点加入最小堆
    for (ListNode head : lists) {
        if (head != null)
            pq.add(head);
    }

    while (!pq.isEmpty()) {
        // 获取最小节点，接到结果链表中
        ListNode node = pq.poll();
        p.next = node;
        if (node.next != null) {
            pq.add(node.next);
        }
        // p 指针不断前进
        p = p.next;
    }
    return dummy.next;
}
```

