# Phone number encoder implementation details

Encoder is in charge of finding all possible representations of phone number 
via words available in the dictionary.

## How to build

To build application run `mvn clean install`

## How to run

To start application run
```
java -jar target/number-encoding-1.0-SNAPSHOT.jar "path/to/phone/list"
```
where `path/to/phone/list` is relative or absolute path to files with list of phones.

Dictionary for encoding is embedded to artifact via the file `dictionary.txt`.

## Algorithm

Algorithm for finding all possible encodings is simple tree-traversing algorithm, 
where tree represents phone number via alphabetical characters.

Each tree layer represents one digit of phone number. Each node - is one particular character,
which corresponds to current phone digit. For instance, for phone number **64** tree looks as follow

                   root               
              /     |      \             
            b       k        u           
          /  \     /  \     /  \          
         f    t   f    t   f    t
         
## Architecture

There are three base model instances in application, representing via interfaces:

**PhoneNumbersReader** - responsible for reading incoming file of phone numbers.

**Dictionary** - responsible for providing words for encoding process.

**PhoneNumberEncoder** - perform encoding of input phone number.

###PhoneNumbersReader
is implemented via class `FilePhoneNumbersReader`. It's just simple line by line file reader.

###Dictionary 
is implemented with class `HashedNormalizedDictionary` which contains hashed map for all
words. 

The key of map is normalized source word. Normalization is the process of removing umlaut characters
and transforming all characters to lower case.

The map values (represented by `WordSet` class) is in essence set of words with same normal form.

With such implementation finding a particular word in dictionary has complexity O(1).
        
Also we want dictionary to provide as with searching-by-word-prefix method
```
boolean hasWordsWithNormalizedPrefix(String normalizedPrefix);
```
Performing such search directly through words map gives as complexity O(n), 
where n - is amount of words in dictionary.

To reduce complexity to O(log m) (where m - is the max word length) we need to build extra tree
of nodes (`DictionaryNode` class). 

Each node represent one character in the word.
Thus we have a tree of all prefixes possible for this particular dictionary.

###PhoneNumberEncoder
is implemented with `TreeRecursivePhoneNumberEncoder` class, there recursive 
tree-traversing algorithm is shown.

Algorithm starts with root of the tree and empty prefix.
On each followed step it 
- calculates `new prefix` by appending current node value to incoming prefix;
- looks if `new prefix` is valid word from dictionary;
- if so, invoke (recursively) algorithm with current node as root and empty prefix;
- invoke (recursively) algorithm with current node as root and `new prefix`.

If at particular step no encoding options for rest of the phone number are found, 
then digit is applied as it is (method `canAppendChildNodesAsDigit`) and new traverse 
through children of current node and empty prefix is performed (method `traverseAsDigitChildNodesOf`).

Algorithm stops traversing if
- leaf node is reached (method `isEncodingCompleted`), 
that means that we successfully encode the phone number;
- no one word for particular prefix is found in dictionary 
(method `hasWordsWithNormalizedPrefix` of `Dictionary` interface), 
that means that this tree branch can't give any possible encoding options 
and traversing should be stopped.


All model entities tested with unit tests.

## Performance
Solution is not optimal regarding performance concerns, but still has several optimizations:
- dictionary implementation with constant time requests;
- dropping useless tree branches as fast as possible during tree traversing.

At a test sequence of 100_000 phone numbers each 50 random digits length 
application executes about 50 seconds.
