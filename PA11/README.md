backtrack: cost = 1.356677532196045, 5107.955526 milliseconds
mine: cost = 1.356677532196045, 4952.186661 milliseconds

My improved version of backtrack decreases the time slightly
because it checks if the minimum amount found in all of the 
other searches is less than the current search or not. If it
is less than the current search, then the program will stop
executing that path and go onto the next path knowing that
that path is not going to be the minumum cost path.