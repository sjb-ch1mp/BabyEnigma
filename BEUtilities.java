import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

class BEUtilities
{
	public final String info = 
		"\n===  BabyEnigma v2.1  ===" + 
		"\n=== By Samuel Brookes ===\n" + 
		"\nBabyEnigma is a very simple string and text file encrypter/decrypter.\n" + 
		"\nUsage: \t[e|d][s|f] [keyPhrase] \"[filePath|string]\" | r [keyPhrase] \"[filePath]\" | q" + 
		"\n\t======================================" + 
		"\n\tq: quit" + 
		"\n\te: Encrypt" + 
		"\n\td: Decrypt" + 
		"\n\tr: Read decrypted file (no write)" +
		"\n\ts: String" + 
		"\n\tf: File" + 
		"\n\tkeyPhrase: Phrase used to generate key" + 
		"\n\t======================================";
	public final String usage = "Usage: [e|d][s|f] [keyPhrase] [filePath] | r [keyPhrase] [filePath] | q";
	private String[] errorMsg = new String[1];

	public boolean commandOK(String command)
	{
		int commaCnt = 0;
		int spaceCnt = 0;
		for(int i=0; i<command.length(); i++)
		{
			if(command.charAt(i) == '"') commaCnt++;
			if(command.charAt(i) == ' ') spaceCnt++;
		}
		return ((commaCnt == 2) && (spaceCnt > 1));
	}

	public boolean argsOK(String args[])
	{
		if(args.length == 3)
		{
			if(args[0].equals("r"))
			{
				return true;
			}
		}
		else if(args.length == 4)
		{
			if(args[0].equals("e") || args[0].equals("d"))
			{
				if(args[1].equals("s") || args[1].equals("f"))
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
		boolean err = false;

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
			System.out.println("File not found");
			err = true;
		}
		catch(IOException exc)
		{
			System.out.println("Error reading file");
			err = true;
		}

		if(!err)
		{
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
				System.out.println("File not found");
				err = true;
			}
			catch(IOException exc)
			{
				System.out.println("Error reading file");
				err = true;
			}
		}

		if(!err) return fileArray;
		else
		{
			errorMsg[0] = "err";
			return errorMsg;
		}
	}

	public boolean decryptFileOK(String filePath)
	{
		if(filePath.indexOf(".be") == -1)
		{
			String[] fileArray = buildFileArray(filePath);
			if(fileArray[0].equals("err")) return false;
			else
			{
				for(int i=0; i<fileArray.length; i++)
				{
					if(!decryptStringOK(fileArray[i])) return false;
				}
			}
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
					if(fileArray[i].equals("err")) break;
				}
			}
		}
		catch(IOException exc)
		{
			System.out.println("Error writing to file");
			return;
		}
		System.out.println("All done!\r\nFile written to: " + filePath);
	}

	public void returnToMenu()
	{
		return;
	}
}