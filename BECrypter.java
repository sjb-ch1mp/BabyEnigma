import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class BECrypter
{

	String filePath;
	BEUtilities util;
	int key;

	BECrypter()
	{
		util = new BEUtilities();
	}

	public void encrypt(String stringFile, String keyPhrase, String pathString)
	{
		key = util.buildKey(keyPhrase);
		if(stringFile.equals("s"))
		{
			System.out.println(encryptString(pathString));
		}
		else
		{
			String fileArray[] = util.buildFileArray(pathString);	
			util.printToFile(pathString, fileArray, true);
		}
	}	

	public void decrypt(String stringFile, String keyPhrase, String pathString)
	{
		key = util.buildKey(keyPhrase);
		if(stringFile.equals("s"))
		{
			System.out.println(decryptString(pathString));
		}
		else
		{
			if(util.decryptFileOK(pathString))
			{
				util.printToFile(decryptFile(util.buildFileArray(pathString)));
			}
			else
			{
				System.out.println("Wrong file format");
				System.exit(1);
			}
		}
	}

	public void decryptAndShow(String keyPhrase, String filePath)
	{
		key = util.buildKey(keyPhrase);
		String[] fileArray = decryptFile(util.buildFileArray(filePath));
		System.out.println();
		for(int i=0; i<fileArray.length; i++)
		{
			System.out.println(fileArray[i]);
		}
		System.out.println();
	}

	private String encryptString(String s)
	{
		String outString="";	

		for(int i=0; i<s.length(); i++)
		{
			if(i != s.length() - 1)
			{
				outString += ((int) (s.charAt(i) + key)) + "?";
			}
			else
			{
				outString += ((int) (s.charAt(i) + key));
			}
		}

		return outString;
	}

	private String[] encryptFile(String[] fileArray)
	{
		for(int i=0; i<fileArray.length; i++)
		{
			fileArray[i] = encryptString(fileArray[i]);
		}
		return fileArray;
	}

	private String decryptString(String s)
	{
		if(util.decryptStringOK(s))
		{
			String outString="";
			String charInt="";
			for(int i=0; i<s.length(); i++)
			{	
				if(s.charAt(i) == '?')
				{
					outString += (char) (Integer.parseInt(charInt) - key);
					charInt = "";
				}
				else if(i == s.length() - 1)
				{				
					charInt += s.charAt(i);
					outString += (char) (Integer.parseInt(charInt) - key);
				}
				else
				{
					charInt += s.charAt(i);
				}
			}
			return outString;
		}
		else
		{
			return "Error - BECrypter.decryptString()";
		}
	}

	private String[] decryptFile(String[] fileArray)
	{
		for(int i=0; i<fileArray.length; i++)
		{
			fileArray[i] = decryptString(fileArray[i]);
		}
		return fileArray;
	}

}