# Expressions regulars en PHP

  - [preg\_filter](http://www.php.net/manual/en/function.preg-filter.php) – performs a regular expression search and replace
  - [preg\_grep](http://www.php.net/manual/en/function.preg-grep.php) – returns array entries that match a pattern
  - [preg\_last\_error](http://www.php.net/manual/en/function.preg-last-error.php) – returns the error code of the last PCRE regex execution
  - [preg\_match](http://www.php.net/manual/en/function.preg-match.php) – perform a regular expression match
  - [preg\_match\_all](http://www.php.net/manual/en/function.preg-match-all.php) – perform a global regular expression match
  - [preg\_quote](http://www.php.net/manual/en/function.preg-quote.php) – quote regular expression characters
  - [preg\_replace](http://www.php.net/manual/en/function.preg-replace.php) – perform a regular expression search and replace
  - [preg\_replace\_callback](http://www.php.net/manual/en/function.preg-replace-callback.php) – perform a regular expression search and replace using a callback
  - [preg\_split](http://www.php.net/manual/en/function.preg-split.php) – split string by a regular expression

    Meta Characters
        Description
    ^   Marks the start of a string
    $   Marks the end of a string
    .   Matches any single character
    |   Boolean OR
    ()  Group elements
    [abc]   Item in range (a,b or c)
    [^abc]  NOT in range (every character except a,b or c)
    \s  White-space character
    a?  Zero or one b characters. Equals to a{0,1}
    a*  Zero or more of a
    a+  One or more of a
    a{2}    Exactly two of a
    a{,5}   Up to five of a
    a{5,10}     Between five to ten of a
    \w  Any alpha numeric character plus underscore. Equals to [A-Za-z0-9_]
    \W  Any non alpha numeric characters
    \s  Any white-space character
    \S  Any non white-space character
    \d  Any digits. Equals to [0-9]
    \D  Any non digits. Equals to [^0-9]
    
    
    Pattern Modifiers
        Description
    i   Ignore case
    m   Multiline mode
    S   Extra analysis of pattern
    u   Pattern is treated as UTF-8
