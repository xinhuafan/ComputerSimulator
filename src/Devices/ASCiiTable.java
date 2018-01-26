/**
 *@Author Jeremy Case
 *George Washington University
 *CSCI 6461:  Computer Architecture
 *****************************************************************************/

package Devices;

/**
*ASCiiTable Class
* 
*Class is a simple list of values to convert binary to/from chars
*******************************************************************************/

public class ASCiiTable
{
    public final int SPACE = 32;	//		Space
    public final int EXCLAMATION = 33;	//	!	Exclamation mark
    public final int DOUBLEQUOTE = 34;	//	"	Double quotes (or speech marks)
    public final int NUMBER = 35;	//	#	Number
    public final int DOLLAR = 36;	//	$	Dollar
    public final int PERCENT = 37;	//	%	Procenttecken
    public final int AMPERSAND	= 38;	//	&	Ampersand
    public final int SINGLEQUOTE = 39;	//	'	Single quote
    public final int OPENPARENTH = 40;	//	(	Open parenthesis (or open bracket)
    public final int CLOSEPARENTH = 41;	//	)	Close parenthesis (or close bracket)
    public final int ASTERISK = 42;	//	*	Asterisk
    public final int PLUS = 43;         //	+	Plus
    public final int COMMA = 44;	//	,	Comma
    public final int HYPHEN = 45;	//	-	Hyphen
    public final int PERIOD = 46;	//	.	Period, dot or full stop
    public final int SLASH = 47;	//	/	Slash or divide
    public final int ZERO = 48;         //	0	Zero
    public final int ONE = 49;          //	1	One
    public final int TWO = 50;          //	2	Two
    public final int THREE = 51;	//	3	Three
    public final int FOUR = 52;         //	4	Four
    public final int FIVE = 53;         //	5	Five
    public final int SIX = 54;          //	6	Six
    public final int SEVEN = 55;        //	7	Seven
    public final int EIGHT = 56	;       //	8	Eight
    public final int NINE = 57	;	//	9	Nine
    public final int COLON = 58	;	//	:	Colon
    public final int SEMICOLON = 59;	//	;	Semicolon
    public final int LESSTHAN =	60;	//	<	Less than (or open angled bracket)
    public final int EQUALS = 61;	//	=	Equals
    public final int GREATERTHAN = 62;	//	>	Greater than (or close angled bracket)
    public final int QUESTIONMARK = 63;	//	?	Question mark
    public final int ATSYMBOL =	64;	//	@	At symbol
    public final int A	= 65;           //	A	Uppercase A
    public final int B	= 66;           //	B	Uppercase B
    public final int C	= 67;           //	C	Uppercase C
    public final int D	= 68;           //	D	Uppercase D
    public final int E	= 69;           //	E	Uppercase E
    public final int F	= 70;           //	F	Uppercase F
    public final int G	= 71;           //	G	Uppercase G
    public final int H	= 72;           //	H	Uppercase H
    public final int I	= 73;           //	I	Uppercase I
    public final int J	= 74;           //	J	Uppercase J
    public final int K	= 75;           //	K	Uppercase K
    public final int L	= 76;           //	L	Uppercase L
    public final int M	= 77;           //	M	Uppercase M
    public final int N	= 78;           //	N	Uppercase N
    public final int O	= 79;           //	O	Uppercase O
    public final int P	= 80;           //	P	Uppercase P
    public final int Q	= 81;           //	Q	Uppercase Q
    public final int R	= 82;           //	R	Uppercase R
    public final int S	= 83;           //	S	Uppercase S
    public final int T	= 84;           //	T	Uppercase T
    public final int U	= 85;           //	U	Uppercase U
    public final int V	= 86;           //	V	Uppercase V
    public final int W	= 87;           //	W	Uppercase W
    public final int X	= 88;           //	X	Uppercase X
    public final int Y	= 89;           //	Y	Uppercase Y
    public final int Z	= 90;           //	Z	Uppercase Z
    public final int OPENBRACKET = 91;	//	[	Opening bracket
    public final int BACKSLASH	= 92;	//	\	Backslash
    public final int CLOSEBRACKET = 93;	//	]	Closing bracket
    public final int CARET = 94;	//	^	Caret - circumflex
    public final int UNDERSCORE	= 95;	//	_	Underscore
    public final int GRAVE =	96;	//	`	Grave accent
    public final int a	=	97;	//	a	Lowercase a
    public final int b	=	98;	//	b	Lowercase b
    public final int c	=	99;	//	c	Lowercase c
    public final int d	=	100;	//	d	Lowercase d
    public final int e	=	101;	//	e	Lowercase e
    public final int f	=	102;	//	f	Lowercase f
    public final int g	=	103;	//	g	Lowercase g
    public final int h	=	104;	//	h	Lowercase h
    public final int i	=	105;	//	i	Lowercase i
    public final int j	=	106;	//	j	Lowercase j
    public final int k	=	107;	//	k	Lowercase k
    public final int l	=	108;	//	l	Lowercase l
    public final int m	=	109;	//	m	Lowercase m
    public final int n	=	110;	//	n	Lowercase n
    public final int o	=	111;	//	o	Lowercase o
    public final int p	=	112;	//	p	Lowercase p
    public final int q	=	113;	//	q	Lowercase q
    public final int r	=	114;	//	r	Lowercase r
    public final int s	=	115;	//	s	Lowercase s
    public final int t	=	116;	//	t	Lowercase t
    public final int u	=	117;	//	u	Lowercase u
    public final int v	=	118;	//	v	Lowercase v
    public final int w	=	119;	//	w	Lowercase w
    public final int x	=	120;	//	x	Lowercase x
    public final int y	=	121;	//	y	Lowercase y
    public final int z	=	122;	//	z	Lowercase z
    public final int OPENBRACE = 123;	//	{	Opening brace
    public final int PIPE = 124;	//	|	Vertical bar
    public final int CLOSEBRACE	= 125;	//	}	Closing brace
    public final int EQUIVALENCY = 126;	//	~	Equivalency sign - tilde
    
