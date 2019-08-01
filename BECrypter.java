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

	public void encrypt(String stringFile, String keyPhrase)
	{
		key = util.buildKey(keyPhrase);
		System.out.println("Enter the " + 
			((stringFile.equals("-f"))?"path of the file":"string") +
			" that you would like encrypted: ");
		System.out.print("> ");
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
		{
			if(stringFile.equals("-s"))
			{
				String s = encryptString(br.readLine());
				System.out.println(s);
			}
			else
			{
				filePath = br.readLine();
				String fileArray[] = util.buildFileArray(filePath);
				fileArray = encryptFile(fileArray);
				util.printToFile(filePath, fileArray, true);
			}
		}
		catch(IOException exc)
		{
			System.out.println("I/O Error: BECrypter.encrypt()");
			System.exit(1);
		}
	}	

	public void decrypt(String stringFile, String keyPhrase)
	{
		key = util.buildKey(keyPhrase);
		System.out.println("Enter the " + 
			((stringFile.equals("-f"))?"path of the file":"string") +
			" that you would like decrypted: ");
		System.out.print("> ");
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
		{
			if(stringFile.equals("-s"))
			{
				String s = decryptString(br.readLine());
				System.out.println(s);
			}
			else
			{
				filePath = br.readLine();
				if(util.decryptFileOK(filePath))
				{
					String fileArray[] = util.buildFileArray(filePath);
					fileArray = decryptFile(fileArray);
					util.printToFile(filePath, fileArray, false);
				}
				else
				{
					System.out.println("Wrong file format");
					System.exit(1);
				}
			}
		}
		catch(IOException exc)
		{
			System.out.println("I/O Error: BECrypter.decrypt()");
			System.exit(1);
		}
	}

	public void decryptAndShow(String keyPhrase)
	{
		key = util.buildKey(keyPhrase);
		System.out.println("Enter the path of the file that you would like decrypted: ");
		System.out.print("> ");
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
		{
			filePath = br.readLine();
			String[] fileArray = util.buildFileArray(filePath);
			fileArray = decryptFile(fileArray);
			System.out.println();
			for(int i=0; i<fileArray.length; i++)
			{
				System.out.println(fileArray[i]);
			}
			System.out.println();
		}
		catch(IOException exc)
		{
			System.out.println("I/O Error: BECrypter.decryptAndShow()");
			System.exit(1);
		}
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