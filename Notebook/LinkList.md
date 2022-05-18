# Queue & Stack & Linkedlist

## 1. Queue

FIFO: First in first out



## 2. Stack

LIFO: Last in first out

Function: pop(), push(), top()

### 2.1 Implement a queue by using to stacks

#### a. Solution1:

Stack 1: To buffer all new element

Stack 2: To pop out first element

​				Case 2.1: If stack 2 is empty, then we move all the element from stack1 to stack 2 one by one

​				Case 2.2: If stack 2 is not empty, then we call stack2.pop()

**Time complexity:** 

push() - O(1)

pop() - O(n)

**Amortized time complexity (平均每个元素的时间复杂度) ：**

Five elements (1 2 3 4 5 in stack 1) :

First time call pop() (5 pop from stack 1) + (5 push to stack 2) + (1 pop from stack 1)

N elements:

First time: n + n + 1 = 2n + 1

Second time call pop: 1

Third time call pop: 1

....

Nth time call pop: 1

Amortized time complexity = (2n+1 + n-1)/n  = 3 = O(1)



### 2.2 Implement the min() function by with O(1)

**Solution 1:**

Keep the add() and remove() in sync between stack 1 and stack 2

Stack 1: 1 3 2 4

Stack 2: 1 1 1 1

**Follow up question:**

How to optimize the space usage of stack 2 assuming there are a lot of duplicate elements in stack1

Stack 1: 2 2 2 2 1 2 1

Stack 2: 2 1

When pop out the last 1 in stack 1 we don't know whether should we pop out the 1 in stack 2

**We should keep in mind the position of first 1 entering Stack 1.** 

Stack 1: 2 2 2 2 1 2 1 1 2 4 4 -1

Stack 2: <2, stack1.size() = 1>  <1, stack1.size() = 5> <-1, stack1.size() = 12>

**Only when minimum value enter the stack we should change Stack 2**

### 2.3 Sort number with 3(or 2) Stack