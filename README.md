# POL

## @xzy 2020.4.22
添加了一个library包，其中包括：  
item：题目类  
itemlib：题库类   
liblist：题库列表类  
以及内置的题库和题库列表索引    

liblist X = new liblist( );  
X.load( );  
之后X的liblist成员（arraylist类型）就会包含所有已经存档的题库  
每个题库的lib成员（arraylist类型）包含题库中所有的题目  


## @xzy 2020.5.6
library包进行了一些bug修正  
添加了管理题库的ui    


## @xzy 2020.5.17
管理题库的ui做了一点优化，关于颜色和表格选择  
