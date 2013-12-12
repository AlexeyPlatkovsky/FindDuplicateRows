FindDuplicateRows
=================

Insert text in test.txt and get this text with marked duplicated rows
This code delete all punctuation from rows
All duplicated rows will be marked with symbol *
* - the row is duplicated 1 time
** - the row is duplicated 2 times, etc
The program is case sensitive: "Row" and "row" not equals for it

E.g. test.txt:

Similar row
Not similar row
Similar, row
Row similar
Similar row
similar row

result.txt will be:

Similar row
Not similar row
*Similar, row
Row similar
**Similar row
similar row
