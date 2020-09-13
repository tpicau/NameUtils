# NameUtils
_Simple static utility class for normalising proper nouns._

A best effort, diacritic compatible approach to normalising proper nouns
following the rules documents by HURIDOCS in their [human rights manual](https://www.huridocs.org/resource/how-to-record-names-of-persons/).

## normalise
Fixes all spacing and capitalisation.
```java
NameUtils.normalise("  anne   Maclaren ") >> "Anne MacLaren");
NameUtils.normalise("  Anne   Maclaren ") >> "Anne Maclaren");
NameUtils.normalise("mAria della rosa") >> "Maria Della Rosa");
NameUtils.normalise(" niccolò lo savio") >> "Niccolò Lo Savio");
NameUtils.normalise("sergio de la peña") >> "Sergio de la Peña"); 
```

## isNormalised
Checks if an input string sufficiently matches the normalisation rules.
```java
NameUtils.isNormalised(" HI") == false;
NameUtils.isNormalised(" Hero Hugh ") == false;
NameUtils.isNormalised("Hero  Hugh") == false;
NameUtils.isNormalised("Hero Hugh") == true;
NameUtils.isNormalised("HERO Hugh") == false;
``` 

## normaliseWhitespaceToEmpty
Removes unnecessary whitespace from an input string or returns an empty string if blank.
```java
NameUtils.normaliseWhitespaceToEmpty(" HI") >> " HI";
NameUtils.normaliseWhitespaceToEmpty(" Hero Hugh ") >> "Hero Hugh";
NameUtils.normaliseWhitespaceToEmpty("Hero  Hugh") >> "Hero Hugh";
NameUtils.normaliseWhitespaceToEmpty("Hero Hugh") >> "Hero Hugh";
NameUtils.normaliseWhitespaceToEmpty("HERO Hugh") >> "HERO Hugh";
NameUtils.normaliseWhitespaceToEmpty(null) >> "";
NameUtils.normaliseWhitespaceToEmpty("") >> "";
NameUtils.normaliseWhitespaceToEmpty("  ") >> "";
NameUtils.normaliseWhitespaceToEmpty("  a") >> "a";
``` 