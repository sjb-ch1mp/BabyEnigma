import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class BabyEnigma
{
	public static void main(String args[])
	{
		BEUtilities util = new BEUtilities();
		BECrypter crypt = new BECrypter();

		String command = "";
		System.out.println(util.info);

		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
		{
			while(!command.equals("q"))
			{
				System.out.print("BabyEnigma > ");
				command = br.readLine();
				if(command.equals("q")) break;
				else if(!command.equals(""))
				{
					if(command.charAt(0) == 'e' || command.charAt(0) == 'd')
					{
						args = new String[4];
						args[0] = command.substring(0, 1);
						args[1] = command.substring(1, 2);
						if(util.commandOK(command))
						{
							args[2] = command.split(" ")[1];
							args[3] = command.substring(command.indexOf("\"") + 1, command.lastIndexOf("\""));
							if(util.argsOK(args))
							{

								if(args[0].equals("e")) crypt.encrypt(args[1], args[2], args[3]);
								else crypt.decrypt(args[1], args[2], args[3]);
							}
							else System.out.println(util.usage);
						}
						else
						{
							System.out.println(util.usage);
						}
							
					}
					else if(command.charAt(0) == 'r')
					{
						if(util.commandOK(command))
						{
							args = command.split(" ");
							args[2] = args[2].replace("\"", "");

							if(util.argsOK(args))
							{
								crypt.decryptAndShow(args[1], args[2]);		
							}
						}
					}
					else System.out.println(util.usage);
				}
				else System.out.println(util.usage);	
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
	}
}
