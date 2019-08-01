class BabyEnigma
{
	public static void main(String args[])
	{
		BEUtilities util = new BEUtilities();
		BECrypter crypt = new BECrypter();

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
	}
}