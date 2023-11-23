# Arrays en PHP

``` php
<?php
    /* sum elements of the array */
    echo "<h3>Sum elements of the array</h3>";
    $arr = array(1, 4, 7, 2, 5, 9, 6, 8, 3, 2, 6, 4, 5);
    print_r ($arr);
    $sum = 0;
    foreach ($arr as $num)
        $sum = $sum + $num;
    echo "<p>Sum: $sum</p>";

    /* associative array sorting examples */

    //definition of the hydrogenated bases.
    $dna_bases = array("t" => "thymine", "a" => "adenine", "g" => "guanine", "c" => "cytosine");

    //sort array by value
    echo "<h3>Sort array by value</h3>";
    sort($dna_bases);
    var_dump($dna_bases);
    echo "<br />";
    print_r($dna_bases);
 
    //sort array by value (reverse)
    echo "<h3>Sort array by value (reverse)</h3>";
    rsort($dna_bases);
    print_r($dna_bases);

    //sort array by key
    echo "<h3>Sort array by key</h3>"; 
    ksort($dna_bases);
    print_r($dna_bases);

    //sort array by key (reverse)
    echo "<h3>Sort array by key (reverse)</h3>"; 
    krsort($dna_bases); 
    print_r($dna_bases);

    /* count number of occurrences of each base in a dna sequence */
    echo "<h3>Count number of occurrences of each base</h3>"; 
    $dna_sequence = "agatggcggcgctgaggggtcttgggggctctaggccggccacctactgg";
    $dna_count = array();
    for ($i = 0; $i < strlen($dna_sequence); $i++) {
        if (array_key_exists($dna_sequence[$i], $dna_count)) {
            $dna_count[$dna_sequence[$i]] ++; 
        }
        else {
            $dna_count[$dna_sequence[$i]] = 1;
        }
    }
    print_r($dna_count);
    
    
    echo "<h2>Recorreguts automàtics sobre arrays aplicant funcions i filtres</h2>";
    //arrays amb dades de prova.
    $arr1 = array(2.4, 2.6, 3.5);
    $arr2 = array(2.4, 2.6, 3.5);

    echo "<h3>array_map</h3>";
    print_r(array_map('floor', $arr1));  //arrodonir cap a baix.
    echo "<br/>";
    print_r(array_map(function ($num1, $num2) { return $num1+$num2; }, $arr1, $arr2));

    echo "<h3>array_walk</h3>";
    array_walk($arr2, function (&$value, $key) { $value=floor($value); }); 
    print_r($arr2);
    echo "<br/>";
    array_walk($arr1, function ($value, $key) { echo "<p>$key - $value</p>"; });

    echo "<h3>array_filter</h3>";
    //filtrar elements.
    print_r(array_filter($arr1, function ($num) { return $num>2.5; }));
```

``` php
<?php
/**
* Example of associative arrays.
*/
echo '<h2>Associative arrays example</h2>';
//define associative array.
$nucleotides = array (
  'u' => 'uracil',
  'a' => 'adenine',
  'c' => 'cytosine',
  'g' => 'guanine',
  't' => 'thymine',
);
echo '<h3>Show nucleotide array with print_r</h3>';
print_r($nucleotides);
echo '<h3>Show nucleotide array with foreach loop</h3>';
foreach ($nucleotides as $key => $value) {
  echo "[$key: $value]";
}
echo '<h3>Get and show array keys</h3>';
$keys = array_keys($nucleotides);
print_r($keys);
echo '<h3>Get and show array values</h3>';
$values = array_values($nucleotides);
print_r($values);
echo '<h3>Exchange keys and values</h3>';
print_r(array_flip($nucleotides));
echo '<h3>Apply funtions to each element of an array</h3>';
print_r(array_map('strtoupper', $values));
echo '<h3>Get the 1st key with a given value</h3>';
print_r(array_search('guanine', $nucleotides));

$str="a-adenine";
$delimiter="-";
echo "<h3>Sort by key</h3>";
ksort($nucleotides);
print_r($nucleotides);

echo "<h3>Sort by key (reverse)</h3>"; 
krsort($nucleotides); 
print_r($nucleotides);
  
echo "<h3>Sort by value</h3>";
sort($nucleotides);
print_r($nucleotides);
 
echo "<h3>Sort by value (reverse)</h3>";
rsort($nucleotides);
print_r($nucleotides);

echo "<h3>Convert string to array</h3>";
list($key, $value)=explode($delimiter, $str);   
for ($i=0; $i<strlen($key); $i++) {
    $arr["$key"]=$value;    
}
echo "<p>$str</p>";
print_r($arr);
 
echo "<h3>Convert array to string</h3>";
$str=implode($delimiter, $nucleotides);
print_r($nucleotides);
echo "<p>$str</p>";
```
