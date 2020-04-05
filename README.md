# OurC_interpreter
PAL_project

## v.3.0 重大修正
1. 修正 StringProcessor : 現在 StringProcessor 會 "盡可能" match 所有合法Token .e.g ':=' '<>' '.55' ..
  -> 基於上述修改 MyParser現在不需要再組裝 token , 每次都一定是 "盡可能match" 的 token.
  
2.由於MyScanner 設計缺陷， 現在 EndOfInputErrorException 會印出 Program exits... 以滿足 Ourc_Project1的要求。


## dev_v1.0
v1.0 will finish PL project one, there are sth new.

### new working class
1. MyParser
2. CommandWritter
3. MyCPU
    - fuctionCallStack
    
....

### new DataStructure

1. AssignCommand
2. ComputingCommand
3. PrintCommand
....

### availible Enum(class form)
....

### implimented
1. LexicalErrorException now is working. it might be throw when you call `Token.SymbolOf`.

## dev_v0.0
the first edition of ourc interpreter.Only can read in sth....

1. Myscanner
2. PAL_uTestNum can work

### important

now these branch can work in PAL, But sth still cannot be done!

1. when clone into PAL, you should delete all block comment.
2. when clone into PAL,the condition be seperate to 2 or more line, the indent will always lost 1 exclude 1st Line. 
