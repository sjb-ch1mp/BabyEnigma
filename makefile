run : BabyEnigma.class BEUtilities.class BECrypter.class
	java BabyEnigma
BabyEnigma.class : BabyEnigma.java
	javac BabyEnigma.java
BEUtilities.class : BEUtilities.java
	javac BEUtilities.java
BECrypter.class : BECrypter.java
	javac BECrypter.java
clean : 
	rm *.class
