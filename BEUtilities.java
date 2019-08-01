import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

class BEUtilities
{
	private String info = "\n=== BabyEnigma v2" + 
		"\n=== By Samuel Brookes\n" + 
		"\nBabyEnigma is a very simple string and text file encrypter/decrypter.\n" + 
		"\nUsage: \tjava BabyEnigma [-e|-d] [-s|-f] [keyPhrase]" + 
		"\n\tjava BabyEnigma -r [keyPhrase]" + 
		"\n\tjava BabyEnigma -i\n" +
		"\n\t-e: Encrypt" + 
		"\n\t-d: Decrypt" + 
		"\n\t-r: Read decrypted file (no write)" +
		"\n\t-i: Show Information (this)" + 
		"\n\t-s: String" + 
		"\n\t-f: File" + 
		"\n\tkeyPhrase: Phrase used to generate key";

	public String getInfo()
	{
		return info;
	}

	public boolean argsOK(String args[])
	{
		if(args.length == 1)
		{
			if(args[0].equals("-i"))
			{
				return true;
			}
		}
		else if(args.length == 2)
		{
			if(args[0].equals("-r"))
			{
				return true;
			}
		}
		else if(args.length == 3)
		{
			if(args[0].equals("-e") || args[0].equals("-d"))
			{
				if(args[1].equals("-s") || args[1].equals("-f"))
				{
					return true;
				}
			}
		}
		return false;
	} 

	public int buildKey(String keyPhrase)
	{//This method generates an integer key from the keyphrase
		int key=0;

		for(int i=0; i < keyPhrase.length(); i++)
		{
			key += keyPhrase.charAt(i);
			key /= 2;
		}
		
		return key;
	}

	public String modifyFilePath(String filePath, boolean encrypted)
	{//This method modifies the file path to denote _enc or _dec

		if(encrypted)
		{
			filePath = filePath.substring(0, filePath.lastIndexOf("."));
			filePath += ".be";
		}
		else
		{
			filePath = filePath.substring(0, filePath.lastIndexOf("."));
			filePath += "_dec.txt";
		}

		return filePath;
	}

	public String[] buildFileArray(String file)
	{//This method builds an array from the specified file
		String[] fileArray = null;

		//count the lines in the file
		int lineCount = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			while((br.readLine()) != null)
			{
				lineCount++;
			}
		}
		catch(FileNotFoundException exc)
		{
			System.out.println("FileNotFoundException: be.BuildFileArray");
		}
		catch(IOException exc)
		{
			System.out.println("IOException: be.BuildFileArray");
		}

		//import the lines to the file
		fileArray = new String[lineCount];
		for(int i=0; i<fileArray.length; i++)
		{
			fileArray[i] = "";
		}

		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line = "";
			int idx = 0;
			while((line = br.readLine()) != null)
			{
				if(!line.equals(""))
				{
					fileArray[idx++] = line;
				}
			}
		}
		catch(FileNotFoundException exc)
		{
			System.out.println("FileNotFoundException: BEUtilities.buildFileArray()");
		}
		catch(IOException exc)
		{
			System.out.println("IOException: BEUtilities.buildFileArray()");
		}

		return fileArray;
	}

	public boolean decryptFileOK(String filePath)
	{
		if(filePath.indexOf(".be") == -1)
		{
			return false;
		}
		return true;
	}

	public boolean decryptStringOK(String s)
	{
		if(!s.matches("[0-9?]*"))
		{
			return false;
		}
		return true;
	}

	public void printToFile(String filePath, String[] fileArray, boolean encrypted)
	{
		filePath = modifyFilePath(filePath, encrypted);
		//write crypted array to new file
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)))
		{	
			for(int i=0; i<fileArray.length; i++)
			{
				if(!fileArray[i].equals(""))
				{
					bw.write(fileArray[i] + "\r\n");	
				}
			}
		}
		catch(IOException exc)
		{
			System.out.println("IOException: be.main");
			return;
		}
		System.out.println("All done!\r\nFile written to: " + filePath);
	}
}