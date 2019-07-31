/**
 * @author Samuel Brookes
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BabyEnigma
{
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter key: ");    
    String keyStr = in.nextLine();
    System.out.println("Enter file path: ");
    String filePath = in.nextLine();

    int key = buildKey(keyStr);
    String[] fileArray = buildFileArray(filePath);
    
    System.out.println("Enter d for decrypt and e for encrypt: ");
    char option = in.next().charAt(0);
    
    if(option == 'd') decryptFile(fileArray, key, filePath);
    else if(option == 'e') encryptFile(fileArray, key, filePath);
    else System.out.println("Incorrect option...");
    
  }
  
  private static void decryptFile(String[] fileArray, int key, String filePath)
  {
    String[] decryptedFileArray = new String[fileArray.length];
    for(int i=0; i<fileArray.length; i++)
    {
      if(fileArray[i] != "")
      {
        String decryptedString = "";
        fileArray[i] = fileArray[i].replace("?", "A");
        String[] encryptedChars = fileArray[i].split("A");
        for(int j=0; j<encryptedChars.length; j++)
        {
          if(encryptedChars[j] != "")
          {
            decryptedString += String.valueOf((char) (Integer.parseInt(encryptedChars[j]) - key));
          }
        }
        decryptedFileArray[i] = decryptedString;
      }
    }
    
    filePath = filePath.substring(0, filePath.indexOf(".be"));
    filePath += ".java";

    try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)))
    {
      for(int i=0; i<decryptedFileArray.length; i++)
      {
        bw.write(decryptedFileArray[i]);
        bw.newLine();
      }
    }
    catch(Exception exc)
    {
      System.out.println(exc);
    }
  }

  private static void encryptFile(String[] fileArray, int key, String filePath)
  {
    String[] encryptedFileArray = new String[fileArray.length];
    for(int i=0; i<fileArray.length; i++)
    {
      String encryptedString = "";
      for(int j=0; j<fileArray[i].length(); j++)
      {
        encryptedString += String.valueOf((int) fileArray[i].charAt(j) + key);
	if(j != fileArray[i].length() - 1) encryptedString += "?";
      }
      encryptedFileArray[i] = encryptedString;
    }
    
    filePath = filePath.substring(0, filePath.indexOf(".java"));
    filePath += ".be";

    try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)))
    {
      for(int i=0; i<encryptedFileArray.length; i++)
      {
        bw.write(encryptedFileArray[i]);
        bw.newLine();
      }
    }
    catch(Exception exc)
    {
      System.out.println(exc);
    }
  }

  private static String[] buildFileArray(String filePath)
  {
     ArrayList<String> fileArrayList = new ArrayList<>(0);
     try(BufferedReader br = new BufferedReader(new FileReader(filePath)))
     {
       String line = "";
       while((line = br.readLine()) != null)
       {
         fileArrayList.add(line);
       }
     }
     catch(Exception exc)
     {
       System.out.println(exc);
     }
     return fileArrayList.toArray(new String[0]);
  }

  private static int buildKey(String keyStr)
  {
    int key=0;

    for(int i=0; i<keyStr.length(); i++)
    {
      key += (int) keyStr.charAt(i);
      key = Math.round(key/2);
    }
    
    return key;
  }

}
