[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/) [![CodeFactor](https://www.codefactor.io/repository/github/spbu-coding-2023/trees-4/badge)](https://www.codefactor.io/repository/github/spbu-coding-2023/trees-4)

## About Tree Lib

Tree Lib is the kotlin library for working with 3 different binary tree implementations: Binary Search Tree(BST), AVL-Tree(AVL) and Red-Black Tree(RB).

## How to use
To init an object, you must provide key and value type
```
val treeExample: BSTree<<Key>, <Value>>
```
for example
```
val treeExample = BSTree<String, Double>()
```

All trees supports basic methods:
- Add values and keys as nodes to the tree:
  
  `treeExample.add("abc", 1.0)`
  
- Change node's value:
  
  `treeExample.changeVal("abc", 37.7)`
  
- Remove node from the tree:
  
  `treeExample.remove("key of node")`
  
- Find node by key:
  
  `treeExample.find("meaning of life")`
  
- Iterate in tree:
  
  `for(node in treeExample) { ...`
  
- ...and much more!

With binary trees, Tree Lib provides a node type for each of them. 
You can read node's key and value(with key() and value() methods), compare 
them between each other(although they must share same type), convert them to 
pair(toPair()) or string(toString()).

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

## Contact
This library was created by:
* Magomedov Islam(tg  @JapEmpLove) - BST
* Damir Yunusov(tg  @TopographicOcean) - RB
* Sofya Grishkova(tg  @tea_jack) - AVL

## Acknowledgments

[README Template](https://github.com/othneildrew/Best-README-Template)

Links to english Wikipages about binary trees:
* [Binary Tree](https://en.wikipedia.org/wiki/Binary_tree)
* [Binary Search Tree](https://en.wikipedia.org/wiki/Binary_search_tree)
* [Red-Black Tree](https://en.wikipedia.org/wiki/Red%E2%80%93black_tree)
* [AVL-Tree](https://en.wikipedia.org/wiki/AVL_tree)
  
[Here](https://www.geeksforgeeks.org/applications-advantages-and-disadvantages-of-binary-search-tree/) are provided applications, advantages and disadvantages of Binary Search Tree

Good [Habr article](https://habr.com/ru/articles/150732/) about AVL-Trees
