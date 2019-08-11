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

		while(!command.equals("q"))
		{
			System.out.print("> ");
			
			try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
			{
				command = br.readLine();
				if(command.equals("q")) break;
				else if(command.charAt(0) == 'e' || command.charAt(0) == 'd')
				{
					args = new String[4];
					args[0] = command.substring(0, 1);
					args[1] = command.substring(1, 2);
					args[2] = command.split(" ")[1];
					args[3] = command.substring(command.indexOf("\"") + 1, command.lastIndexOf("\"") - 1);
				}
				else if(command.charAt(0) == 'r') args = command.split(" ");

				if(util.argsOK(args))
				{
					if(args[0].equals("e")) crypt.encrypt(args[1], args[2], args[3]);
					else if(args[0].equals("d")) crypt.decrypt(args[1], args[2], args[3]);
					else if(args[0].equals("r")) crypt.decryptAndShow(args[1], args[2]);
				}
				else System.out.println(util.usage);
			}
			catch(IOException e)
			{
				System.out.println(e);
				System.exit(1);
			}

			
			
		}

		/*
		if(!util.argsOK(args))
		{
			System.out.println(util.getInfo());
			return;
		}

		char choice = args[0].charAt(1);

		if(choice == 'e')
		{
			crypt.encrypt(args[1], args[2]);
		}
		else if(choice == 'd')
		{
			crypt.decrypt(args[1], args[2]);
		}
		else if(choice == 'r')
		{
			crypt.decryptAndShow(args[1]);
		}
		else
		{
			System.out.println(util.getInfo());
		}
		*/
	}
}