    public ASCiiTable()
    {
        // constructor stuff
    }
    
   /**
    *convertIntToString
    * 
    *@param toConvert the integer to convert to a string 
    *@return the string after it has been converted
    ***************************************************************************/
    public String convertIntToString(final int toConvert)
    {
        switch (toConvert)
        {
            case (SPACE): { return " ";}
            case (EXCLAMATION): {return "!";}
            case (DOUBLEQUOTE): {return "''";}
            case (NUMBER): {return "#";}
            case (DOLLAR): {return "$";}
            case (PERCENT): {return "%";}
            case (AMPERSAND): {return "&";}
            case (SINGLEQUOTE): {return "'";}
            case (OPENPARENTH): {return "(";}
            case (CLOSEPARENTH): {return ")";}
            case (ASTERISK): {return "*";}
            case (PLUS): {return "+";}
            case (COMMA): {return ",";}
            case (HYPHEN): {return "-";}
            case (PERIOD): {return ".";}
            case (SLASH): {return "/";}
            case (ZERO): {return "0";}
            case (ONE): {return "1";}
            case (TWO): {return "2";}
            case (THREE): {return "3";}
            case (FOUR): {return "4";}
            case (FIVE): {return "5";}
            case (SIX): {return "6";}
            case (SEVEN): {return "7";}
            case (EIGHT): {return "8";}
            case (NINE): {return "9";}
            case (COLON): {return ":";}
            case (SEMICOLON): {return ";";}
            case (LESSTHAN): {return "<";}
            case (EQUALS): {return "=";}
            case (GREATERTHAN): {return ">";}
            case (QUESTIONMARK): {return "?";}
            case (ATSYMBOL): {return "@";}
            case (A): {return "A";}
            case (B): {return "B";}
            case (C): {return "C";}
            case (D): {return "D";}
            case (E): {return "E";}
            case (F): {return "F";}
            case (G): {return "G";}
            case (H): {return "H";}
            case (I): {return "I";}
            case (J): {return "J";}
            case (K): {return "K";}
            case (L): {return "L";}
            case (M): {return "M";}
            case (O): {return "O";}
            case (P): {return "P";}
            case (Q): {return "Q";}
            case (R): {return "R";}
            case (S): {return "S";}
            case (T): {return "T";}
            case (U): {return "U";}
            case (V): {return "V";}
            case (W): {return "W";}
            case (X): {return "X";}
            case (Y): {return "Y";}
            case (Z): {return "Z";}
            case (OPENBRACKET): {return "[";}
            case (BACKSLASH): {return "\\";}
            case (CLOSEBRACKET): {return "]";}
            case (CARET): {return "^";}
            case (UNDERSCORE): {return "_";}
            case (GRAVE): {return "`";}
            case (a): {return "a";}
            case (b): {return "b";}
            case (c): {return "c";}
            case (d): {return "d";}
            case (e): {return "e";}
            case (f): {return "f";}
            case (g): {return "g";}
            case (h): {return "h";}
            case (i): {return "i";}
            case (j): {return "j";}
            case (k): {return "k";}
            case (l): {return "l";}
            case (m): {return "m";}
            case (n): {return "n";}
            case (o): {return "o";}
            case (p): {return "p";}
            case (q): {return "q";}
            case (r): {return "r";}
            case (s): {return "s";}
            case (t): {return "t";}
            case (u): {return "u";}
            case (v): {return "v";}
            case (w): {return "w";}
            case (x): {return "x";}
            case (y): {return "y";}
            case (z): {return "z";}
            case (OPENBRACE): {return "{";}
            case (PIPE): {return "|";}
            case (CLOSEBRACE): {return "}";}
            case (EQUIVALENCY): {return "~";}
            default: {return "";}
        }
            
    }
    
   /**
    *convertIntToString
    * 
    *@param toConvert the integer to convert to a string 
    *@return the string after it has been converted
    * 
    *Important - this method will only convert a single string 
    ***************************************************************************/
    public int convertStringToInt(final String toConvert)
    {
        switch (toConvert)
        {
            case (""): { return SPACE;}
            case ("!"): {return EXCLAMATION;}
            case ("''"): {return DOUBLEQUOTE;}
            case ("#"): {return NUMBER;}
            case ("$"): {return DOLLAR;}
            case ("%"): {return PERCENT;}
            case ("&"): {return AMPERSAND;}
            case ("'"): {return SINGLEQUOTE;}
            case ("("): {return OPENPARENTH;}
            case (")"): {return CLOSEPARENTH;}
            case ("*"): {return ASTERISK;}
            case ("+"): {return PLUS;}
            case (","): {return COMMA;}
            case ("-"): {return HYPHEN;}
            case ("."): {return PERIOD;}
            case ("/"): {return SLASH;}
            case ("0"): {return ZERO;}
            case ("1"): {return ONE;}
            case ("2"): {return TWO;}
            case ("3"): {return THREE;}
            case ("4"): {return FOUR;}
            case ("5"): {return FIVE;}
            case ("6"): {return SIX;}
            case ("7"): {return SEVEN;}
            case ("8"): {return EIGHT;}
            case ("9"): {return NINE;}
            case (":"): {return COLON;}
            case (";"): {return SEMICOLON;}
            case ("<"): {return LESSTHAN;}
            case ("="): {return EQUALS;}
            case (">"): {return GREATERTHAN;}
            case ("?"): {return QUESTIONMARK;}
            case ("@"): {return ATSYMBOL;}
            case ("A"): {return A;}
            case ("B"): {return B;}
            case ("C"): {return C;}
            case ("D"): {return D;}
            case ("E"): {return E;}
            case ("F"): {return F;}
            case ("G"): {return G;}
            case ("H"): {return H;}
            case ("I"): {return I;}
            case ("J"): {return J;}
            case ("K"): {return K;}
            case ("L"): {return L;}
            case ("M"): {return M;}
            case ("O"): {return O;}
            case ("P"): {return P;}
            case ("Q"): {return Q;}
            case ("R"): {return R;}
            case ("S"): {return S;}
            case ("T"): {return T;}
            case ("U"): {return U;}
            case ("V"): {return V;}
            case ("W"): {return W;}
            case ("X"): {return X;}
            case ("Y"): {return Y;}
            case ("Z"): {return Z;}
            case ("["): {return OPENBRACKET;}
            case ("\\"): {return BACKSLASH;}
            case ("]"): {return CLOSEBRACKET;}
            case ("^"): {return CARET;}
            case ("_"): {return UNDERSCORE;}
            case ("`"): {return GRAVE;}
            case ("a"): {return a;}
            case ("b"): {return b;}
            case ("c"): {return c;}
            case ("d"): {return d;}
            case ("e"): {return e;}
            case ("f"): {return f;}
            case ("g"): {return g;}
            case ("h"): {return h;}
            case ("i"): {return i;}
            case ("j"): {return j;}
            case ("k"): {return k;}
            case ("l"): {return l;}
            case ("m"): {return m;}
            case ("n"): {return n;}
            case ("o"): {return o;}
            case ("p"): {return p;}
            case ("q"): {return q;}
            case ("r"): {return r;}
            case ("s"): {return s;}
            case ("t"): {return t;}
            case ("u"): {return u;}
            case ("v"): {return v;}
            case ("w"): {return w;}
            case ("x"): {return x;}
            case ("y"): {return y;}
            case ("z"): {return z;}
            case ("{"): {return OPENBRACE;}
            case ("|"): {return PIPE;}
            case ("}"): {return CLOSEBRACE;}
            case ("~"): {return EQUIVALENCY;}
            default: {return SPACE;}
        }     
    }
    
   /**
    *convertASCiiToInt
    * 
    *@param toConvert the integer to convert 
    *@return the converted number
    ***************************************************************************/
    public int convertASCiiToInt(final int toConvert)
    {
        switch (toConvert)
        {
            case (ZERO) : {return 0;}
            case (ONE) : {return 1;}
            case (TWO) : {return 2;}
            case (THREE) : {return 3;}
            case (FOUR) : {return 4;}
            case (FIVE) : {return 5;}
            case (SIX) : {return 6;}
            case (SEVEN) : {return 7;}
            case (EIGHT) : {return 8;}
            case (NINE) : {return 9;}
            default : {return 0;}
        }
    }
}
